package com.kaishengit.controller;

import com.kaishengit.controller.result.AjaxResult;
import com.kaishengit.entity.Product;
import com.kaishengit.service.ProductService;
import com.kaishengit.service.exception.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author fankay
 */
@Controller
public class HomeController {

    @Autowired
    private ProductService productService;


    /**
     * 抢购商品列表
     * @return
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList",productList);
        return "home";
    }

    /**
     * 添加抢购商品
     * @return
     */
    @GetMapping("/product/new")
    public String newProduct() {
        return "new";
    }

    @PostMapping("/product/new")
    public String newProduct(Product product, MultipartFile image,String sTime,String eTime) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime startDateTime = DateTime.parse(sTime,formatter);
        DateTime endDateTime = DateTime.parse(eTime,formatter);

        product.setStartTime(startDateTime.toDate());
        product.setEndTime(endDateTime.toDate());

        if(image.isEmpty()) {
            productService.saveProduct(product,null);
        } else {
            try {
                productService.saveProduct(product, image.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    /**
     * 查看商品详情
     */
    @GetMapping("/product/{id:\\d+}")
    public String productInfo(@PathVariable Integer id,Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "product";
    }


    /**
     * 秒杀商品
     * @param id
     * @return
     */
    @GetMapping("/product/seckill/{id:\\d+}")
    @ResponseBody
    public AjaxResult secKill(@PathVariable Integer id) {
        try {
            productService.secKill(id);
            return AjaxResult.success();
        } catch (ServiceException ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }



}
