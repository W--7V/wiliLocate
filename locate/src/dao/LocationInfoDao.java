package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import entity.LocationInfo;

public class LocationInfoDao {
	private Configuration configuration;
	private ServiceRegistry serviceRegistry;
	private SessionFactory sessionFactory;
	private Session session;
	
	public void init(){
		this.configuration = new Configuration().configure();
		this.serviceRegistry =  new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		this.session = sessionFactory.openSession();
		this.session.beginTransaction();
	}
	
	public void close(){
		this.session.getTransaction().commit();
		this.session.flush();
		this.session.close();
		this.sessionFactory.close();
	}
	
	public LocationInfo save(LocationInfo location){
		this.init();
		location.setInsertDate(new Date());
		this.session.save(location);
		this.close();
		return location;
	}
	
	public LocationInfo update(LocationInfo locationInfo){
		this.init();
		this.session.update(locationInfo);
		this.close();
		return locationInfo;
	}
	
	public LocationInfo get(int id){
		this.init();
		LocationInfo location = (LocationInfo) this.session.get(LocationInfo.class, id);
		this.close();
		return location;
	}
	
	public List<LocationInfo> getAll(){
		this.init();
		List<LocationInfo> list = new ArrayList<LocationInfo>();
		String hql = "From LocationInfo";
		Query query =  this.session.createQuery(hql);
		list = query.list();
		this.close();
		return list;
	}
	
	public List<LocationInfo> getClusterCenter(){
		this.init();
		List<LocationInfo> list = new ArrayList<LocationInfo>();
		String hql = "From LocationInfo where isClusterCenter = 1";
		Query query =  this.session.createQuery(hql);
		list = query.list();
		this.close();
		return list;
	}
	
	public void deleteAll(){
		this.init();
		String hql = "Delete LocationInfo";
		Query query =  this.session.createQuery(hql);
		query.executeUpdate();
		this.close();
	}
}
