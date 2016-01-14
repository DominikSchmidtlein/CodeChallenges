package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
	
	public static final int SERVER_PORT = 69;
	
	private DatagramSocket socket;

	public Server(){
		try {
			socket = new DatagramSocket(SERVER_PORT);
			
			byte[] buf = new byte[64];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			socket.receive(packet);
			
			buf = shrinkByteArray(packet.getData(), getLength(packet.getData()));
			
			System.out.println("Received: " + buf + ", " + new String(buf));
			
			buf = "Hi".getBytes();
			packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), packet.getPort());
			
			System.out.println("Sending: " + packet.getData() + ", " + new String(packet.getData()));
			
			DatagramSocket tempSocket = new DatagramSocket();
			tempSocket.send(packet);
			tempSocket.close();
			
			socket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private int getLength(byte[] data){
		int nulls = 0;
		for(int i = 0; i < data.length; i ++){
			if(data[i] == 0)
				nulls += 1;
			if(nulls >= 3)
				return i;
		}
		return -1;
	}
	
	private byte[] shrinkByteArray(byte[] large, int length){
		byte[] small = new byte[length];
		for(int i = 0; i < length; i ++)
			small[i] = large[i];
		return small;
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}
