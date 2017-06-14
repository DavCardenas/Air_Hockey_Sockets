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

import logic.DataGameClient;
import logic.Message;
import logic.MessageInvitationClient;
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
	
	private DataGameClient dataGame; // elemento global que contiene la informacion del juego
	private Thread updateList; // hilo que permitira actualizar constantemente la informacion de la lista obtienendo la del singlenton
	private boolean isUpdateList; // sirve para controlar cuando terminar el hilo de actualizacion de la lista
	
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
		dataGame = Singlenton.getDataGame(); // hace referencia al objeto global que contiene la informacion del juego
		isUpdateList = true;
		
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
				doInvated(); // hace la invitacion a jugar
				if (dataGame.isGame()) {
					minnimizar();
					createGame();
				}
			}
		});
		gbc = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(btn_play, gbc);
		
		updatePlayerListGraphics();
	}
	
	/**
	 * se mantiene esperando la respuesta del invitado
	 */
	public void waitAnswer() {
		int option = JOptionPane.showConfirmDialog(null, "Esperando Respuesta...");
		
		if (option == JOptionPane.OK_OPTION) {
			
		}else {
			
		}
	}
	
	/**
	 * verifica que no tenga solicitudes de juego pendientes
	 */
//	public void	waitNotification() {
//		int option; // alamcena la opcion elegida
//		MessageInvitationClient message; // mensaje que sera enviado
//		if (dataGame.isGame()) {
//			if (dataGame.getInvitations().size() > 0) {
//				for (Player playerCounter : dataGame.getInvitations()) {
//					option = JOptionPane.showConfirmDialog(this, "Deseas jugar contra: " + playerCounter.getName());
//					if (option == JOptionPane.OK_OPTION) {
//						message = new MessageInvitationClient(dataGame.getSelf().getName(), playerCounter.getName());
//						message.setIsAccept("SI");
//						WindowsLoggin.client.getOperations().write(message);
//					}else {
//						
//					}
//				}
//			}
//		}
//	}
	
	/**
	 * envia un mensaje de invitacion al elemento seleccionado en el combobox
	 */
	public void doInvated() {
		String invited = (String) cbx_players.getSelectedItem(); 
		MessageInvitationClient invitation = new MessageInvitationClient(dataGame.getSelf().getName(), invited);
		WindowsLoggin.client.getOperations().write(invitation);
	}
	
	/**
	 * actualiza la lista de jugadores conectados
	 * @param players lista de jugadores conectados
	 */
	public void update(ArrayList<Player> players) {
		model = new DefaultComboBoxModel<>();
		if(players != null){
			for (Player player : players) {
				if (!player.getName().equals(dataGame.getSelf().getName())) {
					model.addElement(player);
				}
			}
		}
		
		
		cbx_players.setModel(model);
		cbx_players.repaint();
		//cbx_players.updateUI();
	}
	
	/**
	 * inicia el hilo para actualizar la lista de los jugadores conectados
	 * obtiene los valores de actualizacion del singlenton
	 */
	public void updatePlayerListGraphics() {
		updateList = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isUpdateList) {
					update(dataGame.getPlayerList());
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		updateList.start();
	}
	
	/**
	 * sirve para detener el hilo de actualizacion
	 */
	public void stopUpdateList() {
		isUpdateList = false;
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
