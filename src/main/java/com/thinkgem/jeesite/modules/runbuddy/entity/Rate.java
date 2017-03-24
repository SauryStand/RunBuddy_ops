package com.thinkgem.jeesite.modules.runbuddy.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @description:
 * @Author: Johnny Chou
 * @Create: 2017-02-21-0:30
 * modifiy in 2017.03.18
 **/

public class Rate extends DataEntity<Rate> {
    private String realRate;
    private String status;
    private Date recordTime;

    public Rate(){super();}


    public String getRealRate() {
        return realRate;
    }

    public void setRealRate(String realRate) {
        this.realRate = realRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String toString(){
        return "-->>"+getRealRate()+"&&"+getStatus();
    }


    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}
