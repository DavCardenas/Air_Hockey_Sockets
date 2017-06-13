package logic;

public class Singlenton {

	private static Message message; // elemento que sera compartido para todos
	
	/**
	 * con este metodo se obtiene el objeto global
	 * de tipo mensaje
	 * @return message devulve el valor que tiene message.
	 */
	public static Message getMessage(){
		if (message == null) {
			message = new Message();
		}
		return message;
	}
	
}
