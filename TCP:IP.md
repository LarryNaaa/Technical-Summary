# TCP/IP
## Transmission Control Protocol/TCP

### TCP message types
#### SYN/Synchronize Sequence Number
Used to initiate and establish a connection. It also helps you to synchronize sequence numbers between devices.
#### ACK/Acknowledgement
Helps to confirm to the other side that it has received the SYN.
#### FIN
Used to terminate a connection.

### 3-Way Handshake
![3-Way Handshake](https://raw.githubusercontent.com/HIT-Alibaba/interview/master/img/tcp-connection-made-three-way-handshake.png)

Step 1 (SYN = 1 + Seq = x from Client) : Client wants to establish a connection with server, so it sends a segment with SYN which informs server that client is likely to start communication and with what sequence number it starts segments with.

Step 2 (SYN = 1 + Seq = y + ACK = 1 + ACKnum = x + 1 from Server): Server responds to the client request with SYN-ACK signal bits set. ACK signifies the response of segment it received and SYN signifies with what sequence number it is likely to start the segments with.

Step 3 (ACK = 1 + ACKnum = y + 1 from Client) : In the final part client acknowledges the response of server and they both establish a reliable connection with which they will start the actual data transfer.

### 4-way handshake
![4-way handshake](https://raw.githubusercontent.com/HIT-Alibaba/interview/master/img/tcp-connection-closed-four-way-handshake.png)

Step 1 (FIN = 1 + Seq = x From Client) – Client wants to close the connection. (Note that the server could also choose to close the connection). This causes the client send a TCP segment with the FIN bit set to 1 to server and to enter the FIN_WAIT_1 state. While in the FIN_WAIT_1 state, the client waits for a TCP segment from the server with an acknowledgment (ACK).

Step 2 (ACK = 1 + ACKnum = x + 1 From Server) – When Server received FIN bit segment from Client, Server Immediately send acknowledgement (ACK) segment to the Sender (Client).

Step 3 (Client waiting) – While in the FIN_WAIT_1 state, the client waits for a TCP segment from the server with an acknowledgment. When it receives this segment, the client enters the FIN_WAIT_2 state. While in the FIN_WAIT_2 state, the client waits for another segment from the server with the FIN bit set to 1.

Step 4 (FIN = 1 + Seq = y from Server) – Server sends FIN bit segment to the Client after some time when Server send the ACK segment (because of some closing process in the Server).

Step 5 (ACK = 1 + ACKnum = y + 1 from Client) – When Client receive FIN bit segment from the Server, the client acknowledges the server’s segment and enters the TIME_WAIT state. The TIME_WAIT state lets the client resend the final acknowledgment in case the ACK is lost.The time spent by client in the TIME_WAIT state is depend on their implementation, but their typical values are 30 seconds, 1 minute, and 2 minutes. After the wait, the connection formally closes and all resources on the client side (including port numbers and buffer data) are released.


