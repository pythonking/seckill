package com.karsa.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TangshiInfo {
    private Integer id;
    private String contents;
    private String type;
    private String author;
    private String title;
}
