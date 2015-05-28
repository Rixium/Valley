package com.bourneless.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bourneless.screen.Screen;
import com.bourneless.screen.SplashScreen;

public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private boolean running = false;

	private JFrame frame;

	private Thread thread;
	private int frames = 0;
	private int updates = 0;
	public static int width;
	public static int height;

	private BufferedImage image;

	private Screen currentScreen;

	private float durationMS = 0;

	@SuppressWarnings("static-access")
	public Game(JFrame frame, int width, int height) {
		this.setDoubleBuffered(true);
		this.width = width;
		this.height = height;
		this.frame = frame;
		
		initialize();
	}

	public void initialize() {
		System.out.println("Initializing..");

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		currentScreen = new SplashScreen();

		running = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void update() {
		currentScreen.update();
	}

	public void blit() {
		float current = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) image.createGraphics(); // Create graphics of g2d.
		currentScreen.paint(g2d);
		g2d.dispose();
	}

	public void draw() {
		BufferStrategy bs = frame.getBufferStrategy();
		if (bs == null && frame.isVisible()) {
			frame.createBufferStrategy(3);
			return;
		}
		if (bs != null) {
			Graphics g = bs.getDrawGraphics();
			blit();
			g.drawImage(image, 3, 26, null);
			g.dispose();
			bs.show();
			super.paintComponent(g);
			g.drawImage(image, 3, 26, null);
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000 / 60D;
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {

				update();

				updates++;
				delta--;
			}
			draw();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(Main.title + " | FPS: " + frames + " | UPS: "
						+ updates);
				updates = 0;
				frames = 0;
			}


		}

		System.exit(0);
	}

	public Screen getScreen() {
		return this.currentScreen;
	}

	public void setScreen(Screen screen) {
		this.currentScreen = screen;
	}

}
