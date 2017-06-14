package logic;

public class MessageInvitationClient extends MessageInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3299642310666561319L;
	
	private String playerClient; // Nombre del Jugador quien hace la invitacion 
	private String playerInvited; // Nombre del Jugador al cual se va a invitarç
	private String isAccept; // verifica si la invitacion fue aceptada, inicia en vacio siempre y solo cambia a SI cuando aceptan la partida o a NO cuando la rechazan
	
	public MessageInvitationClient(String self, String invited) {
		super("Invitacion");
		playerClient = self;
		playerInvited = invited;
		isAccept = "";
	}
	
	/**
	 * devuelve el nombre de los dos jugadores siendo el primero
	 * el jugador quien invita y el segundo el jugador que sera invitado
	 */
	@Override
	public String getMessage() {
		return playerClient + "," + playerInvited;
	}

	public String getPlayerClient() {
		return playerClient;
	}

	public void setPlayerClient(String playerClient) {
		this.playerClient = playerClient;
	}

	public String getPlayerInvited() {
		return playerInvited;
	}

	public void setPlayerInvited(String playerInvited) {
		this.playerInvited = playerInvited;
	}
	
	public String getIsAccept() {
		return isAccept;
	}
	
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
}
