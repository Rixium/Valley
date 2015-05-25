package screen;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.Main;

public class SplashScreen extends Screen implements ActionListener {

	private boolean fadingIn;
	private boolean fadingOut;
	
	Timer splashTimer = new Timer(4000, this);
	Timer fadeTimer = new Timer(10, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(splashAlpha < 0.99f && fadingIn) {
				splashAlpha += 0.01f;
			} else if (splashAlpha >= 0.9f) {
				fadingIn = false;
				fadingOut = true;
			}
			
			if(splashAlpha > 0.01 && fadingOut) {
				splashAlpha -= 0.01f;
			} else if(fadingOut && splashAlpha <= 0) {
				splashAlpha = 0;
				fadeTimer.stop();
			}
		}
	});
	
	private float splashAlpha = 0f;
	
	public SplashScreen() {
		fadingIn = true;
		fadeTimer.start();
		splashTimer.start();
	}
	
	public void update() {

	}
	
	public void paint(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, splashAlpha));
		g.drawImage(Main.resourceLoader.splashImage,
				Main.GAME_WIDTH / 2 - Main.resourceLoader.splashImage.getWidth() / 2,
				Main.GAME_HEIGHT / 2 - Main.resourceLoader.splashImage.getHeight() / 2, null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		splashTimer.stop();
		fadeTimer.stop();
		Main.game.setScreen(new MenuScreen());
	}
}
