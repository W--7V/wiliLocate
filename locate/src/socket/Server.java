package socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import util.ObjectHelper;
import match.Match;

import com.uestc.im.here.DataTransmissionObject;
import com.uestc.im.here.SignalStrengthInfoDto;

import dao.LocationInfoDao;
import dao.SignalStrengthInfoDao;
import entity.LocationInfo;
import entity.SignalStrengthInfo;

public class Server {

	public static final int PORT = 58400;
	
	public void init(){
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			
			while(true){
				Socket client = serverSocket.accept();
				new HandlerThread(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		System.out.println("服务器启动...");
		Server server = new Server();
		server.init();
	}
}

class HandlerThread implements Runnable{
	private Socket socket;
	private Match match;

	public HandlerThread(Socket client) {
		match = new Match();
		socket = client;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			LocationInfoDao locationInfoDao = new LocationInfoDao();
			SignalStrengthInfoDao signalStrengthInfoDao = new SignalStrengthInfoDao();
			
			//读数据
			ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream())); 
			DataTransmissionObject dto = (DataTransmissionObject) objectInputStream.readObject();
			socket.shutdownInput();
			
			if(dto.getOperationCode() == 1){//保存离线数据
				LocationInfo l = new LocationInfo();
				int flag = 1;
				if(ObjectHelper.isEmpty(dto.getLocationInfoDto().getRealAddress()) || ObjectHelper.isEmpty(dto.getLocationInfoDto().getX()) ||ObjectHelper.isEmpty(dto.getLocationInfoDto().getY())){
					dto.setReport("位置信息不能为空！");
					dto.setOperationCode(4);
					flag = 0;
				}
				if(flag == 1){
					l.setRealAddress(dto.getLocationInfoDto().getRealAddress());
					l.setX(dto.getLocationInfoDto().getX());
					l.setY(dto.getLocationInfoDto().getY());
					l = locationInfoDao.save(l);
					signalStrengthInfoDao.init();
					for (SignalStrengthInfoDto s : dto.getSignalStrengthInfoDto()) {
						SignalStrengthInfo signalStrengthInfo = new SignalStrengthInfo();
						signalStrengthInfo.setLocation(l);
						signalStrengthInfo.setMACAddress(s.getMACAddress());
						signalStrengthInfo.setWiFiName(s.getWiFiName());
						signalStrengthInfo.setSignalStrength(s.getSignalStrength());
						signalStrengthInfoDao.save(signalStrengthInfo);
					}
					signalStrengthInfoDao.close();
					dto.setOperationCode(5);
				}
			}else if(dto.getOperationCode() == 2){//调用匹配算法
				if(dto.getSignalStrengthInfoDto().size() < 4){
					dto.setOperationCode(4);
					dto.setReport("待匹配点WiFi数量太少，无法匹配！");
				}else{
					dto = match.KNN(dto);
					dto.setOperationCode(3);
				}
			}else if(dto.getOperationCode() == 6){
				locationInfoDao.deleteAll();
				signalStrengthInfoDao.deleteAll();
				dto.setOperationCode(7);
			}
			
			//写数据
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(dto);
			output.flush();
			
			objectInputStream.close();
			output.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("服务器异常！");
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}