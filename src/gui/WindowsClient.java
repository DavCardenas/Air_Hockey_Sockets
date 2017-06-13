package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logic.Message;
import logic.Player;
import logic.Singlenton;

public class WindowsClient extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7542849052198916995L;
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 150;
	public static final String TITLE = "Jugar Contra ";
	
	private JComboBox<Player> cbx_players;
	private DefaultComboBoxModel<Player> model;
	private JLabel lb_title;
	private JButton btn_play;
	private GridBagLayout gridbag;
	private GridBagConstraints gbc;
	private WindowsGame game;
	
	private Message message;
	
	public WindowsClient() {
		
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
		message = Singlenton.getMessage();
		
		setTitle(TITLE);
		setLayout(gridbag);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		
		lb_title = new JLabel(TITLE);
		lb_title.setFont(new Font("Sans", Font.BOLD, 20));
		gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_title, gbc);
		
		model = new DefaultComboBoxModel<>();
		cbx_players = new JComboBox<>(model);
		gbc = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(cbx_players, gbc);
		
		btn_play = new JButton("Jugar");
		btn_play.setFont(new Font("Sans", Font.BOLD, 15));
		btn_play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				minnimizar();
				createGame();
			}
		});
		gbc = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(btn_play, gbc);
	}
	
	/**
	 * actualiza la lista de jugadores conectados
	 * @param players lista de jugadores conectados
	 */
	public void update(ArrayList<Player> players) {
		model = new DefaultComboBoxModel<>();
		for (Player player : players) {
			model.addElement(player);
		}
		
		cbx_players.setModel(model);
		cbx_players.updateUI();
	}
	
	/**
	 * sirve para minimizar la ventana 
	 */
	public void minnimizar() {
		JOptionPane.showMessageDialog(this, "Se ha conectado correctamente");
		this.setExtendedState(ICONIFIED);
	}
	
	public void createGame() {
		game = new WindowsGame();
		game.setVisible(true);
	}
	
	
}
