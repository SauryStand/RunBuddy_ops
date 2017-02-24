package com.thinkgem.jeesite.modules.runbuddy.entity;

/**
 * @description:
 * @Author: Johnny Chou
 * @Create: 2017-02-21-0:30
 **/

public class Rate {
    private String realRate;
    private String status;

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
}
