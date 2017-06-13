package logic;

public class Singlenton {

	private static DataGameClient dataGame; // elemento que sera compartido para todos
	
	/**
	 * con este metodo se obtiene el objeto global
	 * de tipo mensaje
	 * @return message devulve el valor que tiene message.
	 */
	public static DataGameClient getDataGame(){
		if (dataGame == null) {
			dataGame = new DataGameClient();
		}
		return dataGame;
	}
	
}
