package com.uestc.im.here;

import java.io.Serializable;
import java.util.Date;

import entity.LocationInfo;

public class LocationInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6412723607832332394L;

	private int id;
	
	private String realAddress;
	
	private Date insertDate;
	
	private Double x;//x坐标
	
	private Double y;//y坐标
	
	public LocationInfoDto(){
	}
	public LocationInfoDto(LocationInfo locationInfo){
		this.id = locationInfo.getId();
		this.realAddress = locationInfo.getRealAddress();
		this.insertDate = locationInfo.getInsertDate();
		this.x = locationInfo.getX();
		this.y = locationInfo.getY();
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRealAddress() {
		return realAddress;
	}
	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
}
