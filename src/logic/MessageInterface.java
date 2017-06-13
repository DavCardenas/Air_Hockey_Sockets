package logic;

public abstract class MessageInterface {
	
	String type; // define que tipo de mensaje es
	
	/**
	 * contructor que permite iniciar que tipo de mensaje es
	 * @param type
	 */
	public MessageInterface(String type) {
		this.type = type;
	}
	
	/**
	 * retorna el mensaje
	 * @return
	 */
	public String getMessage() {
		return null;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
