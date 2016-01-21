package assign1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * The server listens on a public port. Depending on
 * the type of incoming message the server returns a 
 * unique packet. Apart from the first incoming packet,
 * all other packets are sent from sockets which are 
 * created as needed.
 */
public class Server {

	/**A constant to represent the reception
	 * of a read request.*/
	public static final int RDQ = 1;
	/**A constant to represent the reception
	 * of a write request.*/
	public static final int WRQ = 2;
	/**The constant to represent an invalid
	 * packet contents*/
	public static final int INV = -1;
	
	/**The public port on which the server listens.*/
	public static final int SERVER_PORT = 69;
	
	/**The default size for the buffer inside the
	 * datagram packet.*/
	public static final int BUFSIZ = 1024;

	private DatagramSocket socket;

	public Server(){
		
	}
	/**
	 * The run method listens on the public server port.
	 * When a packet is received, the server prints out
	 * the contents of the packet. If the packet is a read
	 * request, the bytes 0301 are sent back to the client.
	 * If the packet is a write request, the bytes 0400 are
	 * sent back to the client. In case of an invalid packet,
	 * an exception is thrown.
	 */
	private void run() {
		try {

			socket = new DatagramSocket(SERVER_PORT);
			int result;
			byte[] buf;

			while(true){

				buf = new byte[BUFSIZ];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				socket.receive(packet);

				buf = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), packet.getOffset(), buf, 0, packet.getLength());

				result = verifyData(buf);

				System.out.println("Received: " + getStringOfBytes(buf) + ", " + new String(buf));

				if(result == RDQ)
					buf = new byte[]{0,3,0,1};
				else if(result == WRQ)
					buf = new byte[]{0,4,0,0};
				else
					throw new Exception();

				packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), packet.getPort());

				System.out.println("Sending: " + getStringOfBytes(buf) + ", " + new String(buf));

				DatagramSocket tempSocket = new DatagramSocket();
				tempSocket.send(packet);
				tempSocket.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Returns a string equivalent to the contents of the byte
	 * array, where each byte is separated by a space.
	 * 
	 * @param data the array to be displayed in byte form
	 * @return a string of the bytes with space separation
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
	 * The data verification class checks that the
	 * data is consistent with the format presented in
	 * the assignment description. The byte array must
	 * start with 01 or 02, then it must be followed by 
	 * a file name, then a 0, then a mode, then a 0 and
	 * then nothing else.
	 * 
	 *  @param	data	the byte array to be verified
	 *  @return			-1 (invalid),1 (read),2 (write) 
	 *  				depending on verification result
	 */
	private int verifyData(byte[] data){
		if(data == null)
			return INV;
		if(data.length < 6)
			return INV;
		if(data[0] != 0)
			return INV;
		if(data[1] != RDQ && data[1] != WRQ)
			return INV;

		byte[] filename = new byte[0];
		int i;
		for(i = 2; i < data.length && data[i] != 0; i++){
			filename = concatenateArrays(filename, new byte[]{data[i]});
		}

		if(filename.length == 0)
			return INV;
		if(i >= data.length - 2)
			return INV;

		byte[] filemode = new byte[0];
		for(i = i + 1; i < data.length && data[i] != 0; i++){
			filemode = concatenateArrays(filemode, new byte[]{data[i]});
		}

		if(filemode.length == 0)
			return INV;
		if(data.length - 1 != i)
			return INV;
		if(data[i] != 0)
			return INV;

		return data[1];		
	}

	/**
	 * The main method initializes the server class
	 * and calls its run method.
	 * 
	 * @param args potential command line arguments
	 */
	public static void main(String[] args) {
		new Server().run();
	}

}
