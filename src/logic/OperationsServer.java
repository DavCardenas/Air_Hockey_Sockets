package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OperationsServer {

	private ObjectInputStream inputStream; // flujo de lectura de datos
	private ObjectOutputStream outputStream; // flujo de escritura de datos
	private Thread reading; // hilo de lectura
	private Thread writring; // hilo de escritura
	private Socket conection; // socket del cual obtendra el flujo de entrada y salida
	private MessageInterface message; // mensaje para leer
	private boolean read; // controla cuando se inicia a leer y cuando se detiene
	private boolean write; // controla cuando se inicia a escribir y cuando se detiene
	
	public OperationsServer(Socket socket) {
		conection = socket;
		read = true;
		write = true;
	}
	
	/**
	 * lee constantemente
	 */
	public synchronized void read() {
		reading = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					inputStream = new ObjectInputStream(
							conection.getInputStream());
				} catch (IOException e) {
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}

				while (read) {
					try {
						if (inputStream != null) {
							message = (MessageInterface) inputStream.readObject();
							
							if (message != null) {
								System.out.println("Mensaje del Cliente: " + message.getType());
								if (message.getType().equals("Inicio")) {
									Server.listPlayers.add(new Player(message.getMessage(), null));
									System.out.println(message.getMessage());									
									//alwaysWritePlayers();
									//write(new MessageListPlayersServer(Server.listPlayers));
								}
								//listMessage.add(listMessage.size(), message);
							}
						}
					} catch (ClassNotFoundException | IOException e) {
						
						// Tolerancia a Fallos va aqui
						System.out.println("Toleranacia a Fallos");
						closeDataFlowRead();
						closeConection();
						read = false;
						//e.printStackTrace();
					}

					// ////////////////////////////////////////////////
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// ///////////////////////////////////////////////
				}
			}
		});

		reading.start();
		Server.drivers.add(this);

	}

	

	/**
	 * escribe una unica vez
	 * 
	 * @param message
	 *            objeto que se va a enviar
	 */
	public synchronized void write(MessageInterface message) {
		try {
			if (outputStream == null) {
				outputStream = new ObjectOutputStream(
						conection.getOutputStream());
			}
			outputStream.writeObject(message);

		} catch (IOException e) {
			System.out.println("sale");
			e.printStackTrace();
		}
	}
	/**
	 * escribe un mensaje constantemente
	 * 
	 * @param message objeto que se va a enviar
	 */
	public void alwaysWritePlayers() {
		writring = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					outputStream = new ObjectOutputStream(
							conection.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

				while (write) {
					try {
						outputStream.writeObject(new MessageListPlayersServer(Server.listPlayers));
					} catch (IOException e) {
						closeDataFlowWrite();
						closeConection();
						write = false;
						e.printStackTrace();
					}

					// ////////////////////////////////////////////////
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// ///////////////////////////////////////////////
				}
			}
		});

		writring.start();
	}
	
	/**
	 * escribe un mensaje constantemente
	 * 
	 * @param message objeto que se va a enviar
	 */
	public void alwaysWrite(MessageInterface message) {
		writring = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					outputStream = new ObjectOutputStream(
							conection.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

				while (write) {
					try {
						outputStream.writeObject(message);
					} catch (IOException e) {
						closeDataFlowWrite();
						closeConection();
						write = false;
						e.printStackTrace();
					}

					// ////////////////////////////////////////////////
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// ///////////////////////////////////////////////
				}
			}
		});

		writring.start();
	}
	
	
	/**
	 * Cierra el flujo de escritura
	 */
	private void closeDataFlowWrite() {
		try {
			write = false;
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cierra el flujo de lectura
	 */
	private void closeDataFlowRead() {
		try {
			read = false;
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cierra la conexion con el socket
	 */
	private void closeConection() {
		try {
			conection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
