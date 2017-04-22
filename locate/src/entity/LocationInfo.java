package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_location")
public class LocationInfo implements Comparable<LocationInfo>{
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name="id")
	private int id;
	
	private String realAddress;
	
	private Date insertDate;
	
	private String dis;
	
	private String x;//x坐标
	
	private String y;//y坐标
	
	private String cluster_id;
	
	private boolean isClusterCenter;
	
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
	public String getDis() {
		return dis;
	}
	public void setDis(String dis) {
		this.dis = dis;
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
	public String getCluster_id() {
		return cluster_id;
	}
	public void setCluster_id(String cluster_id) {
		this.cluster_id = cluster_id;
	}
	public boolean isClusterCenter() {
		return isClusterCenter;
	}
	public void setClusterCenter(boolean isClusterCenter) {
		this.isClusterCenter = isClusterCenter;
	}
	@Override
	public int compareTo(LocationInfo o) {
		if(Integer.parseInt(this.dis) > Integer.parseInt(o.getDis())){
			return 1;
		}
		return -1;
	}
}
