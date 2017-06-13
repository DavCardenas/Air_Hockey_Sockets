package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase usada para transportar informacion 
 * se utiliza el patron singlenton para mantener 
 * un diseño facil y pueda ser accedido desde cualquier parte
 * del programa.
 */

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3312476148586539499L;
	
	private ArrayList<Player> players; // lista de jugadores conectados desde el servidor
	
	private Player playerSelf; // jugador que inicio el cliente
	private Player playerCounter; // jugador contrario seleccionado
	
	private boolean startGame; // indica el estado del juego
	private int timeGame; // indica el valor para la barra de tiempo restante
	private int level; // indica el nivel en que se encuentra el juego (sube la velocidad por nivel cda 20s)
	private int level_score; // indica la cantidad de puntos a sumar por gol
	private boolean invitation; // indica si el jugador desea invitar a playerCounter
	private boolean wait; // indica si el jugador tiene una invitacion pendiente
	
	public Message() {
		startGame = false;
		timeGame = 120;
		level = 0;
		level_score = 60;
		invitation = false;
		players = new ArrayList<>();
	}
	
	/**
	 * permite agregar un jugador a la lista de jugadores
	 * @param player jugador que se va a agregar
	 */
	public void addPlayer(Player player) {
		if (!players.contains(player)) {
			this.players.add(player);
		}
	}
	
	/**
	 * permite remover un jugador de la lista de jugadores
	 * @param player jugador que se va a eliminar
	 */
	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Player getPlayerSelf() {
		return playerSelf;
	}

	public void setPlayerSelf(Player playerSelf) {
		this.playerSelf = playerSelf;
	}

	public Player getPlayerCounter() {
		return playerCounter;
	}

	public void setPlayerCounter(Player playerCounter) {
		this.playerCounter = playerCounter;
	}

	public boolean isStartGame() {
		return startGame;
	}

	public void setStartGame(boolean startGame) {
		this.startGame = startGame;
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

	public int getLevel_score() {
		return level_score;
	}

	public void setLevel_score(int level_score) {
		this.level_score = level_score;
	}
	
	public boolean isInvitation() {
		return invitation;
	}
	
	public void setInvitation(boolean invitation) {
		this.invitation = invitation;
	}
	
}
