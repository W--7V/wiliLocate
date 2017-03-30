package test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.InputFromKeyboard;
import dao.LocationInfoDao;
import dao.SignalStrengthInfoDao;
import entity.LocationInfo;
import entity.SignalStrengthInfo;

public class HibernateTest {
	
	public InputFromKeyboard in;
	public LocationInfoDao locationInfoDao;
	private SignalStrengthInfoDao signalStrengthInfoDao;
	
	public HibernateTest(){
		this.in = new InputFromKeyboard();
		this.locationInfoDao = new LocationInfoDao();
		this.signalStrengthInfoDao = new SignalStrengthInfoDao();
	}
	
	public void getLocationInfo(){
		LocationInfo location = new LocationInfo();
		System.out.println("输入查询ID:");
		location = locationInfoDao.get(in.getInt());
		System.out.println(location.getRealAddress());
	}
	
	public void saveLocationinfo(){
		LocationInfo location = new LocationInfo();
		System.out.println("输入位置信息:");
		location.setRealAddress(in.getString());
		LocationInfo l = this.locationInfoDao.save(location);
		System.out.println(l.getId());
	}
	
	public void getSignalStrengthInfo(){
		SignalStrengthInfo signalStrengthInfo = new SignalStrengthInfo();
		System.out.println("输入查询ID:");
		signalStrengthInfo = signalStrengthInfoDao.get(in.getInt());
		System.out.println(signalStrengthInfo.getLocation().getRealAddress());
	}
	
	public void saveSignalStrengthInfo(){
		SignalStrengthInfo signalStrengthInfo = new SignalStrengthInfo();
		signalStrengthInfo.setMACAddress("12 34 56 78 90 3");
		signalStrengthInfo.setSignalStrength("47");
		signalStrengthInfo.setLocation(locationInfoDao.get(1));
		this.signalStrengthInfoDao.save(signalStrengthInfo);
	}
	
	public static void main(String[] args){
		HibernateTest test = new HibernateTest();
		InputFromKeyboard input = new InputFromKeyboard();
		
		for(String inputString = input.getString(); !"quit".equals(inputString); inputString = input.getString()){
			if("1".equals(inputString)){
				test.getLocationInfo();
			}else if("2".equals(inputString)){
				test.saveLocationinfo();
			}else if("3".equals(inputString)){
				test.getSignalStrengthInfo();
			}else if("4".equals(inputString)){
				test.saveSignalStrengthInfo();
			}else if("5".equals(inputString)){
				List<LocationInfo>list = test.locationInfoDao.getAll();
				for (LocationInfo locationInfo : list) {
					System.out.println(locationInfo.getRealAddress());
				}
			}else if("q".equals(inputString)){
				break;
			}
		}
	}
}