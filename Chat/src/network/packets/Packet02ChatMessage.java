package network.packets;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.Charset;

import network.Client;
import network.Server;
import network.User;
import utils.Utils;

public class Packet02ChatMessage extends Packet {
	
	public Packet02ChatMessage(int index, int userID, String message) {
		super((byte) 2, new byte[1023]);
		data[1] = (byte)((index >> 16) & 0xff);
		data[2] = (byte)((index >> 8) & 0xff);
		data[3] = (byte)((index >> 0) & 0xff);
		
		data[4] = (byte)((userID >> 24) & 0xff);
		data[5] = (byte)((userID >> 16) & 0xff);
		data[6] = (byte)((userID >> 8) & 0xff);
		data[7] = (byte)((userID >> 0) & 0xff);
		byte[] msgData = null;
		try {
			msgData = message.getBytes("UTF-32");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < msgData.length; i++) {
			data[i + 8] = msgData[i];
		}
	}
	
	public Packet02ChatMessage(DatagramPacket p) {
		super(p);
	}

	@Override
	public void serverExecute() {
		server = Server.getServer();
		//TODO: optional verify if user and id match if not dont send
		server.sendAll(data);
	}

	@Override
	public void clientExecute() {
		client = Client.getClient();
		
		int userID = data[4] << 24 | (data[5] & 0xFF) << 16 | (data[6] & 0xFF) << 8 | (data[7] & 0xFF);
		User user = Utils.getUserByID(client.users, userID);
		
		byte[] messageBytes = new byte[1016];
		for(int i = 0; i < messageBytes.length; i++) {
			messageBytes[i] = data[i + 8];
		}
		String message = new String(messageBytes, Charset.forName("UTF-32")).trim();
		System.out.println(user.getName() + ": " + message);
	}
	
	
	
}
