package match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.loader.custom.Return;

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
	public DataTransmissionObject NN(DataTransmissionObject dto){
		List<SignalStrengthInfoDto> RSSlist1 = new ArrayList<SignalStrengthInfoDto>(dto.getSignalStrengthInfoDto());
		List<SignalStrengthInfoDto> temp = new ArrayList<SignalStrengthInfoDto>();
		Collections.sort(RSSlist1);
		for(int i=0; i < 4;i++){
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
				System.out.println(this.currentDis);
			}
			this.currentDis = Math.sqrt(this.currentDis);
			l.setDis(this.currentDis.toString());
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
	
}
