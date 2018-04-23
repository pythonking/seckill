package com.kaishengit.service;

import com.kaishengit.entity.Product;
import com.kaishengit.service.exception.ServiceException;

import java.io.InputStream;
import java.util.List;

/**
 * @author fankay
 */
public interface ProductService {

    /**
     * 添加商品
     * @param product
     * @param inputStream
     */
    void saveProduct(Product product, InputStream inputStream);

    /**
     * 显示全部抢购商品
     * @return
     */
    List<Product> findAll();

    /**
     * 根据主键查询商品
     * @param id
     * @return
     */
    Product findById(Integer id);

    /**
     * 秒杀商品
     * @param id
     * @throws ServiceException
     */
    void secKill(Integer id) throws ServiceException;

    /**
     * 上传文件到七牛
     *
     * @param inputStream
     * @return
     * @throws RuntimeException
     */
    String uploadToQiNiu(InputStream inputStream) throws RuntimeException;
}
