package logic;

import java.awt.Point;

public class MessageMatchClient extends MessageInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Point player;
	private String name;
	
	public MessageMatchClient() {
		super("Match_Client");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Point getPositionPlayer() {
		return player;
	}
	
	public void setPlayer(Point player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return name;
	}
}
