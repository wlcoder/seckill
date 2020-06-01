package com.wl.sec_kill.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wl
 * @Description :
 * @date : 2020/5/30 14:46
 */
@Data
public class Goods implements Serializable {
    private Long goodsId;
    private String title;
    private String subTitle;
    private Float originalCost;
    private Float currentPrice;
    private Float discount;
    private Integer isFreeDelivery;
    private Long categoryId;
}
