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
	
	public Match(){
		this.minDis = 10000000000.0;
		this.locationInfoDao = new LocationInfoDao();
		this.signalStrengthInfoDao = new SignalStrengthInfoDao();
	}
	
	//Nearest Neighbor
	public void NN(DataTransmissionObject dto){
		List<SignalStrengthInfoDto> RSSlist1 = new ArrayList<SignalStrengthInfoDto>(dto.getSignalStrengthInfoDto());
		Collections.sort(RSSlist1);
//		for(int i=0;i < RSSlist1.size();i++){
//			System.out.println(RSSlist1.get(i).getSignalStrength());
//		}
		while(RSSlist1.size() > 6){
			RSSlist1.remove(RSSlist1.size()-1);
		}
		List<LocationInfo>list = locationInfoDao.getAll();
		int n = list.size();
		for(int i=0;i < n;i++){
			LocationInfo l = list.get(i);
			this.currentDis=0.0;
			for(int j=0;j < RSSlist1.size();j++){
					
				List<SignalStrengthInfo> RSSlist2 = signalStrengthInfoDao.getByLocationId(l.getId());
				
				Double s1 = Double.parseDouble(RSSlist1.get(j).getSignalStrength());
				int flag=0;//后台Location数据中是否包含该AP信息0-否，1-是
				for(int k=0;k < RSSlist2.size();k++){
					if(RSSlist2.get(k).getMACAddress().equals(RSSlist1.get(j).getMACAddress())){
						Double s2 = Double.parseDouble(RSSlist2.get(k).getSignalStrength());
						this.currentDis += Math.pow((s1-s2), 2);
						flag=1;
						break;
					}
				}
				if(flag == 0){
					this.currentDis += Math.pow(s1, 2);
				}
			}
			this.currentDis = Math.sqrt(this.currentDis);
			l.setDis(this.currentDis.toString());
			locationInfoDao.update(l);
			if(this.currentDis < this.minDis){
				this.minDis = this.currentDis;
				this.locateResult = l;
			}
		}
		dto.setLocationInfoDto(new LocationInfoDto(locateResult));
		System.out.println("客户端所在位置"+locateResult.getRealAddress());
	}
	
}
