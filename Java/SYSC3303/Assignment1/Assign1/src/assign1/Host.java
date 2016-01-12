package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.xml.crypto.Data;

public class Host {
	
	public static final int IN_PORT = 68;
	public static final int OUT_PORT = 69;
	public static final int BUFSIZ = 1024;

	private DatagramSocket receiveSocket;
	private DatagramSocket genSocket;
	
	public Host(){
		try{
			receiveSocket = new DatagramSocket(IN_PORT);
			genSocket = new DatagramSocket();
		}catch(Exception e){
			
		}
		
	}
	
	private void forward(){
		byte[] buf = new byte[BUFSIZ];
		byte[] inbuf;
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try{
			receiveSocket.receive(packet);
			inbuf = packet.getData();
			
			System.out.println("Bytes:" + inbuf);
			System.out.println("String: " + inbuf.toString());
			
		}catch(Exception e){
			
		}
	}
	
}
