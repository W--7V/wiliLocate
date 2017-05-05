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
	
	private Boolean isRepeat(int j,int rand){
		Boolean b = false;
		for(int i=0;i < j;i++){
			if(clusterCenter[i] == rand)b = true;
		}
		return b;
	}
	
	public static void main(String[] args){
		Clustering clustering = new Clustering();
	}
	
}
