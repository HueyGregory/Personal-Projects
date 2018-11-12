package com.tutorial.main;

import java.util.Random;

import com.tutorial.GameObjects.GameObject;
import com.tutorial.GameObjects.HUD;
import com.tutorial.GameObjects.Enemies.BasicEnemy;
import com.tutorial.GameObjects.Enemies.SmartEnemy;

public class Spawn {

	private Handler handler;
	private HUD hud;
	private Random rand = new Random();
	
	public Spawn (Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick() {
		if((hud.getScore() + 1) % 100 == 0) {
			hud.setLevel(hud.getLevel() + 1);
			int maxSpeed = (int) (TheGame.clamp(hud.getLevel(), 1, 15));
			handler.addObject(new BasicEnemy((float) rand.nextInt(TheGame.WIDTH - 16), (float) rand.nextInt(TheGame.HEIGHT - 36), 16, 16, maxSpeed, ID.BasicEnemy, handler, hud));
			for (int i = 0; i < handler.getSizeOfObjectList(); i++) {
				GameObject tempObject = handler.getObject(i);
				if (tempObject == null) {
					continue;
				}
				if (tempObject.getId() == ID.BasicEnemy) {
					BasicEnemy enemyObject = (BasicEnemy) tempObject;
					enemyObject.setLifetime(enemyObject.getLifetime() - 1);
				}
			}
		//	handler.addObject(new HealObject(rand.nextInt(TheGame.WIDTH - 16), rand.nextInt(TheGame.HEIGHT - 36), 16, 16, ID.Heal, handler));
		//	if ((hud.getScore() + 1) % 1000 == 0) {
				handler.addObject(new SmartEnemy(rand.nextInt(TheGame.WIDTH - 16), rand.nextInt(TheGame.HEIGHT - 36), 16, 16, maxSpeed, ID.SmartEnemy, handler, hud));
		//	}
			if (rand.nextInt(3) == 1) {
				handler.addObject(new BasicEnemy((float) rand.nextInt(TheGame.WIDTH - 16), (float) rand.nextInt(TheGame.HEIGHT - 36), 16, 16, rand.nextInt(10) + 5, ID.BasicEnemy, handler, hud));
				handler.addObject(new SmartEnemy(rand.nextInt(TheGame.WIDTH - 16), rand.nextInt(TheGame.HEIGHT - 36), 16, 16, rand.nextInt(10) + 10, ID.SmartEnemy, handler, hud));
			}
			if (hud.getLevel() % 10 == 0) {
				for (int i = 0; i < hud.getLevel() / 10; i++) {
					handler.addObject(new BasicEnemy((float) rand.nextInt(TheGame.WIDTH - 16), (float) rand.nextInt(TheGame.HEIGHT - 36), 16, 16, rand.nextInt(10) + (int) (TheGame.clamp(hud.getLevel(), 1, 10 + hud.getLevel() / 100)), ID.BasicEnemy, handler, hud));
					handler.addObject(new SmartEnemy(rand.nextInt(TheGame.WIDTH - 16), rand.nextInt(TheGame.HEIGHT - 36), 16, 16, rand.nextInt(10) + (int) (TheGame.clamp(hud.getLevel(), 1, 10 + hud.getLevel() / 100)), ID.SmartEnemy, handler, hud));
				}
			}
		}
		
	}
}
