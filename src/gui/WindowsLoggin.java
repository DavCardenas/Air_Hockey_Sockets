package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logic.Client;
import logic.Message;
import logic.MessageClient;
import logic.MessageInterface;
import logic.Player;

public class WindowsLoggin extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6011358952673925748L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final String TITLE = "Ingreso";
	
	private Client client;
	private Player player;
	
	private JLabel lb_Title;
	private JLabel lb_Port;
	private JTextField txt_Port;
	private JLabel lb_Dir;
	private JTextField txt_Dir;
	private JLabel lb_name;
	private JTextField txt_name;
	private JButton btn_Connect;
	private JButton btn_Disconect;
	private GridBagLayout gridbag;
	private GridBagConstraints gbc;
	private WindowsClient Win_client;
	
	public WindowsLoggin() {
		
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
		setLayout(gridbag);
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		
		lb_Title = new JLabel(TITLE);
		lb_Title.setFont(new Font("Sans", Font.BOLD, 20));
		gbc = new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_Title, gbc);
		
		lb_Port = new JLabel("Puerto: ");
		gbc = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_Port, gbc);
		
		txt_Port = new JTextField(10);
		gbc = new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(txt_Port, gbc);
		
		lb_Dir = new JLabel("Direccion IP: ");
		gbc = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_Dir, gbc);
		
		txt_Dir = new JTextField(10);
		gbc = new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(txt_Dir, gbc);
		
		lb_name = new JLabel("Nombre: ");
		gbc = new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_name, gbc);
		
		txt_name = new JTextField(10);
		gbc = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(txt_name, gbc);
		
		btn_Connect = new JButton("Conectar");
		btn_Connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txt_Port.getText().isEmpty() && !txt_Dir.getText().isEmpty() && !txt_name.getText().isEmpty()) {
					try {
						createClient(Integer.parseInt(txt_Port.getText()), txt_Dir.getText());
						createPlayer(txt_name.getText());
						openWinClient();
						minnimizar();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Puerto: Solo digitos \nDireccion: xxx.xxx.xxx.xxx");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Ingrese todos los campos");
				}
			}
		});
		gbc = new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(btn_Connect, gbc);
		
		btn_Disconect = new JButton("Desconectar");
		btn_Disconect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				desconect();
			}
		});
		gbc = new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(btn_Disconect, gbc);
	}
	
	/**
	 * sirve para minimizar la ventana 
	 */
	public void minnimizar() {
		JOptionPane.showMessageDialog(this, "Se ha conectado correctamente");
		this.setExtendedState(ICONIFIED);
	}
	
	/**
	 * crea un cliente logico
	 * @param port puerto al que se va a conectar
	 * @param dir direccion ip a la que se va a conectar
	 */
	public void createClient(int port, String dir) {
		client = new Client(port, dir);
	}
	
	/**
	 * se desconecta de forma logica
	 */
	public void desconect() {
		client.closeCon();
	}
	
	/**
	 * crea un jugador logico
	 * @param name nombre del jugador
	 */
	public void createPlayer(String name) {
		player = new Player();
		player.setName(name);
		registerPlayer(player);
	}
	
	/**
	 * permite abrir la ventana para elegir un contrario
	 */
	public void openWinClient() {
		Win_client = new WindowsClient();
		Win_client.setVisible(true);
	}
	
	public void registerPlayer(Player player) {
		MessageInterface msn = new MessageClient(player.getName(), "Inicio");
		client.getOperations().write(msn);
	}
	
	
}
