
package gui;
import javax.sound.sampled.*;
import java.io.*;
import sun.audio.*;

public class Sounds {

	private String ruta;
	private Clip clip;

	/**
	 * 
	 */
	public Sounds() {
		super();
		this.ruta = "../sounds/";
		this.clip = null;
	}
	
	public void sonido(String archivo){
		try{
			this.clip = AudioSystem.getClip();
			this.clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(this.ruta+archivo+".wav")));
			this.clip.loop(1);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Sounds sounds = new Sounds();
		sounds.sonido("tada");
		
	}
}