package logic;

import java.util.ArrayList;
/**
 * 
 * Esta clase sirve exclusivamente para enviar la lista de jugadores conectados
 * hacia los clientes
 *
 */
public class MessageListPlayersServer extends MessageInterface{

	private ArrayList<Player> players; // lista de jugadores a enviar hacia el cliente
	
	public MessageListPlayersServer(ArrayList<Player> listPlayers) {
		super("Lista_Jugadores");
		this.players = listPlayers;
	}
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	@Override
	public ArrayList<Player> getPlayerList() {
		return players;
	}

	@Override
	public String getType() {
		return super.getType();
	}
}
