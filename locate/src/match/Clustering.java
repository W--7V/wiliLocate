package match;

import java.util.List;

import dao.LocationInfoDao;
import entity.LocationInfo;

//K均值聚类算法
public class Clustering {
	private Integer K = 4;
	private Integer[] clusterCenter = new Integer[this.K];//聚类中心
	private LocationInfoDao locationInfoDao = new LocationInfoDao();
	private List<LocationInfo>list;
	
	public Clustering() {//初始化聚类中心
		list = locationInfoDao.getAll();
		for(int i=0;i < K;i++){
//			System.out.println(Math.r);
			clusterCenter[i] = (int) Math.random()/list.size();
			System.out.println(clusterCenter[i]);
		}
	}
	
	public static void main(String[] args){
		Clustering clustering = new Clustering();
	}
	
}
