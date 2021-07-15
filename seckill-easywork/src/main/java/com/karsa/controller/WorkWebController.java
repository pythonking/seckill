package com.karsa.controller;


import com.alibaba.excel.EasyExcel;
import com.karsa.dao.UploadDAO;
import com.karsa.entity.DownloadData;
import com.karsa.entity.UploadData;
import com.karsa.enums.ErrorCode;
import com.karsa.listener.UploadDataListener;
import com.karsa.service.IGoodExcelService;
import com.karsa.utils.Result;
import com.karsa.utils.ResultUtils;
import com.karsa.vo.excel.GoodsExcelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 工具类
 */
@RestController
@RequestMapping("/work")
public class WorkWebController {

    @Autowired
    private UploadDAO uploadDAO;

    @Autowired
    private IGoodExcelService goodExcelService;

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <p>2. 设置返回的 参数
     * <p>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("商品列表", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), GoodsExcelVo.class).sheet("商品1").doWrite(goodExcelService.listGoodsExcel());
    }

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public Result upload(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), GoodsExcelVo.class, new UploadDataListener(goodExcelService)).sheet().doRead();
        } catch (IOException e) {
            return ResultUtils.error(ErrorCode.FAIL);
        }
        return ResultUtils.success();
    }

}

