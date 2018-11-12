package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.tutorial.GameObjects.Player;

public class Player1KeyInput extends KeyAdapter {
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	private Player player;

	public Player1KeyInput(Handler handler, Player player) {
		this.handler = handler;	
		this.player = player;
					
	}
	
	public void keyPressed (KeyEvent e) {
		int key = e.getKeyCode();
		
		// all the key events for player 2
		if (key == KeyEvent.VK_UP) { player.setVolY(-5); keyDown[0] = true; player.setDirection(0);}
		if (key == KeyEvent.VK_DOWN) { player.setVolY(5); keyDown[1] = true; player.setDirection(1);}
		if (key == KeyEvent.VK_LEFT) { player.setVolX(-5); keyDown[2] = true; player.setDirection(2);}
		if (key == KeyEvent.VK_RIGHT) { player.setVolX(5); keyDown[3] = true; player.setDirection(3);}
		if(key == KeyEvent.VK_SPACE) { player.shoot(); }
				
		if (key == KeyEvent.VK_P) {
			TheGame.switchPause();
		}
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// all the key events for player 2
		if (key == KeyEvent.VK_UP) keyDown[0] = false;//tempObject.setVolY(0);
		if (key == KeyEvent.VK_DOWN) keyDown[1] = false;//tempObject.setVolY(0);
		if (key == KeyEvent.VK_LEFT) keyDown[2] = false;//tempObject.setVolX(0);
		if (key == KeyEvent.VK_RIGHT) keyDown[3] = false;//tempObject.setVolX(0);
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