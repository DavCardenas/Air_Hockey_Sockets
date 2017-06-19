package logic;

import java.awt.Point;
import java.io.Serializable;

public class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934960251874208682L;
	
	private String name; // nombre del jugador
	private Point position; // posicion del jugador en el tablero de juego
	private int points; // puntos ganados en el partido
	private int goals; // numero de goles realizados
	private boolean inGame; // sirve para verificar si el jugador esta jugando
	
	public Player() {
		name = "";
		position = null;
	}
	
	/**
	 * clona un objeto a otro cambiando la direccion de memoria
	 * @param p_player
	 * @return
	 */
	public static Player changeDir(Player p_player) {
		Player player_Aux = new Player();
		player_Aux.setName(p_player.getName());
		player_Aux.setPosition(p_player.getPosition());
		player_Aux.setPoints(p_player.getPoints());
		player_Aux.setGoals(p_player.getGoals());
		player_Aux.setInGame(p_player.isInGame());
		
		return player_Aux;
	}
	
	public Player(String p_name, Point p_point) {
		name = p_name;
		position = p_point;
	}
	
	public int getGoals() {
		return goals;
	}
	
	public void setGoals(int goals) {
		this.goals = goals;
	}

	/**
	 * suma un gol
	 */
	public void addGoals() {
		goals++;
	}
	
	/**
	 * @param name
	 */
	public Player(String name) {
		super();
		this.name = name;
		position = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public boolean isInGame() {
		return inGame;
	}
	
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
