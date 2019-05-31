package network;

import java.net.InetAddress;

public class User {
	
	InetAddress ipAddress;
	int port;
	int id;
	String name;
	
	public User(InetAddress ipAddress, int port, int id, String name) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.id = id;
		this.name = name;
	}
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
