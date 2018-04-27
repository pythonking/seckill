package com.leoIt.controller;

import com.leoIt.controller.result.AjaxResult;
import com.leoIt.controller.result.SimditorResult;
import com.leoIt.entity.Product;
import com.leoIt.service.ProductService;
import com.leoIt.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author king
 */
@Controller
public class HomeController {

    @Autowired
    private ProductService productService;


    /**
     * 抢购商品列表
     *
     * @return
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "home";
    }

    /**
     * 添加抢购商品
     *
     * @return
     */
    @GetMapping("/product/new")
    public String newProduct() {
        return "new";
    }

    @PostMapping("/product/new")
    public String newProduct(Product product, MultipartFile image) {
        if (image.isEmpty()) {
            productService.saveProduct(product, null);
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
    public String productInfo(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product";
    }


    /**
     * 秒杀商品
     *
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

    /**
     * 上传图片
     *
     * @param image
     * @return
     */
    @PostMapping("/img/upload")
    @ResponseBody
    public SimditorResult secKill(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                String url = productService.uploadToQiNiu(image.getInputStream());
                return SimditorResult.success(url);
            } catch (IOException e) {
                return SimditorResult.error();
            }
        }
        return SimditorResult.error();
    }
}
