package socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.uestc.im.here.DataTransmissionObject;

import dao.LocationInfoDao;
import util.InputFromKeyboard;

public class Client {
	public static final String IP_ADDR = "localhost";
	public static final int PORT = 58400;
	public InputFromKeyboard input = new InputFromKeyboard();
	public LocationInfoDao locationInfoDao = new LocationInfoDao();
	
	public void communication() throws InterruptedException{
		System.out.println("客户端启动...");
		while(true){
			
			Socket socket = null;
			
			try {
				socket = new Socket(IP_ADDR, PORT);
				DataTransmissionObject dto = new DataTransmissionObject();
				
				
				//写数据
				System.out.println("请输入：");
				ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				int i = input.getInt();
//				dto.setLocationInfoDto(locationInfoDao.get(i));
				dto.setOperationCode(2);
				output.writeObject(dto);
				output.flush();
				
				//匹配结果
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				dto = (DataTransmissionObject) input.readObject();
				if(dto.getOperationCode() == 3){
					System.out.println("匹配成功!");
//					System.out.println("服务端返回的消息："+dto.getLocationInfo().getRealAddress());
				}else if(dto.getOperationCode() == 4){
					System.out.println("匹配失败！");
					System.out.println("错误报告:"+dto.getReport());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally{
				if(socket != null){
					try {
						socket.shutdownInput();
						socket.shutdownOutput();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Client client = new Client();
		client.communication();
	}
}
