package network.packets;

import java.net.DatagramPacket;

import chat.Chat;
import network.Client;
import network.Server;

public abstract class Packet {
	
	protected byte id;
	protected byte[] data;
	
	protected DatagramPacket p;
	
	protected Server server;
	protected Client client;
	
	protected Chat chat;
	
	public Packet(byte id, byte[] data) {
		this.id = id;
		this.data = new byte[data.length + 1];
		this.data[0] = id;
		for(int i = 0; i < data.length; i++) {
			this.data[i + 1] = data[i];
		}
		this.chat = Chat.getChat();
	}
	
	public Packet(DatagramPacket p) {
		this.p = p;
		this.data = p.getData();
		this.chat = Chat.getChat();
	}
	
	public abstract void serverExecute();
	public abstract void clientExecute();

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}
