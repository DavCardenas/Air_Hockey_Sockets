package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logic.DataGameClient;
import logic.Player;
import logic.Singlenton;


public class WindowsGame extends JFrame implements Runnable, MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440988445348282291L;

	private Image table; // almacena la imagen del tablero
	private Image disc; // almacena la imagen del disco
	private Image playerBlue; // almacena la imagen del martillo azul
	private Image playerRed; // almacena la imagen del martillo rojo
	
	private Thread paintGame; // hilo que mantiene pintanto los componentes
	private boolean isPaint; // controla el hilo para detener o iniciar
	
	private DataGameClient dataGame; // elemento que contiene toda la informacion del juego
	
	public static final int PLAYER_HEIGHT = 70; // Alto del jugador
	public static final int PLAYER_WIDTH = 72; // Ancho del jugador
	public static final int DISC_TAM = 47; // ancho y alto del disco;
	public static final int TABLE_HEIGHT = 500; // Alto del tablero
	public static final int TABLE_WIDTH = 1000; // ancho del Tablero
	
	public static final String PATH_TABLE = "/images/table.png"; // ruta donde esta la imagen del tablero
	public static final String PATH_DISC = "/images/disc.png"; // ruta donde esta la imagen deel disco
	public static final String PATH_PLAYER_BLUE = "/images/paddleBlue.png"; // ruta donde esta la imagen del martillo azul
	public static final String PATH_PLAYER_RED = "/images/paddleRed.png"; // ruto donde esta la imagen del martillo rojo
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 630;
	
	public static final String NAME = "Nombre: ";
	public static final String SCORE = "Puntaje: ";
	public static final String LEVEL = "Nivel: ";
	public static final String GOALS = "Goles: ";
	public static final String TIME = "Tiempo Restante: ";
	public static final String TITLE = "-- Air Hockey --";
	
	
	private JPanel pnl_Information; // Agrupacion de elementos que informan el estado del juego
	
	private JLabel lb_Player_1; // almacena el nombre del jugador
	private JLabel lb_Player_2;
	private JLabel lb_Score_1; // almacena el puntaje del jugador
	private JLabel lb_Score_2;
	private JLabel lb_Goals_1; // almacena la cantidad de goles del jugador
	private JLabel lb_Goals_2;
	private JProgressBar time; // barra de progreso del tiempo restante 120 s
	private JLabel lb_level; // nivel de velocidad
	private JLabel lb_time; //
	
	private Font fontPer; // sirve para crear fuente personalizadas
	
	private GridBagLayout gridbag; // layout para asignar una estructura de ubicacion
	private GridBagConstraints gbc; // configuracion de los parametros para la ubicacion dentro del layout
	
	public WindowsGame() {
		
		try {
			// Establecemos el look and feel "Metal"
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} 
			catch (UnsupportedLookAndFeelException e) {
				System.out.println(e.getMessage());
			// manejar excepción
			}
			catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			// manejar excepción
			}
			catch (InstantiationException e) {
				System.out.println(e.getMessage());
			// manejar excepción
			}
			catch (IllegalAccessException e) {
				System.out.println(e.getMessage());
			// manejar excepción
			}
		
		gridbag = new GridBagLayout();
		table = new ImageIcon(getClass().getResource(PATH_TABLE)).getImage();
		disc = new ImageIcon(getClass().getResource(PATH_DISC)).getImage();
		playerBlue = new ImageIcon(getClass().getResource(PATH_PLAYER_BLUE)).getImage();
		playerRed = new ImageIcon(getClass().getResource(PATH_PLAYER_RED)).getImage();
		isPaint = false;
		
		dataGame = Singlenton.getDataGame();
		
		setTitle(TITLE);
		setLayout(new BorderLayout());
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		
		pnl_Information = new JPanel();
		pnl_Information.setPreferredSize(new Dimension(WIDTH, 100));
		pnl_Information.setLayout(gridbag);
		fontPer = new Font("Sans", Font.BOLD, 15);
		
		lb_Player_1 = new JLabel(NAME);
		lb_Player_1.setFont(fontPer);
		gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_Player_1, gbc);
		
		lb_Score_1 = new JLabel(SCORE);
		lb_Score_1.setFont(fontPer);
		gbc = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_Score_1, gbc);
		
		lb_Goals_1 = new JLabel(GOALS);
		lb_Goals_1.setFont(fontPer);
		gbc = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_Goals_1, gbc);
		
		lb_time = new JLabel(TIME);
		
		lb_time.setFont(fontPer);
		gbc = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_time, gbc);
		
		time = new JProgressBar(0, 120);
		time.setValue(120);
		gbc = new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(time, gbc);
		
		lb_level = new JLabel(LEVEL);
		fontPer = new Font("Sans", Font.BOLD, 20);
		lb_level.setFont(fontPer);
		gbc = new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_level, gbc);
		
		lb_Player_2 = new JLabel(NAME);
		fontPer = new Font("Sans", Font.BOLD, 15);
		lb_Player_2.setFont(fontPer);
		gbc = new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_Player_2, gbc);
		
		lb_Score_2 = new JLabel(SCORE);
		lb_Score_2.setFont(fontPer);
		gbc = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_Score_2, gbc);
		
		lb_Goals_2 = new JLabel(GOALS);
		lb_Goals_2.setFont(fontPer);
		gbc = new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		pnl_Information.add(lb_Goals_2, gbc);
		
		add(pnl_Information, BorderLayout.NORTH);
		
		addMouseMotionListener(this);
		
	}
	
	/**
	 * actualiza la informacion del juego 
	 * @param playerBlue
	 * @param playerRed
	 * @param level
	 * @param timeRest
	 */
	public void updatePaneInformation(Player playerBlue, Player playerRed, int level, int timeRest) {
		// jugador 1
		lb_Player_1.setText(NAME + playerBlue.getName());
		lb_Goals_1.setText(GOALS + playerBlue.getGoals());
		lb_Score_1.setText(SCORE + playerBlue.getPoints());
		// jugador 2
		lb_Player_2.setText(NAME + playerRed.getName());
		lb_Goals_2.setText(GOALS + playerRed.getGoals());
		lb_Score_2.setText(SCORE + playerRed.getPoints());
		// nivel y tiempo restante
		lb_level.setText(LEVEL + level);
		time.setValue(timeRest);
	}
	
	/**
	 * metodo que pinta los componentes del programa
	 * @param g
	 */
	public void paintComp(Graphics g, Point positionDisc, Point positionPlayerB, Point positionPlayerR) {
		// se pinta primero el tablero con el fin de que quede por debajo de todos los demas elementos
		g.drawImage(table, 0, 130, TABLE_WIDTH, TABLE_HEIGHT, null);
		// pintar la pelota
		g.drawImage(disc, positionDisc.x, positionDisc.y, DISC_TAM, DISC_TAM, null);
		// pintar un jugador
		g.drawImage(playerBlue, positionPlayerB.x, positionPlayerB.y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
		// pintar el segundo jugador
		g.drawImage(playerRed, positionPlayerR.x, positionPlayerR.y, PLAYER_WIDTH, PLAYER_HEIGHT, null);
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point disc = null;
		Point playerBlue = null;
		Point playerRed =  null;
		
		disc = dataGame.getDisc();
		if (dataGame.isBegin()) {
			playerBlue = dataGame.getSelf().getPosition();
			playerRed = dataGame.getCounter().getPosition();
		}else {
			playerBlue = dataGame.getCounter().getPosition();
			playerRed = dataGame.getSelf().getPosition();
		}
		
		paintComp(g, disc, playerBlue, playerRed);
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
	
	public static void main(String[] args) {
		WindowsGame g = new WindowsGame();
		g.setVisible(true);
	}

	@Override
	public void run() {
		
		while (isPaint) {
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point pointPlayer = new Point(e.getX(), e.getY());
		dataGame.getSelf().setPosition(pointPlayer);
		dataGame.getOperations().write(null); // crear el mensaje a enviar
	}
	
}
