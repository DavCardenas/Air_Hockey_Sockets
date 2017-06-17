package logic;

import java.io.IOException;
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
	private OperationsServer operations; // operaciones de lectura y escritura
	private ArrayList<Socket> conections; // vector que almacena los clientes conectados al servidor
	public static ArrayList<OperationsServer> drivers; // sirve para controlar cada conexion de entrada
	private Thread threadGames; // sirve para comprobar quien quiere jugar
	public static ArrayList<Player> listPlayers; // lista de jugadores
	public static ArrayList<Match> listMatchs; //lista de partidas 
	
	
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
		listPlayers = new ArrayList<>();
		createServer(); 
		threadConect.start();
		listMatchs = new ArrayList<>();
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
			operations = new OperationsServer(clientSocket);
			operations.read();
			
			waitGame();
			System.out.println("Conexion aceptada proveniente de: " + clientSocket.getInetAddress());
		} catch (IOException e) {
			System.out.println("Conexion Cerrada");
		}
	}
	

	/**
	 *devulve lista de jugadores
	 */
	private ArrayList<Player> playerList(){
		ArrayList<Player> players = new ArrayList<>();
		for (int i = 0; i < listPlayers.size(); i++) {
			players.add(listPlayers.get(i));
		}
		return players;
		
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
					synchronized(drivers){
						for (int i = 0; i < drivers.size(); i++) {
							if(drivers.get(i) != null){
								drivers.get(i).write(new MessageListPlayersServer(playerList()));
								try {
									Thread.sleep(4000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}								
							}
						}					
					}
					synchronized(listMatchs){
						for (int i = 0; i < listMatchs.size(); i++) {					
							System.out.println("Ya hay partido");
							System.out.println(listMatchs.get(i).getPlayerBegin());
						}
					}
				}
			}
		});			
		
		threadGames.start();
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
	
	/**
	 * sirve para encontrar el indice de la lista que corresponde a los
	 * jugadores para encontrar la misma posicion en la lista de drivers con el
	 * fin de identificar a quien enviar el mensaje
	 * 
	 * @param p_player
	 *            jugador a encontrar
	 * @return
	 */
	public int findConection(String p_player) {
		int conection = 0;
		for (int i = 0; i < Server.listPlayers.size(); i++) {
			if (Server.listPlayers.get(i).getName().equals(p_player)) {
				conection = i;
			}
		}
		return conection;
	}

	
	
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setOperations(OperationsServer operations) {
		this.operations = operations;
	}
	
	public OperationsServer getOperations() {
		return operations;
	}
}
