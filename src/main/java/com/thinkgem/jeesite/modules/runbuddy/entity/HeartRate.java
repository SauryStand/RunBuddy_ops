package com.thinkgem.jeesite.modules.runbuddy.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.validator.constraints.Length;

/**
 * @description: HeartRate实体类
 * @Author: Johnny Chou
 * @Create: 2017-02-12-19:28
 **/
public class HeartRate extends DataEntity<HeartRate> {

	private static final long serialVersionUID = 1L;
	private String highestRate;// HIGHEST_RATE
	private String lowestRate;
	private String averageRate;
	private int motionState;
	private int recommendState;
	private int execiseTime;
	private int execiseLoad;
	private Date recordDate;

	public HeartRate() {
		super();
	}

	public HeartRate(String id) {
		super(id);
	}

	public String toString() {
		return getHighestRate() + "," + getLowestRate() + ","
				+ getAverageRate() + "," + getMotionState() + ","
				+ getRecommendState() + "," + getExeciseLoad() + ","
				+ getRecordDate();
	}

	public String getHighestRate() {
		return highestRate;
	}

	public void setHighestRate(String highestRate) {
		this.highestRate = highestRate;
	}

	public String getLowestRate() {
		return lowestRate;
	}

	public void setLowestRate(String lowestRate) {
		this.lowestRate = lowestRate;
	}

	public String getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(String averageRate) {
		this.averageRate = averageRate;
	}

	public int getMotionState() {
		return motionState;
	}

	public void setMotionState(int motionState) {
		this.motionState = motionState;
	}

	public int getRecommendState() {
		return recommendState;
	}

	public void setRecommendState(int recommendState) {
		this.recommendState = recommendState;
	}

	public int getExeciseTime() {
		return execiseTime;
	}

	public void setExeciseTime(int execiseTime) {
		this.execiseTime = execiseTime;
	}

	public int getExeciseLoad() {
		return execiseLoad;
	}

	public void setExeciseLoad(int execiseLoad) {
		this.execiseLoad = execiseLoad;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	


}
