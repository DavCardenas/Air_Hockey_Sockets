package logic;

import java.awt.Point;

public class Match {
	
	public static final int TOTAL__TIME_VALUE = 120; // TIEMPO TOTAL DE LA PARTIDA EN SEGUNDOS
	public static final int MAXIMUM_WIDTH = 1000; //ANCHO MAXIMO
	public static final int MAXIMUM_HEIGHT = 600; //ALTO MAXIMO
	public static final int X_POSITION_INITIAL_PLAYER_LEFT = 15; //posicion inicial en x para el jugador izquierda
	public static final int X_POSITION_INITIAL_PLAYER_RIGTH = MAXIMUM_WIDTH - X_POSITION_INITIAL_PLAYER_LEFT; //posicion en x para el jugador derecha
	public static final int Y_POSITION_INITIAL = MAXIMUM_HEIGHT/2; //posicion inicial para todos en y
	public static final int RANGE_CENTER = 100; //rango que tendra el mazo con respecto al centro
	public static final int X_POSITION_INITIAL_disk_LEFT = (MAXIMUM_WIDTH/2)-RANGE_CENTER; //posicion inicial x cuando empieza izquierrda
	public static final int X_POSITION_INITIAL_disk_RIGTH = (MAXIMUM_WIDTH/2)+RANGE_CENTER; //posicion inicial x cuando empieza derecha
	
	
	private Player playerLeft; //jugador de la izquierda
	private Player playerRigth; //jugador de la derecha
	private Point disk; //disco
	private int timeLeft; //tiempo restante
	private String playerBegin; //nombre del jugador que  inicia
	/**
	 * @param playerLeft
	 * @param playerRigth
	 */
	public Match(String namePlayerLeft, String namePlayerRigth, String playerBegin) {
		super();
		this.playerLeft = new Player(namePlayerLeft);
		this.playerRigth = new Player(namePlayerRigth);
		this.playerBegin = playerBegin;
		this.timeLeft = TOTAL__TIME_VALUE;
		
		this.assignInitialPosition();				
	}
	
	/**
	 *metodo encarga de asignar las posiciones inciales de los demas 
	 */
	private void assignInitialPosition(){
		this.assignInitialPositionPlayerLeft();
		this.assignInitialPositionPlayerRigth();
		this.createdisk();
	}
	
	/**
	 * JUGADOR DE LA IZQUIERDA ASIGNAR LA POSICION 
	 */
	private void assignInitialPositionPlayerLeft(){
		this.playerLeft.setPosition(new Point(X_POSITION_INITIAL_PLAYER_LEFT, Y_POSITION_INITIAL));
	}
	
	/**
	 * JUGADOR DE LA IZQUIERDA ASIGNAR LA POSICION 
	 */
	private void assignInitialPositionPlayerRigth(){
		this.playerLeft.setPosition(new Point(X_POSITION_INITIAL_PLAYER_RIGTH, Y_POSITION_INITIAL));
	}
	/**
	 * metodo para crear y asignar posiciones iniciales al mazo
	 * 
	 */
	private void createdisk(){
		if(isBeginPlayerLeft()){
			this.disk = new Point(X_POSITION_INITIAL_PLAYER_LEFT, Y_POSITION_INITIAL);
		} else {
			this.disk = new Point(X_POSITION_INITIAL_disk_RIGTH, Y_POSITION_INITIAL);
		}
	}
	
	/**
	 * metodo para verificar si el que comienza es el jugador de la izquierda
	 * @return
	 */
	private boolean isBeginPlayerLeft(){
		return this.playerLeft.getName().equals(this.playerBegin);
	}

	/**
	 * @return the disk
	 */
	public Point getdisk() {
		return disk;
	}

	/**
	 * @param disk the disk to set
	 */
	public void setdisk(Point disk) {
		this.disk = disk;
	}

	/**
	 * @return the timeLeft
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * @param timeLeft the timeLeft to set
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * @return the playerBegin
	 */
	public String getPlayerBegin() {
		return playerBegin;
	}

	/**
	 * @param playerBegin the playerBegin to set
	 */
	public void setPlayerBegin(String playerBegin) {
		this.playerBegin = playerBegin;
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
	
	
}
