package com.leoIt.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xieyu on 2018/5/14.
 */
@Component("myCronJob")
public class MyCronJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(MyCronJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        System.out.println("【" + time + "】定时任务第【" + time + "】次执行……");
    }
}