package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class OperationsServer {

	private ObjectInputStream inputStream; // flujo de lectura de datos
	private ObjectOutputStream outputStream; // flujo de escritura de datos
	private Thread reading; // hilo de lectura
	private Thread writring; // hilo de escritura
	private Socket conection; // socket del cual obtendra el flujo de entrada y
								// salida
	private MessageInterface message; // mensaje para leer
	private boolean read; // controla cuando se inicia a leer y cuando se
							// detiene
	private boolean write; // controla cuando se inicia a escribir y cuando se
							// detiene

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

					inputStream = new ObjectInputStream(conection.getInputStream());
				} catch (IOException e) {
					System.out.println(e.getMessage());
					// e.printStackTrace();
				}

				while (read) {
					try {
						if (inputStream != null) {
							message = (MessageInterface) inputStream.readObject();

							if (message != null) {
								System.out.println("Mensaje del Cliente: " + message.getType());
								if (message.getType().equals("Inicio")) { //mensaje tipo Inicio
									Server.listPlayers.add(new Player(message.getMessage(), null));
									System.out.println(message.getMessage());
								} else if (message.getType().equals("Invitacion")) { //mensaje invitacion
									MessageInvitationClient invitation = (MessageInvitationClient) message;
									if (invitation.getIsAccept().isEmpty()) {
										MessageReplicateInvitationServer msn = new MessageReplicateInvitationServer(
												findPlayer(invitation.getPlayersVector()[0]));
										writeMessage(invitation.getPlayersVector()[1], msn);
									} else if (invitation.getIsAccept().equals("SI")) {
										MessageInvitationClient msn = new MessageInvitationClient(
												invitation.getPlayersVector()[0], invitation.getPlayersVector()[1]);
										msn.setIsAccept("SI");
										String ganador = raffleBegin(invitation);
										msn.setPlayerBegin(ganador);
										writeInitGame(msn, invitation.getPlayerInvited());
										msn.setIsAccept("BEGIN");
										writeInitGame(msn, invitation.getPlayerClient());
										Match match = new Match(invitation.getPlayerInvited(), invitation.getPlayerClient(), msn.getPlayerBegin());
										match.setClientLeft(Server.drivers.get(findConection(match.getPlayerLeft().getName())));
										match.setClientRigth(Server.drivers.get(findConection(match.getPlayerRigth().getName())));
										Server.listMatchs.add(match);
									} else {
										MessageInvitationClient msn = new MessageInvitationClient(
												invitation.getPlayersVector()[0], invitation.getPlayersVector()[1]);
										msn.setIsAccept("NO");
										writeMessage(invitation.getPlayersVector()[1], msn);
									}
								}
							}
						}
					} catch (ClassNotFoundException | IOException e) {

						// Tolerancia a Fallos va aqui
						System.out.println("Toleranacia a Fallos");
						closeDataFlowRead();
						closeConection();
						read = false;
						// e.printStackTrace();
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
	 * escribirle a un jugador que empiece el juego y envia el ganador
	 * @param msn
	 * @param ganador
	 * @param destino
	 */
	private void writeInitGame(MessageInvitationClient msn, String destino){
		writeMessage(destino, msn);
	}

	/**
	 * rifa quien es el que comienza, retornando el nombre del jugador
	 * @param invitation
	 * @return
	 */
	private String raffleBegin(MessageInvitationClient invitation){
		if(getRandom()>=0.5){
			return invitation.getPlayerClient();									
		}else{
			return invitation.getPlayerInvited();
		}
	}
	/**
	 * retorna numero aleaotiro entre 0 y 1
	 * @return
	 */
	private double getRandom(){
		return new Random().nextDouble();
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

	/**
	 * escribe un mensaje a una conexion especifica
	 * 
	 * @param p_player
	 *            jugador al que se le enviara el mensaje
	 * @param msn
	 *            mensaje que se desea enviar
	 */
	public void writeMessage(String p_player, MessageInterface msn) {
		Server.drivers.get(findConection(p_player)).write(msn);
	}

	/**
	 * sirve para retornar una jugador de la lista por su nombre
	 * 
	 * @return
	 */
	public Player findPlayer(String p_player) {
		Player aux = null;
		for (Player player : Server.listPlayers) {
			if (player.getName().equals(p_player)) {
				aux = player;
			}
		}
		return aux;
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
				outputStream = new ObjectOutputStream(conection.getOutputStream());
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
	 * @param message
	 *            objeto que se va a enviar
	 */
	public void alwaysWritePlayers() {
		writring = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					outputStream = new ObjectOutputStream(conection.getOutputStream());
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
	 * @param message
	 *            objeto que se va a enviar
	 */
	public void alwaysWrite(MessageInterface message) {
		writring = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					outputStream = new ObjectOutputStream(conection.getOutputStream());
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
