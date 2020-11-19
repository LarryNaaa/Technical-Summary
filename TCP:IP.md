# TCP/IP
## Transmission Control Protocol/TCP
### 3-Way Handshake
![3-Way Handshake](https://raw.githubusercontent.com/HIT-Alibaba/interview/master/img/tcp-connection-made-three-way-handshake.png)

Step 1 (SYN) : In the first step, client wants to establish a connection with server, so it sends a segment with SYN(Synchronize Sequence Number) which informs server that client is likely to start communication and with what sequence number it starts segments with.

Step 2 (SYN + ACK): Server responds to the client request with SYN-ACK signal bits set. Acknowledgement(ACK) signifies the response of segment it received and SYN signifies with what sequence number it is likely to start the segments with.

Step 3 (ACK) : In the final part client acknowledges the response of server and they both establish a reliable connection with which they will start the actual data transfer.

