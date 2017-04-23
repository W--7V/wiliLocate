package match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uestc.im.here.DataTransmissionObject;
import com.uestc.im.here.LocationInfoDto;
import com.uestc.im.here.SignalStrengthInfoDto;

import dao.LocationInfoDao;
import dao.SignalStrengthInfoDao;
import entity.LocationInfo;
import entity.SignalStrengthInfo;

//匹配算法
public class Match {
	private Double minDis;
	private Double currentDis;
	private LocationInfoDao locationInfoDao;
	private LocationInfo locateResult;
	private SignalStrengthInfoDao signalStrengthInfoDao;
	private Integer K;
	private Double x,y;
	
	public Match(){
		this.minDis = 10000000000.0;
		this.locateResult = new LocationInfo();
		this.locationInfoDao = new LocationInfoDao();
		this.signalStrengthInfoDao = new SignalStrengthInfoDao();
		this.K = 4;
		this.x=0.0;
		this.y=0.0;
	}
	
	//Nearest Neighbor
	public DataTransmissionObject NN(DataTransmissionObject dto){
		List<SignalStrengthInfoDto> RSSlist1 = new ArrayList<SignalStrengthInfoDto>(dto.getSignalStrengthInfoDto());
		List<SignalStrengthInfoDto> temp = new ArrayList<SignalStrengthInfoDto>();
		Collections.sort(RSSlist1);
		for(int i=0; i < 10;i++){
			temp.add(RSSlist1.get(i));
		}
		if(Integer.parseInt(temp.get(2).getSignalStrength()) < -80){
			dto.setOperationCode(4);
			dto.setReport("当前位置距离周围无线热点较远，定位误差较大！");
			return dto;
		}
		RSSlist1 = temp;
		List<LocationInfo>list = locationInfoDao.getAll();
		int n = list.size();
		signalStrengthInfoDao.init();
		for(int i=0;i < n;i++){
			LocationInfo l = list.get(i);
			List<SignalStrengthInfo> RSSlist2 = signalStrengthInfoDao.getByLocationId(l.getId());
			this.currentDis = distance(RSSlist1, RSSlist2);
			l.setDis(this.currentDis);
			locationInfoDao.update(l);
			if(this.currentDis < this.minDis){
				this.minDis = this.currentDis;
				this.locateResult = l;
			}
		}
		signalStrengthInfoDao.close();
		dto.setLocationInfoDto(new LocationInfoDto(locateResult));
		System.out.println("客户端所在位置:"+locateResult.getRealAddress());
		return dto;
	}
	
	//K-Nearest Neighbor
	public DataTransmissionObject KNN(DataTransmissionObject dto){
		List<LocationInfo> minDisList = new ArrayList<LocationInfo>();
		List<SignalStrengthInfoDto> RSSlist1 = new ArrayList<SignalStrengthInfoDto>(dto.getSignalStrengthInfoDto());
		List<SignalStrengthInfoDto> temp = new ArrayList<SignalStrengthInfoDto>();
		Collections.sort(RSSlist1);
		for(int i=0; i < 10;i++){
			temp.add(RSSlist1.get(i));
		}
		if(Integer.parseInt(temp.get(2).getSignalStrength()) < -80){
			dto.setOperationCode(4);
			dto.setReport("当前位置距离周围无线热点较远，定位误差较大！");
			return dto;
		}
		RSSlist1 = temp;
		
		//获取离线数据
		List<LocationInfo>list = locationInfoDao.getAll();
		int n = list.size();
		signalStrengthInfoDao.init();
		
		for(int i=0;i < this.K;i++){
			LocationInfo l = list.get(i);
			List<SignalStrengthInfo> RSS = signalStrengthInfoDao.getByLocationId(l.getId());
			l.setDis(distance(RSSlist1, RSS));
			minDisList.add(list.get(i));
		}
		Collections.sort(minDisList);
		
		//开始匹配
		for(int i=this.K;i < n;i++){
			LocationInfo l = list.get(i);
			List<SignalStrengthInfo> RSSlist2 = signalStrengthInfoDao.getByLocationId(l.getId());
			this.currentDis = distance(RSSlist1, RSSlist2);
			l.setDis(this.currentDis);
			locationInfoDao.update(l);
			
			if(this.currentDis < minDisList.get(this.K-1).getDis()){
				minDisList.set(this.K-1, l);
				Collections.sort(minDisList);
			}
		}
		signalStrengthInfoDao.close();
		for (LocationInfo locationInfo : minDisList) {
			this.x += locationInfo.getX();
			this.y += locationInfo.getY();
		}
		locateResult.setX(this.x/this.K);
		locateResult.setX(this.y/this.K);
		dto.setLocationInfoDto(new LocationInfoDto(locateResult));
		System.out.println("客户端所在位置:"+locateResult.getRealAddress());
		return dto;
	}
	
	//计算欧几里得距离
	public Double distance(List<SignalStrengthInfoDto>RSSlist1, List<SignalStrengthInfo>RSSlist2){
		Double dis = 0.0;
		for(int j=0;j < RSSlist1.size();j++){
			Double s1 = Double.parseDouble(RSSlist1.get(j).getSignalStrength());
			int flag=0;//后台Location数据中是否包含该AP信息0-否，1-是
			for(int k=0;k < RSSlist2.size();k++){
				if(RSSlist2.get(k).getMACAddress().equals(RSSlist1.get(j).getMACAddress())){
					Double s2 = Double.parseDouble(RSSlist2.get(k).getSignalStrength());
					dis += Math.pow((s1-s2), 2);
					flag=1;
					break;
				}
			}
			if(flag == 0){
				dis += Math.pow(s1, 2);
			}
		}
		dis = Math.sqrt(dis);
		return dis;
	}
}
