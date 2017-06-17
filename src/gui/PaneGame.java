package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import logic.DataGameClient;
import logic.Singlenton;

public class PaneGame extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -193690441860312070L;
	
	private Image table; // almacena la imagen del tablero
	private Image disc; // almacena la imagen del disco
	private Image playerBlue; // almacena la imagen del martillo azul
	private Image playerRed; // almacena la imagen del martillo rojo
	
	private Thread paintGame; // hilo que mantiene pintanto los componentes
	private boolean isPaint; // controla el hilo para detener o iniciar
	
	private DataGameClient dataGame; // elemento que contiene toda la informacion del juego
	private WindowsGame windows; // Padre de esta ventana
	
	public static final int PLAYER_HEIGHT = 70; // Alto del jugador
	public static final int PLAYER_WIDTH = 72; // Ancho del jugador
	public static final int DISC_TAM = 47; // ancho y alto del disco;
	public static final int TABLE_HEIGHT = 500; // Alto del tablero
	public static final int TABLE_WIDTH = 1000; // ancho del Tablero
	
	public static final String PATH_TABLE = "/imagenes/table.png"; // ruta donde esta la imagen del tablero
	public static final String PATH_DISC = "/imagenes/disc.png"; // ruta donde esta la imagen deel disco
	public static final String PATH_PLAYER_BLUE = "/imagenes/paddleBlue.png"; // ruta donde esta la imagen del martillo azul
	public static final String PATH_PLAYER_RED = "/imagenes/paddleRed.png"; // ruto donde esta la imagen del martillo rojo
	
	public PaneGame(WindowsGame windowsG) {
		setPreferredSize(new Dimension(WIDTH, 500));

		table = new ImageIcon(getClass().getResource(PATH_TABLE)).getImage();
		disc = new ImageIcon(getClass().getResource(PATH_DISC)).getImage();
		playerBlue = new ImageIcon(getClass().getResource(PATH_PLAYER_BLUE)).getImage();
		playerRed = new ImageIcon(getClass().getResource(PATH_PLAYER_RED)).getImage();
		isPaint = false;
		
		windows = windowsG;
		dataGame = Singlenton.getDataGame();
		
		setOpaque(false);
	}
	
	/**
	 * metodo que pinta los componentes del programa
	 * @param g
	 */
	public void paintComp(Graphics g, Point positionDisc, Point positionPlayerB, Point positionPlayerR) {
		// se pinta primero el tablero con el fin de que quede por debajo de todos los demas elementos
		g.drawImage(table, 0, 0, TABLE_WIDTH, TABLE_HEIGHT, null);
		// pintar la pelota
		g.drawImage(disc, positionDisc.x, positionDisc.y, DISC_TAM, DISC_TAM, null);
		
		// pintar un jugador
		g.drawImage(playerBlue, positionPlayerB.x, positionPlayerB.y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
		// pintar el segundo jugador
		g.drawImage(playerRed, positionPlayerR.x, positionPlayerR.y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
		
	}
	
	/**
	 * inicia el hilo de pintado
	 */
	public void startPaint() {
		paintGame = new Thread(this);
		isPaint = true;
		paintGame.start();
	}
	
	/**
	 * detiene la ejecucion del hilo de pintado
	 */
	public void stopPaint() {
		isPaint = false;
	}
	

	@Override
	public void run() {
		Point disc = dataGame.getDisc();
		Point playerBlue;
		Point playerRed;
		while (isPaint) {
			//paintComp(this.getGraphics(), );
			windows.updatePaneInformation(dataGame.getSelf(), dataGame.getCounter(), dataGame.getLevel(), dataGame.getTimeGame());
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
