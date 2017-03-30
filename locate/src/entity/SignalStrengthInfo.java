package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_signalstrength")
public class SignalStrengthInfo{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	@Column(name="id")
	private int id;
	
	private String MACAddress;
	
	private String signalStrength;
	
	private String WiFiName;
	
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=LocationInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="location_id")
	private LocationInfo location;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMACAddress() {
		return MACAddress;
	}
	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}
	public String getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}
	public String getWiFiName() {
		return WiFiName;
	}
	public void setWiFiName(String wiFiName) {
		WiFiName = wiFiName;
	}
	public LocationInfo getLocation() {
		return location;
	}
	public void setLocation(LocationInfo location) {
		this.location = location;
	}
	
}
