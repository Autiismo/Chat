package network.packets;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import network.Client;
import network.Server;
import network.User;

public class Packet03UserList extends Packet {
	
	public Packet03UserList(int index, List<User> users) {
		super((byte) 3, new byte[1023]);
		data[1] = (byte)((index >> 16) & 0xff);
		data[2] = (byte)((index >> 8) & 0xff);
		data[3] = (byte)((index >> 0) & 0xff);
		
		int i = 4;
		for(User user : users) {
			int userID = user.getId();
			data[i] = (byte)((userID >> 24) & 0xff);
			data[i + 1] = (byte)((userID >> 16) & 0xff);
			data[i + 2] = (byte)((userID >> 8) & 0xff);
			data[i + 3] = (byte)((userID >> 0) & 0xff);
			i += 4;
			
			int nameLength = user.getName().length();
			data[i] = (byte)((nameLength >> 24) & 0xff);
			data[i + 1] = (byte)((nameLength >> 16) & 0xff);
			data[i + 2] = (byte)((nameLength >> 8) & 0xff);
			data[i + 3] = (byte)((nameLength >> 0) & 0xff);
			i += 4;
			
			byte[] nameBytes = null;
			try {
				nameBytes = user.getName().getBytes("UTF-32");
			} catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			for(byte b : nameBytes) {
				data[i] = b;
				i++;
			}
		}
	}
	
	public Packet03UserList(DatagramPacket p) {
		super(p);
	}

	@Override
	public void serverExecute() {
		server = Server.getServer();
	}

	@Override
	public void clientExecute() {
		client = Client.getClient();
		
		List<User> users = new ArrayList<User>();
		
		int userID = 0, nameLength = 0, nameStart = 0;
		byte[] name = null;
		
		for(int i = 4; i < data.length; i += 4) {
			if(userID == 0) {
				userID = data[i] << 24 | (data[i + 1] & 0xFF) << 16 | (data[i + 2] & 0xFF) << 8 | (data[i + 3] & 0xFF);
				if(userID == 0) break;
				continue;
			}
			
			if(nameLength == 0) {
				nameLength = data[i] << 24 | (data[i + 1] & 0xFF) << 16 | (data[i + 2] & 0xFF) << 8 | (data[i + 3] & 0xFF);
				nameStart = i + 4;
				name = new byte[nameLength * 4];
				continue;
			}
			
			if(i < nameStart + (nameLength * 4)) {
				name[(i - nameStart) + 0] = data[i + 0];
				name[(i - nameStart) + 1] = data[i + 1];
				name[(i - nameStart) + 2] = data[i + 2];
				name[(i - nameStart) + 3] = data[i + 3];
				
				if(((i - nameStart) / 4) + 1 == nameLength) {
					users.add(new User(null, 0, userID, new String(name, Charset.forName("UTF-32"))));
					
					userID = 0;
					nameLength = 0;
					nameStart = 0;
					name = null;
				}
			}
		}
		
		client.users = users;
		chat.updateUsers(users);
	}
	
	
	
}
