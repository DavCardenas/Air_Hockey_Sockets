package logic;

import java.awt.Point;

public class MessageMatch extends MessageInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3299642310666561319L;
	
	private Player playerLeft;
	private Player playerRigth;
	private Point disk;
	
	/**
	 * constructor
	 * @param type
	 */
	public MessageMatch(Player playerLeft, Player playerRigth, Point disk) {
		super("Match");		
		this.playerLeft = playerLeft;
		this.playerRigth = playerRigth;
		this.disk = disk;
	}

	/**
	 * @return the playerLeft
	 */
	public Player getPlayerLeft() {
		return playerLeft;
	}

	/**
	 * @return the playerRigth
	 */
	public Player getPlayerRigth() {
		return playerRigth;
	}

	/**
	 * @return the disk
	 */
	public Point getDisk() {
		return disk;
	}

}	
