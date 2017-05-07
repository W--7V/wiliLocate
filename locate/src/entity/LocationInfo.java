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
	
	private Double dis;
	
	private Double x;//x坐标
	
	private Double y;//y坐标
	
	private int clusterId;
	
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
	public Double getDis() {
		return dis;
	}
	public void setDis(Double dis) {
		this.dis = dis;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public int getClusterId() {
		return clusterId;
	}
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	public boolean getIsClusterCenter() {
		return isClusterCenter;
	}
	public void setIsClusterCenter(boolean isClusterCenter) {
		this.isClusterCenter = isClusterCenter;
	}
	@Override
	public int compareTo(LocationInfo o) {
		if(this.dis > o.getDis()){
			return 1;
		}
		return -1;
	}
}
