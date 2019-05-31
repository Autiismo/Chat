package network.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;

import network.Client;
import network.Server;
import network.User;

public class Packet01Disconnect extends Packet {
	
	public Packet01Disconnect() {
		super((byte) 1, new byte[1023]);
	}
	
	public Packet01Disconnect(DatagramPacket p) {
		super(p);
	}

	@Override
	public void serverExecute() {
		server = Server.getServer();
		InetAddress ip = p.getAddress();
		int port = p.getPort();
		User user = server.getUserByIP(ip, port);
		System.out.println("[" + ip + ":" + port + "] " + user.getId() + "/" + user.getName() + " disconnected.");
		server.users.remove(user);
		
		Packet03UserList userList = new Packet03UserList(0, server.users);
		server.sendAll(userList.getData());
	}

	@Override
	public void clientExecute() {
		client = Client.getClient();
	}
	
	
	
}
