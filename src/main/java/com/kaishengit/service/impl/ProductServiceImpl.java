package com.kaishengit.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.job.ProductInventoryJob;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.service.ProductService;
import com.kaishengit.service.exception.ServiceException;
import com.kaishengit.util.redis.RedisUtil;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;
    @Value("${qiniu.ak}")
    private String qiniuAk;
    @Value("${qiniu.sk}")
    private String qiniuSK;
    @Value("${qiniu.buket}")
    private String qiniuBuket;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 添加商品
     *
     * @param product
     * @param inputStream
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveProduct(Product product, InputStream inputStream) {
        //上传文件到七牛
        String key = uploadToQiNiu(inputStream);
        //保存Product对象
        product.setProductImage(key);
        productMapper.insertSelective(product);
        //在redis中添加商品库存量的集合
        for (int i = 0; i < product.getProductInventory(); i++) {
            RedisUtil.lpush("product:" + product.getId() + ":inventory", String.valueOf(i));
        }
        //添加秒杀结束的定时任务，用于秒杀结束时更新库存
        addSchedulerJob(product.getEndTime().getTime(), product.getId());
    }

    /**
     * 添加一个定时任务
     *
     * @param endTime
     * @param productId
     */
    private void addSchedulerJob(Long endTime, Integer productId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("productId", productId);

        JobDetail jobDetail = JobBuilder
                .newJob(ProductInventoryJob.class)
                .setJobData(jobDataMap)
                .withIdentity(new JobKey("taskID:" + productId, "productInventoryGroup"))
                .build();

        DateTime dateTime = new DateTime(endTime);

        StringBuilder cron = new StringBuilder("0")
                .append(" ")
                .append(dateTime.getMinuteOfHour())
                .append(" ")
                .append(dateTime.getHourOfDay())
                .append(" ")
                .append(dateTime.getDayOfMonth())
                .append(" ")
                .append(dateTime.getMonthOfYear())
                .append(" ? ")
                .append(dateTime.getYear());

        logger.info("CRON EX: {}", cron.toString());

        ScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule(cron.toString());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception ex) {
            throw new ServiceException(ex, "添加定时任务异常");
        }
    }

    /**
     * 显示全部抢购商品
     *
     * @return
     */
    @Override
    public List<Product> findAll() {
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("start_time asc");
        return productMapper.selectByExample(productExample);
    }

    /**
     * 根据主键查询商品
     *
     * @param id
     * @return
     */
    @Override
    public Product findById(Integer id) {
        Product product = null;
        String json = RedisUtil.getStringValue("product:" + id);
        if (json == null) {
            product = productMapper.selectByPrimaryKey(id);
            RedisUtil.setString("product:" + id, JSON.toJSONString(product));
        } else {
            product = JSON.parseObject(json, Product.class);
        }
        return product;
    }

    /**
     * 秒杀商品
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public void secKill(Integer id) throws ServiceException {
        Product product = JSON.parseObject(RedisUtil.getStringValue("product:" + id), Product.class);
        if (!product.isStart()) {
            throw new RuntimeException("你来早了，还没开始");
        }
        if (!product.isEnd()) {
            throw new RuntimeException("该商品秒杀活动已经结束");
        }
        String value = RedisUtil.lpop("product:" + id + ":inventory");

        if (value == null) {
            logger.error("库存不足，秒杀失败");
            throw new ServiceException("抢光了");
        } else {
            logger.info("秒杀商品成功");

            //修改redis的缓存
            Long length = RedisUtil.llen("product:" + product.getId() + ":inventory");
            product.setProductInventory(length.intValue() - 1);
            RedisUtil.setString("product:" + id, JSON.toJSONString(product));
        }
    }

    /**
     * 上传文件到七牛
     *
     * @param inputStream
     * @return
     * @throws RuntimeException
     */
    @Override
    public String uploadToQiNiu(InputStream inputStream) throws RuntimeException {
        Configuration configuration = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(qiniuAk, qiniuSK);
        String uploadToken = auth.uploadToken(qiniuBuket);

        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream), null, uploadToken);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return defaultPutRet.key;
        } catch (IOException ex) {
            throw new RuntimeException("上传文件到七牛异常", ex);
        }
    }
}
