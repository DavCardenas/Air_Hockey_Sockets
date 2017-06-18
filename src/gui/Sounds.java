
package gui;
import javax.sound.sampled.*;
import java.io.*;

public class Sounds {

	public static final String RUTA_SOUND_BACKGROUND = "/sounds/fondo.wav";
	public static final String RUTA_SOUND_LEVEL = "/sounds/cambio_nivel.wav";
	public static final String RUTA_SOUND_BALL = "/sounds/golpe_pelota.wav";
	public static final String RUTA_SOUND_POINT = "/sounds/gana_punto.wav";
	private Clip clip;

	/**
	 * 
	 */
	public Sounds() {
		super();
		this.clip = null;
	}
	
	/**
	 * reproduce un sonido a partir de la ruta
	 * @param ruta
	 * @param loop
	 */
	public void play(String ruta, boolean loop) {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(ruta)));
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clip.start();
		if (loop) {
			clip.loop(Clip.LOOP_CONTINUOUSLY  );
		}
	}
	
	public static void main(String[] args) {
		Sounds sounds = new Sounds();
		sounds.play("/sounds/cambio_nivel.wav", false);
		while (true) {
			System.out.println("reproduciendo musica");
		}
	}
}