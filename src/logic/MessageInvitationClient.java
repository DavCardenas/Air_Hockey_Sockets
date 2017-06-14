package logic;

public class MessageInvitationClient extends MessageInterface{
	
	private String playerClient; // Nombre del Jugador quien hace la invitacion 
	private String playerInvited; // Nombre del Jugador al cual se va a invitar
	
	public MessageInvitationClient(String self, String invited) {
		super("Invitacion");
		playerClient = self;
		playerInvited = invited;
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
	
}
