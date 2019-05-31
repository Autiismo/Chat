package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import chat.Chat;
import network.Client;
import network.packets.Packet02ChatMessage;
import utils.FrameDragListener;
import utils.Utils;

public class UI extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Chat chat;
	private Client client;
	
	public UI(Chat chat, Client client, JFrame frame) {
		this.chat = chat;
		this.client = client;
		
		setLayout(new BorderLayout());
		
		JPanel panelTopBar = new JPanel();
		panelTopBar.setLayout(new BorderLayout());
		panelTopBar.setBackground(new Color(50, 50, 50));
		FrameDragListener frameDragListener = new FrameDragListener(frame);
		panelTopBar.addMouseListener(frameDragListener);
		panelTopBar.addMouseMotionListener(frameDragListener);
		
		JPanel panelTopButtons = new JPanel();
		panelTopButtons.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		ImageButton buttonMinimize = new ImageButton(new Dimension(24, 24), Utils.loadImage("/res/button_minimize/button_minimize.png"), Utils.loadImage("/res/button_minimize/button_minimize_hover.png"));
		buttonMinimize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}
			
		});
		panelTopButtons.add(buttonMinimize);
		
		ImageButton buttonMax = new ImageButton(new Dimension(24, 24), Utils.loadImage("/res/button_max/button_max.png"), Utils.loadImage("/res/button_max/button_max_hover.png"));
		buttonMax.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chat.minMaxScreen();
			}
			
		});
		panelTopButtons.add(buttonMax);
		
		ImageButton buttonClose = new ImageButton(new Dimension(24, 24), Utils.loadImage("/res/button_close/button_close.png"), Utils.loadImage("/res/button_close/button_close_hover.png"));
		buttonClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
			
		});
		panelTopButtons.add(buttonClose);
		
		panelTopBar.add(panelTopButtons, BorderLayout.LINE_END);
		
		add(panelTopBar, BorderLayout.PAGE_START);
		
		
		JPanel panelMessageBox = new JPanel();
		
		JTextPane textMessageBox = new JTextPane();
		textMessageBox.setEditable(false);
		panelMessageBox.add(textMessageBox);
		
		add(panelMessageBox);
		
		
		
		JPanel panelMessageBar = new JPanel();
		
		JTextField textMessageBar = new JTextField(20);
		ActionListener sendAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Packet02ChatMessage messagePacket = new Packet02ChatMessage(0, chat.getId(), textMessageBar.getText());
				client.sendData(messagePacket.getData());
				textMessageBar.setText("");
			}
		};
		textMessageBar.addActionListener(sendAction);
		panelMessageBar.add(textMessageBar);
		
		JButton buttonSend = new JButton("Send");
		buttonSend.addActionListener(sendAction);
		panelMessageBar.add(buttonSend);
		
		add(panelMessageBar);
	}
	
}
