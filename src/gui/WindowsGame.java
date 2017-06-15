package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class WindowsGame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440988445348282291L;

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	public static final String NAME = "Nombre: ";
	public static final String SCORE = "Puntaje: ";
	public static final String LEVEL = "Nivel: ";
	public static final String GOALS = "Goles: ";
	public static final String TIME = "Tiempo Restante: ";
	public static final String TITLE = "-- Air Hockey --";
	
	
	private JPanel pnl_Information; // Agrupacion de elementos que informan el estado del juego
	private JPanel pnl_Game; // contenedor para el tablero de juego 
	
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
		
		pnl_Game = new JPanel();
		pnl_Game.setPreferredSize(new Dimension(WIDTH, 500));
	}
	
	
	
	public static void main(String[] args) {
		WindowsGame g = new WindowsGame();
		g.setVisible(true);
	}
	
}
