package logic;

import java.awt.im.InputContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase usada para crear un Servidor 
 * 
 * 
 * 
 */

public class Server implements Runnable{
	
	private ServerSocket severSocket; // sirve para abrir un servidor
	private Socket clientSocket; // sirve para almacenar la conexion entrante
	private int port; // puerto que abre el servido para que se le conecten
	private boolean waitConect; // variable que permite controlar cuando escucha o espera una conexion
	private Thread threadConect; // Hilo en el cual se ejecutara la espera de una nueva conexion
	private Operations operations; // operaciones de lectura y escritura
	private ArrayList<Socket> conections; // vector que almacena los clientes conectados al servidor
	private ArrayList<Operations> drivers; // sirve para controlar cada conexion de entrada
	private Thread threadGames; // sirve para comprobar quien quiere jugar
	private Message messageGlobal; // objeto global de tipo mensaje
	
	
	/**
	 * Constructor que asigna el puerto y crear un servidor
	 * ademas como tal la clase es un runnable que mantiene escuchando
	 * o esperando conexiones
	 * 
	 * @param port Puerto al cual se va a conectar 
	 * @see createServer
	 */
	public Server(int port) {
		this.port = port; 
		waitConect = true;
		threadConect = new Thread(this);
		conections = new ArrayList<Socket>();
		drivers = new ArrayList<>();
		messageGlobal = Singlenton.getMessage();
		createServer(); 
		threadConect.start();
		
	}
	
	/**
	 * Crea un servidor que escucha por el puerto port
	 * 
	 */
	public void createServer() {
		try {
			severSocket = new ServerSocket(port);
			System.out.println("servidor creado y escuchando por: " + port);
		} catch (IOException e) {
			System.out.println("No se pudo crear el servidor ERROR: CreateServer()");
			e.printStackTrace();
		}
	}
	
	/**
	 * sirve para esperar y crear un conexion entrante
	 */
	public void waitConection() {
		try {
			System.out.println("Esperando conexion");
			clientSocket = severSocket.accept();
			conections.add(clientSocket);
			operations = new Operations(clientSocket);
			operations.read();
			waitGame();
			drivers.add(operations);
			System.out.println("Conexion aceptada proveniente de: " + clientSocket.getInetAddress());
		} catch (IOException e) {
			System.out.println("Conexion Cerrada");
		}
	}
	
	/**
	 * sirve para controlar quien desea jugar
	 * de los que estan conectados
	 */
	public void waitGame() {

		threadGames = new Thread(new Runnable() {
			boolean start = true;
			@Override
			public void run() {
				while(start){
					for (Operations operation : drivers) {
						if (!operation.getListMessage().isEmpty()) {
							for (Message msn : operation.getListMessage()) {
								if (!msn.isStartGame()) {
									messageGlobal.addPlayer(msn.getPlayerSelf()); // lo agrega a la lista de jugadores si no lo esta
									if (msn.isInvitation()) {
										
									}
								}
								
							}
						}
					}
				}
			}
		});			
		
		threadGames.start();
	}
	
	/**
	 * sirve para encontrar el indice de la lista que corresponde
	 * a los jugadores para encontrar la misma posicion en la lista
	 * de drivers con el fin de identificar a quien enviar el mensaje
	 * @param p_player jugador a encontrar
	 * @return
	 */
	public int findConection(Player p_player) {
		int conection = 0;
		for (int i = 0; i < messageGlobal.getPlayers().size(); i++) {
			if (messageGlobal.getPlayers().get(i).equals(p_player)) {
				conection = i;
			}
		}
		return conection;
	}
	
	/**
	 * escribe un mensaje a una conexion especifica
	 * @param p_player jugador al que se le enviara el mensaje
	 * @param msn mensaje que se desea enviar
	 */
	public void writeMessage(Player p_player, Message msn) {
		drivers.get(findConection(p_player)).write(msn);
	}

	/**
	 * Metodo que mantieje ejecutando la escucha
	 * o la espera de conexiones
	 */
	@Override
	public void run() {
		while (waitConect) {
			waitConection();
		}
	}
	
	/**
	 * detiene el socket
	 */
	public void stop() {
		waitConect = false;
		try {
			if(severSocket != null)
			severSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setOperations(Operations operations) {
		this.operations = operations;
	}
	
	public Operations getOperations() {
		return operations;
	}
}
