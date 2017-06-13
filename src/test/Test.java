package test;


import gui.WindowsLoggin;
import gui.WindowsServer;

public class Test {
	
	public static void main(String[] args) {
		
		if (args[0].equalsIgnoreCase("server")) {
			WindowsServer windowsS = new WindowsServer();
			windowsS.setVisible(true);
		}else if (args[0].equalsIgnoreCase("client")) {
			WindowsLoggin loggin = new WindowsLoggin();
			loggin.setVisible(true);
		}
		
	}

}
