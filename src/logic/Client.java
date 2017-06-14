package logic;

import java.io.IOException;
import java.net.Socket;

/**
 * Clase usada para realizar una conexion hacia otro socket 
 * 
 * 
 * 
 */

public class Client{
	
	private Socket clientSocket; // socket que se usara para realizar la conexion hacia otro socket
	private String ip; // almacena la ip a la cual se conecta
	private int port; // almacena el puerto al cual se va a conectar
	private int attemps; // almacena cuantos intentos de conexion llevan
	private OperationsClient operations; // operacion de lectura y escritura
	private Player self;
	/**
	 * Constructor que asigna la direccion y el puerto
	 * 
	 * @param ipDirection Direccion a la cual se va a conectar 
	 * @param port Puerto al cual se va a conectar n
	 * @param queueMessageClient Cola de mensajes leidos por el cliente
	 * @param queueMessageServer Cola de mensahe leidos por el servidor
	 * @see createConection
	 */
	public Client(int port, String ipDirection) {
		this.port = port;
		this.ip = ipDirection;
		attemps = 1;
		
		
		createConection();
	}
	
	/**
	 * Crea una conexion hacia un socket con puerto port
	 * y direccion "ip"
	 * 
	 */
	public void createConection() {
		try {
			clientSocket = new Socket(ip, port);
			operations = new OperationsClient(clientSocket, this.self);
			operations.read();
			//createDataFlow(); 
			System.out.println("Conexion creada hacia: " + ip + " en el puerto: " + port);
		} catch (IOException e) {
			System.out.println("No se pudo establecer la conexion");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//if (attemps <= 5) {
				System.out.println("Intentando conectarse a : " + "Ip -> " + ip + " Puerto -> " + port);
				System.out.println("Intento numero: " + attemps);
				attemps++;
				createConection();
				//}
			
			System.out.println("No mas Intento se cierra la conexion");
		}
	}
	
	
	/**
	 * @return the self
	 */
	public Player getSelf() {
		return self;
	}

	/**
	 * @param self the self to set
	 */
	public void setSelf(Player self) {
		this.self = self;
	}

	public void closeCon() {
		try {
			clientSocket.close();
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
	
	public void setOperations(OperationsClient operations) {
		this.operations = operations;
	}
	
	public OperationsClient getOperations() {
		return operations;
	}
}
