package main;

import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.JFrame;

import util.InputListener;
import util.ResourceLoader;

public class Main {

	public final static String title = "Valley"; // Game Title.

	public static final int SCALE = 2;
	public final static int GAME_WIDTH = 640 * SCALE; // Default Width.
	public static final int GAME_HEIGHT = 360 * SCALE; // Default Height.

	public static ResourceLoader resourceLoader = new ResourceLoader();
	public static JFrame frame = new JFrame();
	public static Game game = new Game(frame, GAME_WIDTH, GAME_HEIGHT);

	private static InputListener inputListener;

	public static void main(String[] args) {

		inputListener = new InputListener();
		game.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		frame.add(game);
		frame.setTitle(title); // Set the title of JFrame.
		frame.setFocusable(true);
		frame.setResizable(false); // Stop the window getting resized.
		frame.addMouseListener(inputListener);
		frame.addKeyListener(inputListener);
		frame.addMouseMotionListener(inputListener);
		game.setFocusable(true);
		frame.pack(); // Correct the window size.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on JFrame close.
		frame.setLocationRelativeTo(null); // Make window appear in centre of screen.
		frame.setVisible(true); // Set the window to visible.
		frame.createBufferStrategy(3);
	}
}
