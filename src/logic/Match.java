package logic;

import java.awt.Point;

public class Match implements Runnable{
	
	public static final int TOTAL__TIME_VALUE = 120; // TIEMPO TOTAL DE LA PARTIDA EN SEGUNDOS
	public static final int MAXIMUM_WIDTH = 1000; //ANCHO MAXIMO
	public static final int MAXIMUM_HEIGHT = 600; //ALTO MAXIMO
	public static final int X_POSITION_INITIAL_PLAYER_LEFT = 15; //posicion inicial en x para el jugador izquierda
	public static final int X_POSITION_INITIAL_PLAYER_RIGTH = MAXIMUM_WIDTH - X_POSITION_INITIAL_PLAYER_LEFT; //posicion en x para el jugador derecha
	public static final int Y_POSITION_INITIAL = MAXIMUM_HEIGHT/2; //posicion inicial para todos en y
	public static final int RANGE_CENTER = 100; //rango que tendra el mazo con respecto al centro
	public static final int X_POSITION_INITIAL_disk_LEFT = (MAXIMUM_WIDTH/2)-RANGE_CENTER; //posicion inicial x cuando empieza izquierrda
	
	private Player playerLeft; //jugador de la izquierda
	private Player playerRigth; //jugador de la derecha
	private Point disk; //disco
	private int timeLeft; //tiempo restante
	private String playerBegin; //nombre del jugador que  inicia
	private OperationsServer clientLeft;
	private OperationsServer clientRigth;
	private Thread game; // hilo que controla el juego
	private boolean isGame; // controla si se esta en juego o no.

	/**
	 * @param playerLeft
	 * @param playerRigth
	 */
	public Match(String player1, String player2, String playerBegin) {
		super();		
		this.playerBegin = playerBegin;
		this.timeLeft = TOTAL__TIME_VALUE;
		this.clientLeft = null;
		this.clientRigth = null;
		this.assignInitialPosition(player1, player2);
		isGame = true;
		game = new Thread(this);
	}
	
	/**
	 * detiene el juego
	 */
	public void stopGame() {
		isGame = false;
	}
	
	/**
	 * inicia el hilo del juego
	 */
	public void startGame() {
		game.start();
	}
	
	/**
	 * metodo para esribir a jugadores
	 */
	public void writePlayers(){
		MessageMatch match = new MessageMatch(this.playerLeft, this.playerRigth, this.disk);
		this.clientLeft.write(match);
		this.clientRigth.write(match);
	}
	
	/**
	 *metodo encarga de asignar las posiciones inciales de los demas 
	 */
	private void assignInitialPosition(String player1, String player2){
		if(player1.equals(this.playerBegin)){
			this.assignInitialPositionPlayerLeft(player1);	
			this.assignInitialPositionPlayerRigth(player2);
		} else {
			this.assignInitialPositionPlayerLeft(player2);	
			this.assignInitialPositionPlayerRigth(player1);		
		}
		this.createdisk();
	}
	
	/**
	 * JUGADOR DE LA IZQUIERDA ASIGNAR LA POSICION 
	 */
	private void assignInitialPositionPlayerLeft(String namePlayer){
		this.playerLeft = new Player(namePlayer);
		this.playerLeft.setPosition(new Point(X_POSITION_INITIAL_PLAYER_LEFT, Y_POSITION_INITIAL));
	}
	
	/**
	 * JUGADOR DE LA IZQUIERDA ASIGNAR LA POSICION 
	 */
	private void assignInitialPositionPlayerRigth(String namePlayer){
		this.playerRigth = new Player(namePlayer);
		this.playerRigth.setPosition(new Point(X_POSITION_INITIAL_PLAYER_RIGTH, Y_POSITION_INITIAL));
	}
	/**
	 * metodo para crear y asignar posiciones iniciales al mazo
	 * siempre al lado izquierdo
	 */
	private void createdisk(){
		this.disk = new Point(X_POSITION_INITIAL_PLAYER_LEFT, Y_POSITION_INITIAL);
	}

	/**
	 * @return the disk
	 */
	public Point getDisk() {
		return disk;
	}

	/**
	 * @param disk the disk to set
	 */
	public void setDisk(Point disk) {
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

	/**
	 * @return the clientLeft
	 */
	public OperationsServer getClientLeft() {
		return clientLeft;
	}

	/**
	 * @param clientLeft the clientLeft to set
	 */
	public void setClientLeft(OperationsServer clientLeft) {
		this.clientLeft = clientLeft;
	}

	/**
	 * @return the clientRigth
	 */
	public OperationsServer getClientRigth() {
		return clientRigth;
	}

	/**
	 * @param clientRigth the clientRigth to set
	 */
	public void setClientRigth(OperationsServer clientRigth) {
		this.clientRigth = clientRigth;
	}

	@Override
	public void run() {
		while (isGame) {
			
			writePlayers();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	
}
