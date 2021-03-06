package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OperationsClient {

	private ObjectInputStream inputStream; // flujo de lectura de datos
	private ObjectOutputStream outputStream; // flujo de escritura de datos
	private Thread reading; // hilo de lectura
	private Thread writring; // hilo de escritura
	private Socket conection; // socket del cual obtendra el flujo de entrada y salida
	private MessageInterface messageRead; // mensaje para leer
	private boolean read; // controla cuando se inicia a leer y cuando se detiene
	private boolean write; // controla cuando se inicia a escribir y cuando se detiene
	private DataGameClient dataGame; // elemento global que contiene la informacion del juego	

	/**
	 * 
	 * @param socket
	 *            sirve para alamacenar el socket
	 */
	public OperationsClient(Socket socket, Player self) {
		conection = socket;
		read = true;
		write = true;
		dataGame = Singlenton.getDataGame();
		dataGame.setSelf(self);
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
							messageRead = (MessageInterface) inputStream.readObject();							
							if (messageRead != null) {	
								if (messageRead.getType().equals("Lista_Jugadores")) {
									System.out.println("Mensaje del Servidor: " + messageRead.getPlayerList());
									dataGame.setPlayerList(messageRead.getPlayerList());
								}else if (messageRead.getType().equals("InvitacionServer")) {
									MessageReplicateInvitationServer msn = (MessageReplicateInvitationServer) messageRead;
									dataGame.updateListInvitations(msn.getPlayer());
								}else if (messageRead.getType().equals("Invitacion")) {
									MessageInvitationClient msn = (MessageInvitationClient) messageRead;
									if (msn.getIsAccept().equals("SI")) {
										dataGame.setInvitationClient("SI");
										dataGame.setGame(true);
										if(msn.getPlayerBegin().equals(msn.getPlayerInvited())){
											dataGame.setBegin(true);
									}					
									}else if(msn.getIsAccept().equals("NO")){
										dataGame.setInvitationClient("NO");
									}else if(msn.getIsAccept().equals("BEGIN")){
										dataGame.setGame(true);
										if(msn.getPlayerBegin().equals(msn.getPlayerClient())){
											dataGame.setBegin(true);
										}
									} else {										
										dataGame.setInvitationClient("");
									}
								} else if(messageRead.getType().equals("Match")){
									MessageMatch match= (MessageMatch) messageRead;
									if(dataGame.isBegin()){
										dataGame.setSelf(Player.changeDir(match.getPlayerLeft()));
										dataGame.setCounter(Player.changeDir(match.getPlayerRigth()));
									} else {
										dataGame.setSelf(Player.changeDir(match.getPlayerRigth()));
										dataGame.setCounter(Player.changeDir(match.getPlayerLeft()));
									}
									dataGame.setDisc(match.getDisk());
									dataGame.setTimeGame(match.getTimeLeft());
									dataGame.setGame(match.isGame());
									dataGame.setLevel(match.getLevelGame());
								}
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
			System.out.println("Se escribe mensaje tipo:"+message.getType());
			if(message.getType().equals("Inicio")){
				dataGame.setSelf(new Player(message.getMessage(), null));
			}
//			if (message.getType().equals("Match_Client")) {
//				System.out.println("Mesaje: "+	(MessageMatchClient) message);
//			}
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
	 * sirve para mantener enviando la posicion del jugador hacia el servidor 
	 * utilizando lo que esta almacenado en el singlenton
	 */
	public void writePosition() {
		write = true;
		writring = new Thread(new Runnable() {
		
			@Override
			public void run() {
				try {
					if (outputStream == null) {
						outputStream = new ObjectOutputStream(
								conection.getOutputStream());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				while (write) {
					try {
						MessageMatchClient msn = new MessageMatchClient(); // crea el mensaje a enviar
						Player aux = Player.changeDir(dataGame.getSelf());
						msn.setPlayer(aux.getPosition()); // asigna el contenido al mensaje
						msn.setName(aux.getName());
						//System.out.println("Se escribe mensaje tipo siempre: "+ msn.getType());
						outputStream.writeObject(msn);
					} catch (IOException e) {
						write = false;
						closeConection();
						System.out.println("Ya no puede escribir");
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
