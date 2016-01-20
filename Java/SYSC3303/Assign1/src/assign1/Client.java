package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	public static final int HOST_PORT = 68;
	public static final int SERVER_PORT = 69;
	public static final int BUFSIZ = 1024;

	private DatagramSocket socket;
	private DatagramPacket packet;

	public Client(){
		try{
			byte[] buf;
			for(int i = 0; i < 11; i++){
				socket = new DatagramSocket();

				buf = new byte[]{0, (byte) (i%2+1)};
				buf = concatenateArrays(buf, "test.txt".getBytes());
				buf = concatenateArrays(buf, new byte[]{0});
				buf = concatenateArrays(buf, "octet".getBytes());
				buf = concatenateArrays(buf, new byte[]{0});

				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), HOST_PORT);

				System.out.println("Sending: " + getStringOfBytes(buf) + ", " + new String(buf));

				socket.send(packet);

				buf = new byte[BUFSIZ];
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				buf = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), packet.getOffset(), buf, 0, packet.getLength());

				System.out.println("Received: " + getStringOfBytes(buf) + ", " + new String(buf));
			}
			socket.close();
		}catch(Exception e){	
		}
	}

	private String getStringOfBytes(byte[] data){
		String s = "";
		for(byte b: data)
			s += b + " ";
		return s;
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
