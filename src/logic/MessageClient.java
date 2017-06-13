package logic;

public class MessageClient extends MessageInterface{

	String namePlayer;
	
	
	public MessageClient(String name) {
		namePlayer = name;
	}
	
	@Override
	public String getMessage() {
		return namePlayer;
	}
	
	public String getNamePlayer() {
		return namePlayer;
	}
	
	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}

}
