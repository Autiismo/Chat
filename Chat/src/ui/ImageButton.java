package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class ImageButton extends JComponent implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private Dimension size;
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private BufferedImage image;
	private BufferedImage imageHover;
	
	private boolean hover = false;
	
	public ImageButton(Dimension size, BufferedImage image, BufferedImage imageHover) {
		super();
		this.size = size;
		this.image = image;
		this.imageHover = imageHover;
		enableInputMethods(true);
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(hover ? imageHover : image, 0, 0, null);
	}
	
	public Dimension getPreferredSize() {
		return size;
	}
	
	public Dimension getMinimumSize() {
		return size;
	}
	
	public Dimension getMaximumSize() {
		return size;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		notifyListeners(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		hover = true;
		repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners(MouseEvent e) {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(),
				e.getModifiers());
		synchronized(listeners) {
			for(int i = 0; i < listeners.size(); i++) {
				ActionListener tmp = listeners.get(i);
				tmp.actionPerformed(evt);
			}
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public BufferedImage getImageHover() {
		return imageHover;
	}
	
}
