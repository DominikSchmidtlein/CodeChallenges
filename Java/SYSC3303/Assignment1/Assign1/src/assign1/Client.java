package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	
	public static final int HOST_PORT = 68;
	public static final int SERVER_PORT = 69;
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	public Client(){
		try{
			socket = new DatagramSocket();
			
			byte[] buf = concatenateArrays(new byte[]{01}, "test.txt".getBytes());
			buf = concatenateArrays(buf, new byte[]{0});
			buf = concatenateArrays(buf, "octet".getBytes());
			buf = concatenateArrays(buf, new byte[]{0});
			
			packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), HOST_PORT);

			System.out.println("Sending: " + packet.getData() + ", " + new String(packet.getData()));
			
			socket.send(packet);
			
			socket.receive(packet);
			
			System.out.println("Received: " + packet.getData() + ", " + new String(packet.getData()));
			
			socket.close();
			
		}catch(Exception e){	
		}
	}
	
	private byte[] concatenateArrays(byte[] a, byte[] b){
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	public static void main(String[] args) {
		new Client();
	}
	
}
