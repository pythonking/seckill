package com.karsa.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GoodsListReq implements Serializable {
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;
}
