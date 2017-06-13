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

import logic.Server;

public class WindowsServer extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8387214202017633392L;

	private Server server;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 230;
	public static final String TITLE = "Crear Servidor";
	
	private JLabel lb_Title;
	private JLabel lb_Port;
	private JTextField txt_Port;
	private JButton btn_Accept;
	private JButton btn_Stop;
	private GridBagLayout gridbag;
	private GridBagConstraints gbc;
	
	public WindowsServer() {
		
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
		gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_Title, gbc);
		
		lb_Port = new JLabel("Ingrese el puerto de escucha");
		gbc = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(lb_Port, gbc);
		
		txt_Port = new JTextField(10);
		gbc = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(txt_Port, gbc);
		
		btn_Accept = new JButton("Crear");
		btn_Accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txt_Port.getText().isEmpty()) {
					try {
						createServer(Integer.parseInt(txt_Port.getText()));
						minnimizar();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Solo digitos");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Ingrese el puerto (Solo digitos)");
				}
			}
		});
		gbc = new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(btn_Accept, gbc);
		
		btn_Stop = new JButton("Detener");
		btn_Stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}
		});
		gbc = new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);
		add(btn_Stop, gbc); 
	}
	
	/**
	 * sirve para minimizar la ventana
	 */
	public void minnimizar() {
		JOptionPane.showMessageDialog(this, "Se ha creado correctamente");
		this.setExtendedState(ICONIFIED);
	}
	
	/**
	 * crea un servidor logico
	 * @param port puerto de escucha para el servidor
	 */
	public void createServer(int port) {
		server = new Server(port);
	}
	
	/**
	 * detiene el servidor, cierra el puerto
	 */
	public void stopServer() {
		if (server != null) {
			server.stop();
			JOptionPane.showMessageDialog(this, "Detenido");
		}
	}
}
