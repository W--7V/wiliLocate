package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import entity.SignalStrengthInfo;

public class SignalStrengthInfoDao {
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
		this.session.flush();
		this.session.close();
		this.sessionFactory.close();
	}
	
	public void save(SignalStrengthInfo signalStrengthInfo){
		this.init();
		this.session.save(signalStrengthInfo);
		this.session.getTransaction().commit();
		this.close();
	}
	
	public SignalStrengthInfo get(int id){
		this.init();
		SignalStrengthInfo signalStrengthInfo = (SignalStrengthInfo) this.session.get(SignalStrengthInfo.class, id);
		this.close();
		return signalStrengthInfo;
	}
	
	public List<SignalStrengthInfo> getByLocationId(Integer id){
		this.init();
		List<SignalStrengthInfo>list = new ArrayList<SignalStrengthInfo>();
		String hql = "From SignalStrengthInfo WHERE location_id = '"+id.toString()+"'";
		Query q = this.session.createQuery(hql);
		list = q.list();
		this.close();
		return list;
	}

}
