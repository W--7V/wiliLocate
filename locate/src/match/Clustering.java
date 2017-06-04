package match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uestc.im.here.SignalStrengthInfoDto;

import dao.LocationInfoDao;
import dao.SignalStrengthInfoDao;
import entity.LocationInfo;
import entity.SignalStrengthInfo;

//K均值聚类算法
public class Clustering {
	private Integer K = 4;
	private Integer[] clusterCenter = new Integer[this.K];//聚类中心
	private LocationInfoDao locationInfoDao = new LocationInfoDao();
	private SignalStrengthInfoDao signalStrengthInfoDao = new SignalStrengthInfoDao();
	private List<LocationInfo>list = new ArrayList<LocationInfo>();
	
	public Clustering() {//初始化聚类中心
		list = locationInfoDao.getAll();
		for(int i=0;i < K;){
			int rand = (int) (Math.random() * list.size());
			Boolean b = isRepeat(i, rand);
			if(b == true)continue;
			else{
				clusterCenter[i] = rand;
				i++;
			}
		}
	}
	
	//判断取得随机数是否重复
	private Boolean isRepeat(int j,int rand){
		Boolean b = false;
		for(int i=0;i < j;i++){
			if(clusterCenter[i] == rand)b = true;
		}
		return b;
	}
	
	//计算欧几里得距离
	public Double distance(List<SignalStrengthInfo>RSSlist1, List<SignalStrengthInfo>RSSlist2){
		Double dis = 0.0;
		List<SignalStrengthInfo> temp = new ArrayList<SignalStrengthInfo>();
		Collections.sort(RSSlist1);
		for(int k=0; k < 10;k++){
			temp.add(RSSlist1.get(k));
		}
		RSSlist1 = temp;
		for(int j=0;j < RSSlist1.size();j++){
			Double s1 = Double.parseDouble(RSSlist1.get(j).getSignalStrength());
			int flag=0;//后台Location数据中是否包含该AP信息0-否，1-是
			for(int k=0;k < RSSlist2.size();k++){
				if(RSSlist2.get(k).getMACAddress().equals(RSSlist1.get(j).getMACAddress())){
//					System.out.println(RSSlist1.get(j).getWiFiName()+"========"+RSSlist2.get(k).getWiFiName());
					Double s2 = Double.parseDouble(RSSlist2.get(k).getSignalStrength());
					dis += Math.pow((s1-s2), 2);
					flag=1;
					break;
				}
			}
			if(flag == 0){
//				System.out.println(RSSlist1.get(j).getWiFiName()+"!!!!!!!");
				dis += Math.pow(s1, 2);
			}
//			System.out.println(dis);
		}
		dis = Math.sqrt(dis);
		return dis;
	}
	
	//聚类算法
	private void cluster(){
		List<LocationInfo>list1 = new ArrayList<LocationInfo>();
		List<LocationInfo>cluster = new ArrayList<LocationInfo>();
		Double minDis;
		for(int i=0;i < this.K;i++){
			list1.add(list.get(clusterCenter[i]));
			list1.get(i).setIsClusterCenter(true);
			locationInfoDao.update(list1.get(i));
		}
		
		signalStrengthInfoDao.init();
		for(int i=0;i < list.size();i++){
			minDis = 10000.0;
			if(list.get(i).getIsClusterCenter() == true){
				list.get(i).setClusterId(list.get(i).getId());
			}else{
				for(int j=0;j < list1.size();j++){
					List<SignalStrengthInfo> RSSlist1 = signalStrengthInfoDao.getByLocationId(list.get(i).getId());
					List<SignalStrengthInfo> RSSlist2 = signalStrengthInfoDao.getByLocationId(list.get(clusterCenter[j]).getId());
					Double dis = distance(RSSlist1,RSSlist2);
					if(dis < minDis){
						list.get(i).setClusterId(list1.get(j).getId());
						minDis = dis;
					}
				}
			}
			locationInfoDao.update(list.get(i));
		}
		signalStrengthInfoDao.close();
		
		//更新聚类中心
		for (LocationInfo l1 : list1) {//获得聚类中心
			double dis = 0.0;
			double minDist = 0.0;
			LocationInfo newCenter = null;
			int flag = 0;
			
			l1.setIsClusterCenter(false);
			locationInfoDao.update(l1);
			cluster = locationInfoDao.getByClusterId(l1.getId());
			double x=0.0,y=0.0;
			for (LocationInfo l2 : cluster) {
				x += l2.getX();
				y += l2.getY();
			}
			x = x/cluster.size();
			y = y/cluster.size();
			
			minDist = realDistance(x, y, l1);
			
			for (LocationInfo l3 : cluster) {
				dis = realDistance(x, y, l3);
				if(dis < minDist){
					newCenter = l3;
					flag = 1;
				}
			}
			if(flag == 1){
				newCenter.setIsClusterCenter(true);
				for (LocationInfo locationInfo : cluster) {
					locationInfo.setClusterId(newCenter.getId());
					locationInfoDao.update(locationInfo);
				}
			}else{
				l1.setIsClusterCenter(true);
				locationInfoDao.update(l1);
			}
		}
	}
	
	public double realDistance(double x,double y, LocationInfo l){
		double dis = 0.0;
		dis += Math.pow(x-l.getX(), 2);
		dis += Math.pow(y-l.getY(), 2);
		dis = Math.sqrt(dis);
		return dis;
	}
	
	public void test(){
//		list = locationInfoDao.getAll();
		signalStrengthInfoDao.init();
		List<SignalStrengthInfo> RSSlist2 = signalStrengthInfoDao.getByLocationId(10);
		for(int i=0;i < list.size();i++){
			List<SignalStrengthInfo> RSSlist1 = signalStrengthInfoDao.getByLocationId(list.get(i).getId());
			System.out.println(distance(RSSlist1, RSSlist2));
		}
		signalStrengthInfoDao.close();
	}
	
	public static void main(String[] args){
		Clustering clustering = new Clustering();
		clustering.cluster();
//		clustering.test();
	}
	
}