package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Client {

	/**The port on which the client contacts the host.*/
	public static final int HOST_PORT = 68;
	/**The default size of the buffer inside the receiver
	 * packets.*/
	public static final int BUFSIZ = 1024;
	
	/**The filename to be sent to the server.*/
	public static final String FILENAME = "test.txt";
	/**The mode included in the packet for the server*/
	public static final String FILEMODE = "ocTEt";

	private DatagramSocket socket;
	private DatagramPacket packet;

	public Client(){
		
	}
	
	/**
	 * The run method sends 11 packets to the server, through
	 * the host. The packets alternate between read and write
	 * requests. After sending a packet, the client listens for
	 * an acknowledgement from the server.
	 */
	private void run(){
		try{
			byte[] buf;
			for(int i = 0; i < 11; i++){
				socket = new DatagramSocket();

				buf = new byte[]{0, (byte) (i%2+1)};
				buf = concatenateArrays(buf, FILENAME.getBytes());
				buf = concatenateArrays(buf, new byte[]{0});
				buf = concatenateArrays(buf, FILEMODE.getBytes());
				buf = concatenateArrays(buf, new byte[]{0});
				if(i == 10)
					buf = concatenateArrays(buf, new byte[]{0});

				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), HOST_PORT);

				System.out.println("Sending: " + Arrays.toString(buf) + ", " + new String(buf));

				socket.send(packet);

				buf = new byte[BUFSIZ];
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				buf = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), packet.getOffset(), buf, 0, packet.getLength());

				System.out.println("Received: " + Arrays.toString(buf) + ", " + new String(buf));
			}
			socket.close();
		}catch(Exception e){	
		}
	}

	/**
	 * Returns a string where the contents of the byte
	 * array are separated by spaces.
	 * @param data the byte array to be converted to string
	 * @return a space separated representation of the byte 
	 * values of data
	 */
	private String getStringOfBytes(byte[] data){
		String s = "";
		for(byte b: data)
			s += b + " ";
		return s;
	}

	/**
	 * Generates one byte array where array a is first
	 * and array b is second.
	 * 
	 * @param a occupies indexes 0 to len(a)-1 of new array
	 * @param b occupies indexes len(a) to len(a)+len(b)-1
	 * @return the concatenation of arrays a and b
	 */
	private byte[] concatenateArrays(byte[] a, byte[] b){
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * The main method creates a client instance and calls
	 * the run method.
	 * @param args command line arguments which are not used
	 */
	public static void main(String[] args) {
		new Client().run();
	}

}
