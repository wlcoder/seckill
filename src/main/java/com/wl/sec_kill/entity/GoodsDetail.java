package com.wl.sec_kill.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDetail  implements Serializable {
    private Long gdId;
    private Long goodsId;
    private String gdPicUrl;
    private Integer gdOrder;

}
