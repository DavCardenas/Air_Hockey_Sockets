package logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class MessageInterface implements Serializable{
	
	String type; // define que tipo de mensaje es
	
	/**
	 * contructor que permite iniciar que tipo de mensaje es
	 * @param type
	 */
	public MessageInterface(String type) {
		this.type = type;
	}
	
	/**
	 * sirve para retornar un jugador
	 * @return un jugador
	 */
	public Player getPlayer() {
		return null;
	}
	
	/**
	 * sirve para retorna una lista de jugadores
	 * @return una lista de jugadores
	 */
	public ArrayList<Player> getPlayerList() {
		return null;
	}
	
	/**
	 * retorna el mensaje
	 * @return
	 */
	public String getMessage() {
		return null;
	}
	
	public String[] getPlayersVector() {
		return null;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
