package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * The Host lives in between the client and the server.
 * Currently, the Host simply forwards messages as they
 * arrive.
 */
public class Host {

	/**The port on which the host listens for the client.*/
	public static final int HOST_PORT = 68;
	/**The port to which the host forwards packets for the server.*/
	public static final int SERVER_PORT = 69;
	/**The default size of the buffer inside the packet.*/
	public static final int BUFSIZ = 1024;

	private DatagramSocket receiveSocket;
	private DatagramSocket serverSocket;
	private DatagramSocket clientSocket;

	private int clientPort;

	public Host(){
	}
	
	/**
	 * The run method listens for a packet from the client. It
	 * then prints the contents of the packet and generates a
	 * new packet with the same contents. The new packet is then
	 * forwarded to the server. <br></br>Similarly, the host then listens
	 * for a packet from the server. Upon reception, the contents
	 * of the packet are printed and a packet with the same
	 * contents is sent back to the client on a new socket.
	 */
	private void run(){
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
				System.out.println("Received: " + Arrays.toString(buf) + ", " + new String(buf));
				clientPort = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), SERVER_PORT);
				System.out.println("Sending: " + Arrays.toString(buf) + ", " + new String(buf));

				serverSocket.send(packet);

				buf = new byte[BUFSIZ];
				packet = new DatagramPacket(buf, buf.length);
				
				serverSocket.receive(packet);
				
				buf = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), packet.getOffset(), buf, 0, packet.getLength());
				System.out.println("Received: " + Arrays.toString(buf) + ", " + new String(buf));
				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), clientPort);
				System.out.println("Sending: " + Arrays.toString(buf) + ", " + new String(buf));

				clientSocket = new DatagramSocket();
				clientSocket.send(packet);
				clientSocket.close();
				
			}while(true);

		}catch(Exception e){

		}

	}
	/**
	 * Returns a string of the byte representation of the byte array.
	 * 
	 * @param	data	the byte array that will be converted to string
	 * @return			the string representation of the array 
	 */
	private String getStringOfBytes(byte[] data){
		String s = "";
		for(byte b: data)
			s += b + " ";
		return s;
	}

	/**
	 * The main method creates a host instance
	 * and calls the run function.
	 * 
	 * @param args potential command line arguments
	 */
	public static void main(String[] args) {
		new Host().run();
	}

}
