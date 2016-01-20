package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Host {

	public static final int HOST_PORT = 68;
	public static final int SERVER_PORT = 69;
	public static final int BUFSIZ = 512;

	private DatagramSocket receiveSocket;
	private DatagramSocket serverSocket;
	private DatagramSocket clientSocket;

	private int clientPort;
	private String data;

	public Host(){
		try{
			receiveSocket = new DatagramSocket(HOST_PORT);
			serverSocket = new DatagramSocket();

			byte[] buf = new byte[BUFSIZ];

			do{
				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				receiveSocket.receive(packet);
				
				System.out.println("Received: " + packet.getData());
				data = new String(packet.getData());

				clientPort = packet.getPort();

				buf = packet.getData();
				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), SERVER_PORT);

				serverSocket.send(packet);

				serverSocket.receive(packet);
				buf = packet.getData();
				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), clientPort);

				clientSocket = new DatagramSocket();
				clientSocket.send(packet);
				clientSocket.close();
			}while(!data.equals("end"));

			receiveSocket.close();
			serverSocket.close();

		}catch(Exception e){

		}

	}

	public static void main(String[] args) {
		new Host();
	}

}
