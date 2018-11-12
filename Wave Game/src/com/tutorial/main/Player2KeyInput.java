package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.tutorial.GameObjects.Player;

public class Player2KeyInput extends KeyAdapter {
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	private Player player;

	public Player2KeyInput(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
	}
	
	public void keyPressed (KeyEvent e) {
		int key = e.getKeyCode();
		
		// all the key events for player 1
		if (key == KeyEvent.VK_E) { player.setVolY(-5); keyDown[0] = true; player.setDirection(0);}
		if (key == KeyEvent.VK_D) { player.setVolY(5);  keyDown[1] = true; player.setDirection(1);}
		if (key == KeyEvent.VK_S) { player.setVolX(-5); keyDown[2] = true; player.setDirection(2);}
		if (key == KeyEvent.VK_F) { player.setVolX(5);  keyDown[3] = true; player.setDirection(3);}
		if(key == KeyEvent.VK_Q) { player.shoot(); }
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// all the key events for player 1
		if (key == KeyEvent.VK_E) keyDown[0] = false;//tempObject.setVolY(0);
		if (key == KeyEvent.VK_D) keyDown[1] = false;//tempObject.setVolY(0);
		if (key == KeyEvent.VK_S) keyDown[2] = false;//tempObject.setVolX(0);
		if (key == KeyEvent.VK_F) keyDown[3] = false;//tempObject.setVolX(0);
		
		// vertical movement
		if(!keyDown[0] && !keyDown[1]) player.setVolY(0);
		// horizontal movement
		if(!keyDown[2] && !keyDown[3]) player.setVolX(0);
		
	}
	
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
	}
}
