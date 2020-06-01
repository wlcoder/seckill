package com.wl.sec_kill.service;

import com.wl.sec_kill.mapper.SKDao;
import org.springframework.stereotype.Service;

@Service
public class SKService {
    private SKDao skDao = new SKDao();
    public void processSeckill(){
        Integer count = skDao.getCount();
        if(count > 0 ){
            System.out.println("恭喜您，你获得了购买的权利");
            count = count -1;
            skDao.updateCount(count);
        }else{
            System.out.println("抱歉，商品已经被抢购完了");
        }

    }

}
