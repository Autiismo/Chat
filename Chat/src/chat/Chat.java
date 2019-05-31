package chat;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import network.Client;
import network.Server;
import network.User;
import network.packets.Packet00Login;
import network.packets.Packet01Disconnect;
import ui.UI;
import utils.FrameResizeListener;

public class Chat {
	
	private static final long serialVersionUID = 1L;
	
	private Client client;
	private Server server;
	
	private JFrame frame;
	
	private static Chat chat;
	private String username;
	private int id = 0;
	
	private boolean fullscreen = false;
	private boolean minimized = false;
	
	private Toolkit tk = Toolkit.getDefaultToolkit();
	
	public List<User> users = new ArrayList<User>();
	
	private UI ui;
	
	public static void main(String[] args) {
		chat = new Chat();
	}
	public void exit() {
		Packet01Disconnect disconnect = new Packet01Disconnect();
		client.sendData(disconnect.getData());
		
		client.setRunning(false);
		
		System.exit(0);
	}
	
	public void exitWhenUnconnected() {
		client.setRunning(false);
		
		System.exit(0);
	}
	
	public Chat() {
		frame = new JFrame("Chat");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setUndecorated(true);
		
		if(JOptionPane.showConfirmDialog(frame, "Would you like to run the server?", "Run the server?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			server = new Server();
			server.start();
			server.setRunning(true);
		}
		
		client = new Client("69.124.156.153");
		client.start();
		client.setRunning(true);
		
		username = getAvailableUsername();
		if(username == null) exitWhenUnconnected();
		
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowOpened(WindowEvent e) {}
		});
		
		FrameResizeListener frameResizeListener = new FrameResizeListener(frame);
		frame.addMouseListener(frameResizeListener);
		frame.addMouseMotionListener(frameResizeListener);
		
		ui = new UI(this, client, frame);
		frame.add(ui);
		
		frame.setVisible(true);
		
		Packet00Login login = new Packet00Login(username);
		client.sendData(login.getData());
	}
	
	private String getAvailableUsername() {
		String username = JOptionPane.showInputDialog(frame, "Please enter a username");
		if(username == null) return null;
		if(username.matches("^[a-zA-Z0-9_.]+$")) return username;
		return getAvailableUsername();
	}

	public static Chat getChat() {
		return chat;
	}
	
	public int getId() {
		return id;
	}
	
	public void minMaxScreen() {
		fullscreen = !fullscreen;
		frame.setExtendedState(fullscreen ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);
	}

	public void updateUsers(List<User> users) {
		this.users = users;
		if(id == 0) {
			for(User user : users) {
				if(user.getName().equals(username)) {
					id = user.getId();
				}
			}
		}
	}
	
}
