package com.uestc.im.here;

import java.io.Serializable;

import entity.SignalStrengthInfo;


public class SignalStrengthInfoDto implements Serializable,Comparable<SignalStrengthInfoDto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7335001828000915857L;

	private int id;
	
	private String MACAddress;
	
	private String signalStrength;
	
	private String WiFiName;
	
	private LocationInfoDto locationDto;
	
	public SignalStrengthInfoDto(){
	}
	public SignalStrengthInfoDto(SignalStrengthInfo signalStrengthInfo) {
		this.id = signalStrengthInfo.getId();
		this.MACAddress = signalStrengthInfo.getMACAddress();
		this.signalStrength = signalStrengthInfo.getSignalStrength();
		this.WiFiName = signalStrengthInfo.getWiFiName();
		this.locationDto = new LocationInfoDto(signalStrengthInfo.getLocation());
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMACAddress() {
		return MACAddress;
	}
	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}
	public String getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}
	public String getWiFiName() {
		return WiFiName;
	}
	public void setWiFiName(String wiFiName) {
		WiFiName = wiFiName;
	}
	public LocationInfoDto getLocationInfoDto() {
		return locationDto;
	}
	public void setLocationInfoDto(LocationInfoDto locationDto) {
		this.locationDto = locationDto;
	}
	@Override
	public int compareTo(SignalStrengthInfoDto o) {
		if(Integer.parseInt(this.signalStrength) > Integer.parseInt(o.getSignalStrength())){
			return 0;
		}
		return 1;
	}
	
}
