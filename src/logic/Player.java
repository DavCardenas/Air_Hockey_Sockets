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
	private int totalPoints; //puntos acumulados en todos los partidos
	private int goals; // numero de goles realizados
	private int totalGoals; //numero de goles acumulados en todos los partidos
	private boolean inGame; // sirve para verificar si el jugador esta jugando
	
	public Player() {
		name = "";
		position = null;
		points = 0;
		totalPoints = 0;
		goals = 0;
		totalGoals = 0;
		inGame = false;
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
		player_Aux.setTotalGoals(p_player.getTotalGoals());
		player_Aux.setTotalPoints(p_player.getTotalPoints());
		return player_Aux;
	}
	
	public Player(String p_name, Point p_point) {
		name = p_name;
		position = p_point;
		points = 0;
		totalPoints = 0;
		goals = 0;
		totalGoals = 0;
		inGame = false;
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
	 * metodo para avisar que acabo un juego y debe actualizar valores
	 */
	public void gameIsOver(){
		this.inGame = false;
		
		this.totalGoals += this.goals;
		this.goals= 0;
		
		this.totalPoints += this.points;
		this.points = 0;		
	}
	
	/**
	 *anotacion en propia puerta, antes se verifica si se cuenta con mas de 5goles
	 *para restar cinco, y si no dejar en 0 
	 */
	public void goalOwnDoor(){
		if(this.points>5){
			this.points-=5;
		} else {
			this.points = 0;
		}
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
	
	
	
	/**
	 * @return the totalPoints
	 */
	public int getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	/**
	 * @return the totalGoals
	 */
	public int getTotalGoals() {
		return totalGoals;
	}

	/**
	 * @param totalGoals the totalGoals to set
	 */
	public void setTotalGoals(int totalGoals) {
		this.totalGoals = totalGoals;
	}

	@Override
	public String toString() {
		return name;
	}
}
