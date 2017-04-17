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
	
	private String x;
	
	private String y;
	
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
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
}
