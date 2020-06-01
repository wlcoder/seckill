package com.wl.sec_kill.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Evaluate {
    private Long evaluateId;
    private String content;
    private Date createTime;
    private Integer stars;
    private Long goodsId;

}
