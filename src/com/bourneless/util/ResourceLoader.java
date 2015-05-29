package com.bourneless.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	public BufferedImage saveImage;

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

	public BufferedImage cycle;

	public boolean menuMusicPlaying;

	// Game Items

	public BufferedImage fireImage;
	public BufferedImage lake;
	public BufferedImage stockpile;

	// Item Buttons

	public BufferedImage lockedImage;
	public BufferedImage fireButtonImage;

	// Tile Role Buttons

	public BufferedImage buildButton;
	public BufferedImage pathButton;
	public BufferedImage emptyButton;
	public BufferedImage farmButton;

	// Player objects

	public BufferedImage[] maleHeads = new BufferedImage[7];
	public BufferedImage[] maleBodies = new BufferedImage[5];
	public BufferedImage[] femaleHeads = new BufferedImage[7];
	public BufferedImage[] femaleBodies = new BufferedImage[5];
	
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
	public Clip[] femaleVoices = new Clip[4];

	// Role objects
	public BufferedImage wateringcan;
	public BufferedImage pickaxe;
	public BufferedImage hatchet;

	// Mechanics
	public BufferedImage lightsource;
	
	// Animation Images
	public BufferedImage[] walkingImages = new BufferedImage[3];
	public BufferedImage[] flameImages = new BufferedImage[3];
	public BufferedImage[] lakeImages = new BufferedImage[2];
	
	// Text
	public ArrayList<String> maleNames = new ArrayList<String>();
	public ArrayList<String> femaleNames = new ArrayList<String>();

	public ResourceLoader() {
		splashImage = getBufferedImage("res/splash.png");

		menuTitle = getBufferedImage("res/menuTitle.png");
		menuBack = getBufferedImage("res/menuBack.png");
		menuBackHighGrowth = getBufferedImage("res/menuBackHighGrowth.png");
		menuMusic = loadClip("/menuSong.wav");
		newGame = getBufferedImage("res/newGame.png");
		loadGame = getBufferedImage("res/loadGame.png");
		saveImage = getBufferedImage("res/saveImage.png");
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
		femaleVoices[0] = loadClip("/voices/fem1.wav");
		femaleVoices[1] = loadClip("/voices/fem2.wav");
		femaleVoices[2] = loadClip("/voices/fem3.wav");
		femaleVoices[3] = loadClip("/voices/fem4.wav");

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
		maleHeads[0] = getBufferedImage("res/playerobjects/head1.png");
		maleHeads[1] = getBufferedImage("res/playerobjects/head2.png");
		maleHeads[2] = getBufferedImage("res/playerobjects/head3.png");
		maleHeads[3] = getBufferedImage("res/playerobjects/head4.png");
		maleHeads[4] = getBufferedImage("res/playerobjects/head5.png");
		maleHeads[5] = getBufferedImage("res/playerobjects/head6.png");
		maleHeads[6] = getBufferedImage("res/playerobjects/head7.png");
		maleBodies[0] = getBufferedImage("res/playerobjects/body1.png");
		maleBodies[1] = getBufferedImage("res/playerobjects/body2.png");
		maleBodies[2] = getBufferedImage("res/playerobjects/body3.png");
		maleBodies[3] = getBufferedImage("res/playerobjects/body4.png");
		maleBodies[4] = getBufferedImage("res/playerobjects/body5.png");
		
		femaleHeads[0] = getBufferedImage("res/playerobjects/fHead1.png");
		femaleHeads[1] = getBufferedImage("res/playerobjects/fHead2.png");
		femaleHeads[2] = getBufferedImage("res/playerobjects/fHead3.png");
		femaleHeads[3] = getBufferedImage("res/playerobjects/fHead4.png");
		femaleHeads[4] = getBufferedImage("res/playerobjects/fHead5.png");
		femaleHeads[5] = getBufferedImage("res/playerobjects/fHead6.png");
		femaleHeads[6] = getBufferedImage("res/playerobjects/fHead7.png");
		
		femaleBodies[0] = getBufferedImage("res/playerobjects/fBody1.png");
		femaleBodies[1] = getBufferedImage("res/playerobjects/fBody2.png");
		femaleBodies[2] = getBufferedImage("res/playerobjects/fBody3.png");
		femaleBodies[3] = getBufferedImage("res/playerobjects/fBody4.png");
		femaleBodies[4] = getBufferedImage("res/playerobjects/fBody5.png");

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
		hatchet = getBufferedImage("res/skillobjects/hatchet.png");

		rock = getBufferedImage("res/gameobjects/rock.png");
		rockMined = getBufferedImage("res/gameobjects/rockMined.png");
		stockpile = getBufferedImage("res/gameobjects/stockpile.png");

		cycle = getBufferedImage("res/night.png");
		lightsource = getBufferedImage("res/mechanics/lightsource.png");
		
		walkingImages[0] = getBufferedImage("res/animations/walking/1.png");
		walkingImages[1] = getBufferedImage("res/animations/walking/2.png");
		walkingImages[2] = getBufferedImage("res/animations/walking/3.png");
		
		flameImages[0] = getBufferedImage("res/animations/flame/1.png");
		flameImages[1] = getBufferedImage("res/animations/flame/2.png");
		flameImages[2] = getBufferedImage("res/animations/flame/3.png");
		
		lakeImages[0] = getBufferedImage("res/animations/lake/1.png");
		lakeImages[1] = getBufferedImage("res/animations/lake/2.png");

		Scanner s = null;
		
		try {
			s = new Scanner(new File("res/text/maleNames.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (s.hasNextLine()) {
			maleNames.add(s.nextLine());
		}
		s.close();
		
		try {
			s = new Scanner(new File("res/text/femaleNames.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(s.hasNextLine()) {
			femaleNames.add(s.nextLine());
		}
		s.close();
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
