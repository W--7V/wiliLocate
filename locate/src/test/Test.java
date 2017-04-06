package test;

import java.util.List;

import dao.LocationInfoDao;
import dao.SignalStrengthInfoDao;
import entity.LocationInfo;

public class Test {

	public static void main(String[] args) {
		LocationInfoDao dao2 = new LocationInfoDao();
		List<LocationInfo>list = dao2.getAll();
		System.out.println(list.get(0).getRealAddress());
	}

}
