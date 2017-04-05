package test;

import dao.LocationInfoDao;
import dao.SignalStrengthInfoDao;

public class Test {

	public static void main(String[] args) {
//		SignalStrengthInfoDao dao = new SignalStrengthInfoDao();
//		dao.deleteAll();
		LocationInfoDao dao = new LocationInfoDao();
		dao.deleteAll();
	}

}
