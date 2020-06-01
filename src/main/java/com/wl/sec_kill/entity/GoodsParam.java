package com.wl.sec_kill.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsParam implements Serializable {
    private Long gpId;
    private String gpParamName;
    private String gpParamValue;
    private Long goodsId;
    private Integer gpOrder;

}
