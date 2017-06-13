package logic;

import java.util.ArrayList;

public class MessageServer extends MessageInterface{

	private ArrayList<Player> players;
	
	public MessageServer() {
	
	}
	
	private String listPlayers() {
		String players = "";
		for (Player player : this.players) {
			players += "," + player;
		}
		return players;
	}
	
	@Override
	public String getMessage() {
		return listPlayers();
	}

}
