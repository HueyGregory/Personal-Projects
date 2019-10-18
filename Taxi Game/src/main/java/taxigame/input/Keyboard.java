package taxigame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120];	// every key on the keyboard will have one of two states: pressed (true) and released (false)
	private boolean up, down, left, right;		// these states will keep track if pressed key which corresponds to "up" movement or "down" movement, etc.
	
	public void tick() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
	}

	@Override
	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(KeyEvent key) {
		if(key.getKeyChar() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
		
	public boolean getUp() {
		return up;
	}
	public boolean getDown() {
		return down;
	}
	public boolean getLeft() {
		return left;
	}
	public boolean getRight() {
		return right;
	}
	
	
	
}

