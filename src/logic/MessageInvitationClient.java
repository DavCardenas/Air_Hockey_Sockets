package logic;

public class MessageInvitationClient extends MessageInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3299642310666561319L;
	
	private String playerClient; // Nombre del Jugador quien hace la invitacion 
	private String playerInvited; // Nombre del Jugador al cual se va a invitar
	private String isAccept; // verifica si la invitacion fue aceptada, inicia en vacio siempre y solo cambia a SI cuando aceptan la partida o a NO cuando la rechazan
	private String playerBegin; //Nombre del jugador que gano el saque
	
	public MessageInvitationClient(String self, String invited) {
		super("Invitacion");
		playerClient = self;
		playerInvited = invited;
		isAccept = "";
		playerBegin = "";
	}
	
	@Override
	public String[] getPlayersVector() {
		String[] aux = new String[2];
		aux[0] = playerClient;
		aux[1] = playerInvited;
		return aux;
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

	/**
	 * @return the playerBegin
	 */
	public String getPlayerBegin() {
		return playerBegin;
	}

	/**
	 * @param playerBegin the playerBegin to set
	 */
	public void setPlayerBegin(String playerBegin) {
		this.playerBegin = playerBegin;
	}
	
	
}
