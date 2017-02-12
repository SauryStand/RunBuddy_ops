package com.runbuddy.dao.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @description: HeartRate实体类
 * @Author: Johnny Chou
 * @Create: 2017-02-12-19:28
 **/

public class HeartRateDao extends DataEntity<HeartRateDao> {
    /**
     * 这样的命名法不符合java的规范，但是为了配合数据库命名，目前决定这样命名实体类
     */
    private String highest_rate;//
    private String lowest_rate;
    private String average_rate;
    private int motion_state;
    private int recommend_state;
    private int execise_time;
    private int execise_load;
    private Date record_date;

    public HeartRateDao(){}


    public String getHighest_rate() {
        return highest_rate;
    }

    public void setHighest_rate(String highest_rate) {
        this.highest_rate = highest_rate;
    }

    public String getLowest_rate() {
        return lowest_rate;
    }

    public void setLowest_rate(String lowest_rate) {
        this.lowest_rate = lowest_rate;
    }

    public String getAverage_rate() {
        return average_rate;
    }

    public void setAverage_rate(String average_rate) {
        this.average_rate = average_rate;
    }

    public int getRecommend_state() {
        return recommend_state;
    }

    public void setRecommend_state(int recommend_state) {
        this.recommend_state = recommend_state;
    }

    public int getMotion_state() {
        return motion_state;
    }

    public void setMotion_state(int motion_state) {
        this.motion_state = motion_state;
    }

    public int getExecise_time() {
        return execise_time;
    }

    public void setExecise_time(int execise_time) {
        this.execise_time = execise_time;
    }

    public int getExecise_load() {
        return execise_load;
    }

    public void setExecise_load(int execise_load) {
        this.execise_load = execise_load;
    }

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        this.record_date = record_date;
    }
}
