package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class ResourceLoader {

	// Splash Assets
	public BufferedImage splashImage;

	// Menu Assets
	public BufferedImage menuTitle;
	public BufferedImage menuBack;
	public BufferedImage menuBackHighGrowth;
	public BufferedImage newGame;
	public BufferedImage loadGame;
	public BufferedImage options;
	public BufferedImage quit;
	public BufferedImage toggleSound;
	public BufferedImage toggleSoundOff;

	public BufferedImage small;
	public BufferedImage medium;
	public BufferedImage large;
	public BufferedImage low;
	public BufferedImage high;
	public BufferedImage createmap;

	public BufferedImage mapcreation;
	public BufferedImage mapsize;
	public BufferedImage treegrowth;

	public BufferedImage loadImage;

	public Clip menuMusic;
	public Clip menuClick;

	// Tile Assets

	public BufferedImage grass;
	public BufferedImage water;

	// Game Assets

	public BufferedImage uiBackground;
	public BufferedImage personStatScreen;
	public Clip openUI;
	public BufferedImage person;

	public BufferedImage treeTop;
	public BufferedImage treeBottom;
	public BufferedImage treeBottomCut;
	public BufferedImage tree;
	public BufferedImage sapling;
	public BufferedImage rock;
	public BufferedImage rockMined;
	
	public BufferedImage cow;

	public boolean menuMusicPlaying;

	// Game Items

	public BufferedImage fireImage;
	public BufferedImage lake;

	// Item Buttons

	public BufferedImage lockedImage;
	public BufferedImage fireButtonImage;

	// Tile Role Buttons

	public BufferedImage buildButton;
	public BufferedImage pathButton;
	public BufferedImage emptyButton;
	public BufferedImage farmButton;

	// Player objects

	public BufferedImage[] heads = new BufferedImage[7];
	public BufferedImage head1, head2, head3;
	public BufferedImage[] bodies = new BufferedImage[5];
	public BufferedImage swimming;

	// Stat icons

	public BufferedImage cooking;
	public BufferedImage farming;
	public BufferedImage fishing;
	public BufferedImage health;
	public BufferedImage mining;
	public BufferedImage strength;
	public BufferedImage woodcutting;
	
	// Voices
	
	public Clip[] voices = new Clip[4];
	
	// Role objects
	public BufferedImage wateringcan;
	public BufferedImage pickaxe;

	public ResourceLoader() {
		splashImage = getBufferedImage("res/splash.png");

		menuTitle = getBufferedImage("res/menuTitle.png");
		menuBack = getBufferedImage("res/menuBack.png");
		menuBackHighGrowth = getBufferedImage("res/menuBackHighGrowth.png");
		menuMusic = loadClip("/menuSong.wav");
		newGame = getBufferedImage("res/newGame.png");
		loadGame = getBufferedImage("res/loadGame.png");
		quit = getBufferedImage("res/quit.png");
		options = getBufferedImage("res/options.png");

		grass = getBufferedImage("res/tiles/grassTile.png");
		cow = getBufferedImage("res/gameobjects/cow.png");

		uiBackground = getBufferedImage("res/uiBackground.png");
		personStatScreen = getBufferedImage("res/personStatScreen.png");
		openUI = loadClip("/openUI.wav");
		
		voices[0] = loadClip("/voices/man1.wav");
		voices[1] = loadClip("/voices/man2.wav");
		voices[2] = loadClip("/voices/man3.wav");
		voices[3] = loadClip("/voices/man4.wav");
		
		tree = getBufferedImage("res/gameobjects/tree.png");
		treeBottomCut = getBufferedImage("res/gameobjects/treebottomcut.png");

		toggleSound = getBufferedImage("res/toggleSoundImage.png");
		toggleSoundOff = getBufferedImage("res/toggleSoundOffImage.png");

		fireImage = getBufferedImage("res/gameobjects/fire.png");
		fireButtonImage = getBufferedImage("res/buttons/fireButton.png");

		small = getBufferedImage("res/buttons/small.png");
		medium = getBufferedImage("res/buttons/medium.png");
		large = getBufferedImage("res/buttons/large.png");

		lockedImage = getBufferedImage("res/buttons/locked.png");

		low = getBufferedImage("res/buttons/low.png");
		high = getBufferedImage("res/buttons/high.png");
		createmap = getBufferedImage("res/buttons/createmap.png");

		menuClick = loadClip("/menuClick.wav");

		mapsize = getBufferedImage("res/mapsizeimage.png");
		treegrowth = getBufferedImage("res/treegrowthimage.png");
		mapcreation = getBufferedImage("res/mapCreationImage.png");

		person = getBufferedImage("res/person.png");
		heads[0] = getBufferedImage("res/playerobjects/head1.png");
		heads[1] = getBufferedImage("res/playerobjects/head2.png");
		heads[2] = getBufferedImage("res/playerobjects/head3.png");
		heads[3] = getBufferedImage("res/playerobjects/head4.png");
		heads[4] = getBufferedImage("res/playerobjects/head5.png");
		heads[5] = getBufferedImage("res/playerobjects/head6.png");
		heads[6] = getBufferedImage("res/playerobjects/head7.png");
		bodies[0] = getBufferedImage("res/playerobjects/body1.png");
		bodies[1] = getBufferedImage("res/playerobjects/body2.png");
		bodies[2] = getBufferedImage("res/playerobjects/body3.png");
		bodies[3] = getBufferedImage("res/playerobjects/body4.png");
		bodies[4] = getBufferedImage("res/playerobjects/body5.png");

		cooking = getBufferedImage("res/statIcons/cooking.png");
		farming = getBufferedImage("res/statIcons/farming.png");
		fishing = getBufferedImage("res/statIcons/fishing.png");
		health = getBufferedImage("res/statIcons/health.png");
		mining = getBufferedImage("res/statIcons/mining.png");
		strength = getBufferedImage("res/statIcons/strength.png");
		woodcutting = getBufferedImage("res/statIcons/woodcutting.png");

		lake = getBufferedImage("res/gameobjects/lake.png");
		swimming = getBufferedImage("res/swimming.png");
		sapling = getBufferedImage("res/gameobjects/sapling.png");
		treeTop = getBufferedImage("res/gameobjects/treetop.png");
		treeBottom = getBufferedImage("res/gameobjects/treebottom.png");

		farmButton = getBufferedImage("res/buttons/farm.png");
		pathButton = getBufferedImage("res/buttons/path.png");
		emptyButton = getBufferedImage("res/buttons/empty.png");
		buildButton = getBufferedImage("res/buttons/build.png");
		loadImage = getBufferedImage("res/loadImage.png");
		
		wateringcan = getBufferedImage("res/skillobjects/wateringcan.png");
		pickaxe = getBufferedImage("res/skillobjects/pickaxe.png");
		
		rock = getBufferedImage("res/gameobjects/rock.png");
		rockMined = getBufferedImage("res/gameobjects/rockMined.png");
	}

	public Clip loadClip(String filename) {
		Clip clip = null;

		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clip;
	}

	@SuppressWarnings("static-access")
	public void playClip(Clip clip, float volume, boolean loop) {

		if (clip == menuMusic) {
			menuMusicPlaying = !menuMusicPlaying;
		}

		if (clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
		FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);
		if (loop) {
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		clip.start();
	}

	public void stopClip(Clip clip) {
		if (clip == menuMusic) {
			menuMusicPlaying = !menuMusicPlaying;
		}
		if (clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
	}

	public static Image getImage(String url) {

		try {
			return ImageIO.read(new File(url));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static BufferedImage getBufferedImage(String url) {

		try {
			return ImageIO.read(new File(url));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
