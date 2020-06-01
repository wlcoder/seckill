package com.wl.sec_kill.mapper;

public class SKDao {
    public static Integer count = 10;
    public Integer getCount(){
        return SKDao.count;
    }

    public void updateCount(Integer count){
        SKDao.count = count;
    }
}
