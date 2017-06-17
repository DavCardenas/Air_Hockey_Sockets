package logic;

public class MessageMatchClient extends MessageInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Player player;
	
	public MessageMatchClient() {
		super("Match_Client");
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
}
