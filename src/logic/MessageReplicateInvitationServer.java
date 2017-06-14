package logic;

public class MessageReplicateInvitationServer extends MessageInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1298118139911460114L;

	private Player playerCounter; // jugador quien hizo la invitacion
	
	public MessageReplicateInvitationServer(Player player) {
		super("InvitacionServer");
		playerCounter = player;
	}
	
	@Override
	public Player getPlayer() {
		return playerCounter;
	}
	
	public Player getPlayerCounter() {
		return playerCounter;
	}
	
	public void setPlayerCounter(Player playerCounter) {
		this.playerCounter = playerCounter;
	}
}
