package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Main;

public class InputListener implements KeyListener, MouseMotionListener, MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Main.game.getScreen().mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Main.game.getScreen().mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Main.game.getScreen().keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Main.game.getScreen().keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
