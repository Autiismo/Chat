package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import network.packets.Packet;
import network.packets.Packet00Login;
import network.packets.Packet01Disconnect;
import network.packets.Packet02ChatMessage;
import network.packets.Packet03UserList;

public class Server extends Thread {
	
	DatagramSocket socket;
	int port;
	
	public List<User> users = new ArrayList<User>();
	
	protected static Server server;
	
	boolean running;
	
	public Server() {
		try {
			this.port = 2602;
			this.socket = new DatagramSocket(port);
			server = this;
		} catch(SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void run() {
		while(running) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			data = packet.getData();
			Packet p = null;
			
			switch(data[0]) {
			case 0:
				p = new Packet00Login(packet);
				break;
			case 1:
				p = new Packet01Disconnect(packet);
				break;
			case 2:
				p = new Packet02ChatMessage(packet);
				break;
			case 3:
				p = new Packet03UserList(packet);
				break;
			}
			
			p.serverExecute();
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToUser(byte[] data, User user) {
		DatagramPacket packet = new DatagramPacket(data, data.length, user.getIpAddress(), user.getPort());
		try {
			socket.send(packet);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendAll(byte[] data) {
		for(User u : users) {
			sendData(data, u.getIpAddress(), u.getPort());
		}
	}
	
	public User getUserByIP(InetAddress ip, int port) {
		for(User user : users) {
			if(user.getIpAddress().equals(ip) && user.getPort() == port) {
				return user;
			}
		}
		return null;
	}
	
	public static Server getServer() {
		return server;
	}
	
}
