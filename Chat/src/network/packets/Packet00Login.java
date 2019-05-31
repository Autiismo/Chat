package network.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

import network.Client;
import network.Server;
import network.User;

public class Packet00Login extends Packet {
	
	public Packet00Login(String name) {
		super((byte) 0, name.getBytes());
	}
	
	public Packet00Login(DatagramPacket p) {
		super(p);
	}

	@Override
	public void serverExecute() {
		server = Server.getServer();
		InetAddress ip = p.getAddress();
		int port = p.getPort();
		byte[] data = p.getData();
		String name = new String(Arrays.copyOfRange(data, 1, data.length)).trim();
		int id = getUnusedID();
		server.users.add(new User(ip, port, id, name));
		System.out.println("[" + ip + ":" + port + "] " + id + "/" + name + " connected.");
		
		Packet03UserList userList = new Packet03UserList(0, server.users);
		server.sendAll(userList.getData());
	}

	@Override
	public void clientExecute() {
		client = Client.getClient();
	}
	
	private int getUnusedID() {
		int id = (int) (Math.random() * Math.pow(2, 31));
		for(User user : server.users) {
			if(user.getId() == id || id == 0) return getUnusedID();
		}
		return id;
	}
	
}
