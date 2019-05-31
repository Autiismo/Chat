package utils;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class FrameResizeListener extends MouseAdapter {
	
	private final JFrame frame;
	private Point lastCoords = null;
	private int dir = 0;
	
	public FrameResizeListener(JFrame frame) {
		this.frame = frame;
	}
	
	public void mouseMoved(MouseEvent e) {
		if(e.getPoint().x <= 3) {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			dir = 1;
		} else if(e.getPoint().x >= frame.getWidth() - 4) {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			dir = 2;
		} else if(e.getPoint().y <= 3) {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			dir = 3;
		} else if(e.getPoint().y >= frame.getHeight() - 4) {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			dir = 4;
		} else {
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		lastCoords = null;
	}
	
	public void mousePressed(MouseEvent e) {
		lastCoords = e.getLocationOnScreen();
	}
	
	public void mouseDragged(MouseEvent e) {
		Point currCoords = e.getLocationOnScreen();
		int width = frame.getWidth();
		int height = frame.getHeight();
		switch(dir) {
		case 1:
			frame.setSize(width - (currCoords.x - lastCoords.x), height);
			frame.setLocation(frame.getX() + (currCoords.x - lastCoords.x), frame.getY());
			break;
		case 2:
			frame.setSize(width + (currCoords.x - lastCoords.x), height);
			break;
		case 3:
			frame.setSize(width, height - (currCoords.y - lastCoords.y));
			frame.setLocation(frame.getX(), frame.getY() + (currCoords.y - lastCoords.y));
			break;
		case 4:
			frame.setSize(width, height + (currCoords.y - lastCoords.y));
			break;
		}
		
		lastCoords = currCoords;
	}
	
}
