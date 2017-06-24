package test;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Test {
	static Logger logger = Logger.getLogger(Test.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		logger.setLevel(Level.DEBUG);
		logger.trace("跟踪信息");
		logger.debug("调试信息");
	}

}
