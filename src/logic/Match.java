package logic;

import java.awt.Point;
import java.awt.Rectangle;

import gui.Sounds;
import gui.WindowsGame;

public class Match implements Runnable{
	
	public static final int TOTAL__TIME_VALUE = 120; // TIEMPO TOTAL DE LA PARTIDA EN SEGUNDOS
	public static final int NS_FOR_SECONDS = 1000000000; // sirve para hacer la conersion de nano segundo a segundos
	public static final int RANGE_CENTER = 100; //rango que tendra el disk con respecto al centro
	public static final int X_POSITION_INITIAL_PLAYER_LEFT = 15; //posicion inicial en x para el jugador izquierda
	public static final int X_POSITION_INITIAL_PLAYER_RIGTH = WindowsGame.WIDTH - RANGE_CENTER; //posicion en x para el jugador derecha
	public static final int Y_POSITION_INITIAL = WindowsGame.HEIGHT /2; //posicion inicial para todos en y
	public static final int X_POSITION_INITIAL_DISK_LEFT = (WindowsGame.WIDTH /2)-RANGE_CENTER; //posicion inicial x cuando empieza izquierrda
	public static final int X_POSITION_INITIAL_DISK_RIGTH = (WindowsGame.WIDTH /2) + RANGE_CENTER; //posicion inicial x cuando empieza derecha
	public static final int AMMOUNT_POINTS_FOR_GOAL_INITIAL = 60; // cantidad de puntos a sumar por gol
	public static final int AMMOUNT_POINTS_FOR_GOAL_COUNTER = 5; // cantidad de puntos a restar por gol
	public static final int AMMOUNT_INCREMENT_POINT_LEVEL = 10; // puntos de incremento por nivel
	
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
	private Rectangle rectangleLeft; // Arco del jugador Izquierdo
	private Rectangle rectangleRigth; // Arco del jugador Derecho
	private Rectangle tableGame; // tablero de juego sirve para verificar las colisiones
	private int levelGame; // sirve para almacenar el nivel en el que se encuentra el juego
	private Sounds sound; // sirce para agregar sonido
	
	
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
		tableGame = new Rectangle(0, 130, WindowsGame.TABLE_WIDTH, WindowsGame.TABLE_HEIGHT);
		this.rectangleLeft = new Rectangle(0, 340, 30, 80);
		this.rectangleRigth = new Rectangle((WindowsGame.TABLE_WIDTH - 30), 340, 30, 80);
		this.assignInitialPosition(player1, player2);
		this.isRun = false;
		levelGame = 1;
		isGame = true;
		game = new Thread(this);
		sound = new Sounds();
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
	 * permite cambiar la posicion de un jugador
	 * @param p_name
	 * @param point
	 */
	public void changePosition(String p_name, Point point) {
		if (playerLeft.getName().equals(p_name)) {
				playerLeft.setPosition(point);
		}else if(playerRigth.getName().equals(p_name)){
				playerRigth.setPosition(point);
		}
	}
	
	/**
	 * cambia la direccion de memoria del disco
	 */
	public Point changeDirDisk() {
		return new Point(disk);
	}
	
	/**
	 * metodo para esribir a jugadores
	 */
	public void writePlayers(){
		
		MessageMatch match = new MessageMatch(Player.changeDir(this.playerLeft), Player.changeDir(this.playerRigth), changeDirDisk(), this.isGame, this.timeLeft, this.levelGame);
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
		this.playerLeft.setPosition(new Point(X_POSITION_INITIAL_PLAYER_LEFT, Y_POSITION_INITIAL + WindowsGame.PLAYER_HEIGHT/2));
	}
	
	/**
	 * JUGADOR DE LA IZQUIERDA ASIGNAR LA POSICION 
	 */
	private void assignInitialPositionPlayerRigth(String namePlayer){
		this.playerRigth = new Player(namePlayer);
		this.playerRigth.setPosition(new Point(X_POSITION_INITIAL_PLAYER_RIGTH, Y_POSITION_INITIAL + WindowsGame.PLAYER_HEIGHT/2));
	}
	/**
	 * metodo para crear y asignar posiciones iniciales al mazo
	 * siempre al lado izquierdo
	 */
	private void createdisk(){
		this.isRun = false;
		this.disk = new Point(X_POSITION_INITIAL_DISK_LEFT, Y_POSITION_INITIAL +  WindowsGame.DISC_TAM/2);
	}
	
	/**
	 * metodo para crear y asignar posiciones iniciales al mazo
	 * siempre al lado izquierdo
	 */
	private void creatediskRigth(){
		this.isRun = false;
		this.disk = new Point(X_POSITION_INITIAL_DISK_RIGTH, Y_POSITION_INITIAL +  WindowsGame.DISC_TAM/2);
	}

	/**
	 * sirve para calcular cuando y quien hace puntos
	 */
	private void definePoints() {		
		int tamRec = WindowsGame.DISC_TAM;
		Rectangle disk = new Rectangle(this.disk.x , this.disk.y , WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);
		
		
		if (rectangleLeft.contains(new Point(disk.x - tamRec, disk.y))
				|| rectangleLeft.contains(new Point(disk.x, disk.y + tamRec))) {
			//System.out.println("Hizo Gol el Derecho");
			playerRigth.addGoals(); // suma un gol
			addPointPlayer(playerRigth); // agrega puntos al jugador
			playerLeft.goalOwnDoor(); //resta goles al jugador de la izquierda pore recibir gol
			createdisk();
			sound.play(Sounds.RUTA_SOUND_POINT, false);
		}else if (rectangleRigth.contains(new Point(disk.x + tamRec, disk.y))
				|| rectangleRigth.contains(new Point(disk.x + tamRec, disk.y + tamRec))) {
			System.out.println("Hizo Gol el Izquierdo");
			playerLeft.addGoals(); // suma el gol
			addPointPlayer(playerLeft); // agrega puntos
			playerRigth.goalOwnDoor(); //resta goles al jugador de la derecha por recibir gol
			creatediskRigth();
			sound.play(Sounds.RUTA_SOUND_POINT, false);
		}
	}
	
	/**
	 * el nivel se aumenta dependiendo el tiempo transucrrido
	 */
	private void refreshLevel() {
		if(isLevelOne()){
			this.levelGame = 1;
		} else if (isLevelTwo()){
			this.levelGame = 2;
		} else if (isLevelThree()){
			this.levelGame = 3;
		} else if (isLevelFour()){
			this.levelGame = 4;
		} else if (isLevelFive()){
			this.levelGame = 5;
		} else if (isLevelSix()){
			this.levelGame = 6;
		} else {
			System.out.println("NIVEL NO ESPERADO timpo restante: "+this.timeLeft);
		}
	}
	
	/**
	 * agrega puntos al jugador correspondiente al nivel en el que se encuentra el partido
	 * @param player
	 */
	private void addPointPlayer(Player player) {	
		if (levelGame == 1) 
			player.setPoints(player.getPoints() + AMMOUNT_POINTS_FOR_GOAL_INITIAL);
		else 
			player.setPoints(player.getPoints() + (AMMOUNT_POINTS_FOR_GOAL_INITIAL + (AMMOUNT_INCREMENT_POINT_LEVEL * (levelGame-1))));		
	}

	@Override
	public void run() {
		long refereciaTimer = System.nanoTime();
		
		double timeTranscurrido;
		double delta = 0;
		
		while (isGame) {	
			final long timerStart = System.nanoTime();
			//verficair colision inicial para darle movimiento 		
			
			defineMovementDisk();
			definePoints();
			
			moveDisc();
			
			
			writePlayers();		
			
			timeTranscurrido = timerStart - refereciaTimer;
			refereciaTimer = timerStart; 
			
			delta += timeTranscurrido / NS_FOR_SECONDS;
			
			if (delta >= 1) {
				delta--;
				if(this.timeLeft>0){
					this.timeLeft -= 1;
					this.refreshLevel();
				}else{
					isGame=false;
				}
			}
			
			if (playerLeft.getGoals() == 7 || playerRigth.getGoals() == 7) {
				isGame = false;
			}
			
			sleepMe(200);
		}
		this.setWinner();
	}
	
	/**
	 * mueve el disco dependiendo de la orientacion
	 */
	public void moveDisc() {
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
	}
	
	private void defineMovementDisk(){
		int tamanoRec = WindowsGame.DISC_TAM/2;
		int tamanoPlay = WindowsGame.PLAYER_HEIGHT/2;
		Rectangle aux = new Rectangle(this.disk.x - tamanoRec, this.disk.y - tamanoRec, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);
		
		if (tableGame.contains(aux)) {
			moveDisc();
		}
		
		if(this.isLeftSideDisk()){
			Rectangle diskLeftUp = new Rectangle(this.disk.x, this.disk.y, tamanoRec, tamanoRec); //parte izquierda arriba del disco
			Rectangle diskLeftDown = new Rectangle(this.disk.x, (this.disk.y + tamanoRec), tamanoRec, tamanoRec);//parte izquierda abajo del disco
		
			Rectangle playerRectangleLeft = new Rectangle(this.playerLeft.getPosition().x - tamanoPlay, this.playerLeft.getPosition().y - tamanoPlay, WindowsGame.PLAYER_WIDTH, WindowsGame.PLAYER_WIDTH);
			
			if (playerRectangleLeft.contains(diskLeftUp.x, diskLeftUp.y + tamanoRec) // toca ambos rectangulos
					&& playerRectangleLeft.contains(diskLeftDown.x, diskLeftDown.y)) {
				sound.play(Sounds.RUTA_SOUND_BALL, false);
				this.isRun = true;
				this.orientation = 4;
			}else if (playerRectangleLeft.contains(diskLeftUp.x, diskLeftUp.y)) {
				sound.play(Sounds.RUTA_SOUND_BALL, false);
				this.isRun = true;
				this.orientation = 5;
			}else if (playerRectangleLeft.contains(diskLeftDown.x, diskLeftDown.y + tamanoRec)) {
				sound.play(Sounds.RUTA_SOUND_BALL, false);
				this.isRun = true;
				this.orientation = 3;
			}
			
		} else {
			Rectangle diskRigthUp = new Rectangle((this.disk.x + tamanoRec), this.disk.y, tamanoRec, tamanoRec); //parte derecha arriba del disco
			Rectangle diskRigthDown = new Rectangle((this.disk.x + tamanoRec), (this.disk.y + tamanoRec), tamanoRec, tamanoRec);//parte derecha abajo del disco			
			
			Rectangle playerRectangleRigth = new Rectangle(this.playerRigth.getPosition().x - tamanoPlay, this.playerRigth.getPosition().y -  tamanoPlay, WindowsGame.PLAYER_WIDTH, WindowsGame.PLAYER_WIDTH);
			
			if(playerRectangleRigth.contains(diskRigthUp.x + tamanoRec, diskRigthUp.y + tamanoRec) // toca ambos rectangulos
					&& playerRectangleRigth.contains(diskRigthDown.x + tamanoRec, diskRigthDown.y)){ 
				this.isRun = true;
				this.orientation = 1;
				sound.play(Sounds.RUTA_SOUND_BALL, false);
			} else if(playerRectangleRigth.contains(diskRigthUp.x + tamanoRec , diskRigthUp.y)){
				this.isRun = true;
				this.orientation = 2;
				sound.play(Sounds.RUTA_SOUND_BALL, false);
			} else if(playerRectangleRigth.contains(diskRigthDown.x + tamanoRec, diskRigthDown.y + tamanoRec)){
				this.isRun = true;
				this.orientation = 0;
				sound.play(Sounds.RUTA_SOUND_BALL, false);
			}
		}
	}
	
	/**
	 *mueve el disco de forma recta hacia la derecha 
	 */
	private void moveDiskRectTurnRigth(){
		int nextX = (this.disk.x + this.getSpeed());
		Rectangle positionDiskNext = new Rectangle(nextX + (WindowsGame.DISC_TAM/2), this.disk.y, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);			
		if (tableGame.contains(positionDiskNext)) {			
			this.disk.setLocation(nextX, this.disk.y);
		}else {
			orientation = 1;
		}
	}
	/**
	 *mueve el disco de forma diagonal hacia abajo para la derecha
	 */
	private void moveDiskDiagonalDownTurnRigth(){
		int tamRec = WindowsGame.DISC_TAM/2;
		int nextX = (this.disk.x + this.getSpeed());
		int nextY = (this.disk.y + this.getSpeed());
		Rectangle positionDiskNext = new Rectangle(nextX + tamRec, nextY +  tamRec, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);			
		if(tableGame.contains(positionDiskNext)){
			this.disk.setLocation(nextX, nextY);
		} else {
			if (nextX + tamRec > (WindowsGame.TABLE_WIDTH - 100)) { // golpea pared derecha
				this.orientation = 2;
			}else if (nextX < (WindowsGame.TABLE_WIDTH - 100)) { // golpea pared inferior 
				this.orientation = 3;
			} else{
				System.out.println("x:"+nextX+" - y:"+nextY);
			}
		}
	}
	/**
	 *mueve el disco de forma diagonal hacia arriba para la derecha
	 */
	private void moveDiskDiagonalUpTurnRigth(){
		int tamRec = WindowsGame.DISC_TAM/2;
		int nextX = this.disk.x + this.getSpeed();
		int nextY = this.disk.y - this.getSpeed();
		Rectangle positionDiskNext = new Rectangle(nextX + tamRec, nextY -  tamRec, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);			
		if(tableGame.contains(positionDiskNext)){
			this.disk.setLocation(nextX, nextY);
		} else {
			if (nextY < 160) { //si golpea en la parte superior
				this.orientation = 5;
			}else if (nextY >= 160) { // golpea en la pared derecha
				this.orientation = 0;
			} else{
				System.out.println("x:"+nextX+" - y:"+nextY);
			}
		}
	}
	
	/**
	 *mueve el disco de forma recta hacia la izquierda 
	 */
	private void moveDiskRectTurnLeft(){
		int tamRec = WindowsGame.DISC_TAM/2;
		int nextX = this.disk.x - this.getSpeed();
		Rectangle positionDiskNext = new Rectangle(nextX - tamRec, this.disk.y, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);			
		if (tableGame.contains(positionDiskNext)) {
			this.disk.setLocation(nextX, this.disk.y);
		}else {
			orientation = 4;
		}
		//this.disk.x--;
	}
	/**
	 *mueve el disco de forma diagonal hacia abajo para la izquierda
	 */
	private void moveDiskDiagonalDownTurnLeft(){
		int tamRec = WindowsGame.DISC_TAM/2;
		int nextX = this.disk.x - this.getSpeed();
		int nextY = this.disk.y + this.getSpeed();
		Rectangle positionDiskNext = new Rectangle(nextX - tamRec, nextY +  tamRec, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);			
		if(tableGame.contains(positionDiskNext)){
			this.disk.setLocation(nextX, nextY);
		} else {
			if (nextX < 30) { // golpea la pared izquierda
				this.orientation = 5;
			}else if(nextX >= 30){ // golpea la pared inferior
				this.orientation = 0;				
			} else{
				System.out.println("x:"+nextX+" - y:"+nextY);
			}	
		}
	}
	/**
	 *mueve el disco de forma diagonal hacia arriba para la izquierda
	 */
	private void moveDiskDiagonalUpTurnLeft(){
		int tamRec = WindowsGame.DISC_TAM/2;
		int nextX = this.disk.x - this.getSpeed();
		int nextY = this.disk.y - this.getSpeed();
		Rectangle positionDiskNext = new Rectangle(nextX - tamRec, nextY - tamRec, WindowsGame.DISC_TAM, WindowsGame.DISC_TAM);			
		if(tableGame.contains(positionDiskNext)){
			this.disk.setLocation(nextX, nextY);
		} else {
			if (nextX < 5) { // golpea la pared izquierda
				this.orientation = 3;
			}else if (nextX > 5) { // golpea la pared superior
				this.orientation = 2;
			} else{
				System.out.println("x:"+nextX+" - y:"+nextY);
			}
		}
	}
	
	/**
	 * retorna velocidad a partir del nivel
	 * @return
	 */
	private int getSpeed(){
		if(levelGame == 1){
			return 15;
		} else if (levelGame == 2){
			return 18;
		} else if (levelGame == 3){
			return 21;
		} else if (levelGame == 4){
			return 25;
		} else if (levelGame == 5){
			return 28;
		} else if (levelGame == 6){
			return 33;
		} else {
			return 0;
		}
	}
	
	/**
	 * verifica si la posicion del disco es menor a la mitad del ancho del tablero
	 * @return
	 */
	private boolean isLeftSideDisk(){
		return (this.disk.x + WindowsGame.DISC_TAM/2) < (WindowsGame.TABLE_WIDTH / 2);
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
	 * 120 s - 100 s
	 * @return
	 */
	private boolean isLevelOne(){
		return this.timeLeft<=TOTAL__TIME_VALUE && this.timeLeft>=100;
	}
	
	/**
	 * 99 s - 80 s
	 * @return
	 */
	private boolean isLevelTwo(){
		return this.timeLeft<=99 && this.timeLeft>=80;
	}
	
	/**
	 * 79 s - 60 s 
	 * @return
	 */
	private boolean isLevelThree(){
		return this.timeLeft<=79 && this.timeLeft>=60;
	}
	
	/**
	 *59 s - 40 s 
	 * @return
	 */
	private boolean isLevelFour(){
		return this.timeLeft<=59 && this.timeLeft>=40;
	}
	
	/**
	 * 39 s - 20 s 
	 * @return
	 */
	private boolean isLevelFive(){
		return this.timeLeft<=39 && this.timeLeft>=20;
	}
	
	/**
	 * 19 s - 0 s ¡gol ultimo segundo!
	 * @return
	 */
	private boolean isLevelSix(){
		return this.timeLeft<=19 && this.timeLeft>=0;
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
	
}
