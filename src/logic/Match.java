package logic;

import java.awt.Point;
import java.awt.Rectangle;

import gui.WindowsGame;

public class Match implements Runnable{
	
	public static final int TOTAL__TIME_VALUE = 120; // TIEMPO TOTAL DE LA PARTIDA EN SEGUNDOS
	public static final int RANGE_CENTER = 100; //rango que tendra el disk con respecto al centro
	public static final int X_POSITION_INITIAL_PLAYER_LEFT = 15; //posicion inicial en x para el jugador izquierda
	public static final int X_POSITION_INITIAL_PLAYER_RIGTH = WindowsGame.WIDTH - RANGE_CENTER; //posicion en x para el jugador derecha
	public static final int Y_POSITION_INITIAL = WindowsGame.HEIGHT /2; //posicion inicial para todos en y
	public static final int X_POSITION_INITIAL_DISK_LEFT = (WindowsGame.WIDTH /2)-RANGE_CENTER; //posicion inicial x cuando empieza izquierrda
	
	private Player playerLeft; //jugador de la izquierda
	private Player playerRigth; //jugador de la derecha
	private Point disk; //disco
	private int timeLeft; //tiempo restante
	private String playerBegin; //nombre del jugador que  inicia
	private OperationsServer clientLeft;
	private OperationsServer clientRigth;
	private Thread game; // hilo que controla el juego
	private boolean isGame; // controla si se esta en juego o no.
	private boolean isRun; //controla si el disco se esta moviendo o no
	private int orientation; //0 --> izquierda arriba, 1 --> izquierda recto, 2 --> izquierda abajo
							//3 --> derecha arriba, 4 --> derecha recto, 5 --> derecha abajo

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
		this.isRun = false;
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
		MessageMatch match = new MessageMatch(this.playerLeft, this.playerRigth, this.disk, this.isGame, this.timeLeft);
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
		this.disk = new Point(X_POSITION_INITIAL_DISK_LEFT, Y_POSITION_INITIAL);
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
			//verficair colision inicial para darle movimiento 			
			defineMovementDisk();
			
			if(this.isRun){
				switch (this.orientation){
				case 0:
					this.moveDiskDiagonalUpTurnLeft();
					break;
				case 1:
					this.moveDiskRectTurnLeft();
					break;
				case 2:
					this.moveDiskDiagonalDownTurnLeft();
					break;
				case 3:
					this.moveDiskDiagonalUpTurnRigth();
					break;
				case 4:
					this.moveDiskRectTurnRigth();
					break;
				case 5:
					this.moveDiskDiagonalDownTurnRigth();
					break;
				default:
					System.out.println("MOVIMIENTO NO ESPERADO");
					break;
				}
			}
			
			if(this.timeLeft>0)
				this.timeLeft--;
			else
				isGame=false;
			
			writePlayers();		
			sleepMe(100);
		}
		this.setWinner();
	}
	
	private void defineMovementDisk(){
		int tamanoRec = WindowsGame.DISC_TAM/2;
		if(this.isLeftSideDisk()){
			Rectangle diskLeftUp = new Rectangle(this.disk.x, this.disk.y, tamanoRec, tamanoRec); //parte izquierda arriba del disco
			Rectangle diskLeftDown = new Rectangle(this.disk.x, (this.disk.y + tamanoRec), tamanoRec, tamanoRec);//parte izquierda abajo del disco
		
			Rectangle playerRectangleLeft = new Rectangle(this.playerLeft.getPosition().x, this.playerLeft.getPosition().y, WindowsGame.PLAYER_WIDTH, WindowsGame.PLAYER_WIDTH);
			
			if(playerRectangleLeft.contains(diskLeftUp) && playerRectangleLeft.contains(diskLeftDown)){ //toca ambas partes
				this.isRun = true;
				this.orientation = 4;
			} else if(playerRectangleLeft.contains(diskLeftUp)){
				this.isRun = true;
				this.orientation = 5;
			} else if(playerRectangleLeft.contains(diskLeftDown)){
				this.isRun = true;
				this.orientation = 3;
			}
		} else {
			Rectangle diskRigthUp = new Rectangle((this.disk.x + tamanoRec), this.disk.y, tamanoRec, tamanoRec); //parte izquierda arriba del disco
			Rectangle diskRigthDown = new Rectangle((this.disk.x + tamanoRec), (this.disk.y + tamanoRec), tamanoRec, tamanoRec);//parte izquierda abajo del disco			
			
			Rectangle playerRectangleRigth = new Rectangle(this.playerRigth.getPosition().x, this.playerRigth.getPosition().y, WindowsGame.PLAYER_WIDTH, WindowsGame.PLAYER_WIDTH);
			
			if(playerRectangleRigth.contains(diskRigthUp) && playerRectangleRigth.contains(diskRigthDown)){ //toca ambas partes
				this.isRun = true;
				this.orientation = 1;
			} else if(playerRectangleRigth.contains(diskRigthUp)){
				this.isRun = true;
				this.orientation = 2;
			} else if(playerRectangleRigth.contains(diskRigthDown)){
				this.isRun = true;
				this.orientation = 0;
			}
		}
	}
	
	/**
	 *mueve el disco de forma recta hacia la derecha 
	 */
	private void moveDiskRectTurnRigth(){
		this.disk.x++;
	}
	/**
	 *mueve el disco de forma diagonal hacia abajo para la derecha
	 */
	private void moveDiskDiagonalDownTurnRigth(){
		this.disk.x++;
		this.disk.y++;
	}
	/**
	 *mueve el disco de forma diagonal hacia arriba para la derecha
	 */
	private void moveDiskDiagonalUpTurnRigth(){
		this.disk.x++;
		this.disk.y--;
	}
	
	/**
	 *mueve el disco de forma recta hacia la izquierda 
	 */
	private void moveDiskRectTurnLeft(){
		this.disk.x--;
	}
	/**
	 *mueve el disco de forma diagonal hacia abajo para la izquierda
	 */
	private void moveDiskDiagonalDownTurnLeft(){
		this.disk.x--;
		this.disk.y++;
	}
	/**
	 *mueve el disco de forma diagonal hacia arriba para la izquierda
	 */
	private void moveDiskDiagonalUpTurnLeft(){
		this.disk.x--;
		this.disk.y--;
	}
	
	/**
	 * verifica si la posicion del disco es menor a la mitad del ancho del tablero
	 * @return
	 */
	private boolean isLeftSideDisk(){
		return (this.disk.x + WindowsGame.DISC_TAM) < (WindowsGame.PLAYER_WIDTH / 2);
	}
	
	/**
	 *define quien fue el ganador una vez acabado 
	 */
	private void setWinner(){
		if(this.playerLeft.getPoints() == this.playerRigth.getPoints())
			System.out.println("EMPATE");
		else if(this.playerLeft.getPoints() > this.playerRigth.getPoints())
			System.out.println("GANA Jugador: "+this.playerLeft.getName());
		else
			System.out.println("GANA Jugador: "+this.playerRigth.getName());
	}
	
	/**
	 * metodo sleep
	 * @param time
	 */
	private void sleepMe(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the isGame
	 */
	public boolean isGame() {
		return isGame;
	}

	
	
}
