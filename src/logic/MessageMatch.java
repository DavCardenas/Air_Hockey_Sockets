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
	private boolean isGame; 
	private int timeLeft; //tiempo restante
	private int levelGame;

	
	/**
	 * constructor
	 * @param type
	 */
	public MessageMatch(Player playerLeft, Player playerRigth, Point disk, boolean isGame, int timeLeft, int levelGame) {
		super("Match");		
		this.playerLeft = playerLeft;
		this.playerRigth = playerRigth;
		this.disk = disk;
		this.isGame = isGame;
		this.timeLeft = timeLeft;
		this.levelGame = levelGame;
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

	/**
	 * @return the isGame
	 */
	public boolean isGame() {
		return isGame;
	}

	/**
	 * @return the timeLeft
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * @return the levelGame
	 */
	public int getLevelGame() {
		return levelGame;
	}
	
	

}	
