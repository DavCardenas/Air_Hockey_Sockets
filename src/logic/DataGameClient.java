package logic;

import java.util.ArrayList;

/**
 * 
 * Esta clase permite controlar toda la informacion que sera dibujada en pantalla
 * se actualiza en el metodo read del cliente actualizando el Singlenton.
 *
 */
public class DataGameClient {


	private ArrayList<Player> playerList; // lista de jugadores conectados
	
	private Player self; // jugador
	private Player counter; // adversario
	
	private int timeGame; // indica el valor para la barra de tiempo restante (120-0 decreciente)
	private int level; // indica el nivel en que se encuentra el juego (sube la velocidad por nivel cda 20s)
	
	public DataGameClient() {
		self = null;
		counter = null;
		timeGame = 0;
		level = 1;
		playerList = new ArrayList<>();
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public Player getSelf() {
		return self;
	}

	public void setSelf(Player self) {
		this.self = self;
	}

	public Player getCounter() {
		return counter;
	}

	public void setCounter(Player counter) {
		this.counter = counter;
	}

	public int getTimeGame() {
		return timeGame;
	}

	public void setTimeGame(int timeGame) {
		this.timeGame = timeGame;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
