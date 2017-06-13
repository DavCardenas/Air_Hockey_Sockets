package logic;

import java.util.ArrayList;

public class MessageServer extends MessageInterface{

	private ArrayList<Player> players;
	
	public MessageServer(String type, ArrayList<Player> listPlayers) {
		super(type);
		this.players = listPlayers;
	}
	
	private String listPlayers() {
		String players = "";
		for (Player player : this.players) {
			players += "," + player;
		}
		return players;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	@Override
	public String getMessage() {
		return listPlayers();
	}

	@Override
	public String getType() {
		return super.getType();
	}
}
