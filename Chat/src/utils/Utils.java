package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import network.User;

public class Utils {
	
	public static User getUserByName(List<User> users, String username) {
		for(User user : users) {
			if(user.getName().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public static User getUserByID(List<User> users, int id) {
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Utils.class.getResourceAsStream(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
