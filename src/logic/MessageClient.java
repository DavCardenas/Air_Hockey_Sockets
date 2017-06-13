package logic;

public class MessageClient extends MessageInterface{

	String namePlayer;
	
	public MessageClient(String name, String type) {
		super(type);
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
	
	@Override
	public String getType() {
		return super.getType();
	}
	
}
