package logic;

/**
 * 
 * Esta Clase se Utiliza exclusivamente para enviar une mensaje inicial
 * cuando el cliente se conecta al servidor
 *
 */
public class MessageLoginClient extends MessageInterface{

	String namePlayer; // nombre del jugador a enviar hacia el servidor
	
	public MessageLoginClient(String name) {
		super("Inicio");
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
