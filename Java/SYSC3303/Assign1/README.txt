ASSIGNMENT 1
Client - The client initiates read/write requests by sending a packet to the host. The packet is sent to port 68.

Host - The host sits in between the client and the server, for this assignment the host forwards the exact same
message that it receives. The host listens on port 68 at all times. Packets are forwarded to port 69 which is the
server's port.

Server - The server always listens on port 69. Upon reception of a packet, the server determines whether the packet
is a read/write request or an invalid packet format. The server then sends the appropriate response packet or 
throws an exception and quits.

INSTRUCTIONS
1. Open/make a single project with the Host, Server and Client as files.
2. Run the Host.
3. Run the Server.
4. Run the Client
5. Observe the 10 alternating read/write requests and the invalid packet at the end.
6. Forcefully stop the Host.
