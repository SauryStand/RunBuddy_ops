package com.thinkgem.jeesite.modules.runbuddy.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @description: 实时上传的心率实体类
 * @Author: Johnny Chou
 * @Create: 2017-02-19-13:36
 **/

public class RealTimeRate extends DataEntity<RealTimeRate> {

    private String realTimeRate;
    private Date uploadTime;
    private Date recordTime;
    private static final long serialVersionUID = 1L;

    public RealTimeRate(){
        super();
    }


    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }


    public String getRealTimeRate() {
        return realTimeRate;
    }

    public void setRealTimeRate(String realTimeRate) {
        this.realTimeRate = realTimeRate;
    }
}
