package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Host {

	public static final int HOST_PORT = 68;
	public static final int SERVER_PORT = 69;
	public static final int BUFSIZ = 1024;

	private DatagramSocket receiveSocket;
	private DatagramSocket serverSocket;
	private DatagramSocket clientSocket;

	private int clientPort;

	public Host(){
		try{
			receiveSocket = new DatagramSocket(HOST_PORT);
			serverSocket = new DatagramSocket();

			byte[] buf;
			DatagramPacket packet;

			do{
				buf = new byte[BUFSIZ];
				packet = new DatagramPacket(buf, buf.length);

				receiveSocket.receive(packet);
				
				buf = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), packet.getOffset(), buf, 0, packet.getLength());
				
				System.out.println("Received: " + getStringOfBytes(buf) + ", " + new String(buf));

				clientPort = packet.getPort();

				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), SERVER_PORT);
				
				System.out.println("Sending: " + getStringOfBytes(buf) + ", " + new String(buf));

				serverSocket.send(packet);

				buf = new byte[BUFSIZ];
				packet = new DatagramPacket(buf, buf.length);
				
				serverSocket.receive(packet);
				
				buf = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), packet.getOffset(), buf, 0, packet.getLength());
				
				System.out.println("Received: " + getStringOfBytes(buf) + ", " + new String(buf));
				
				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), clientPort);
				
				System.out.println("Sending: " + getStringOfBytes(buf) + ", " + new String(buf));

				clientSocket = new DatagramSocket();
				clientSocket.send(packet);
				clientSocket.close();
				
			}while(true);

		}catch(Exception e){

		}

	}
	
	private String getStringOfBytes(byte[] data){
		String s = "";
		for(byte b: data)
			s += b + " ";
		return s;
	}

	public static void main(String[] args) {
		new Host();
	}

}
