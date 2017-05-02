package com.uestc.im.here;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTransmissionObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2470114150131911354L;
	private Integer operationCode;//1:存入数据库，2:待匹配数据，3:匹配成功，返回匹配结果，4:匹配失败，返回错误报告！5:离线数据保存成功!6:清空离线数据！7:清空离线数据成功！
	private LocationInfoDto locationInfoDto;
	private List<SignalStrengthInfoDto> signalStrengthInfoDto;
	private String report;
	
	public DataTransmissionObject(){
		this.locationInfoDto = new LocationInfoDto();
		this.signalStrengthInfoDto = new ArrayList<SignalStrengthInfoDto>();
	}
	public LocationInfoDto getLocationInfoDto() {
		return locationInfoDto;
	}
	public void setLocationInfoDto(LocationInfoDto locationInfoDto) {
		this.locationInfoDto = locationInfoDto;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public Integer getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(Integer operationCode) {
		this.operationCode = operationCode;
	}
	public List<SignalStrengthInfoDto> getSignalStrengthInfoDto() {
		return signalStrengthInfoDto;
	}
	public void setSignalStrengthInfoDto(List<SignalStrengthInfoDto> signalStrengthInfoDto) {
		this.signalStrengthInfoDto = signalStrengthInfoDto;
	}
	
}
