package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.tutorial.GameObjects.HUD;
import com.tutorial.GameObjects.HealObject;
import com.tutorial.GameObjects.Player;
import com.tutorial.GameObjects.Enemies.BasicEnemy;

public class TheGame extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5684306782075928892L;
	
	public static final int WIDTH = 960, HEIGHT = WIDTH/12 * 9;
	private Thread thread;	// entire game will run within this thread; start() will start up the thread
	private boolean isRunning = true;
	
	private Handler handler;
	private HUD hud;
	private Random rand;
	private Spawn spawner;

	public static ArrayList<Player> players;
	
	private boolean wokeUp;

	private static boolean isPaused = false;
	
	public TheGame() {
		System.out.println("Thread: " + Thread.currentThread().getId() + " Entered constructor public TheGame()");
		handler = new Handler(0,1);
		hud = new HUD(TheGame.WIDTH - 100, 15, 200, 32, handler);
		spawner = new Spawn(handler, hud);
		rand = new Random();
		//this.addKeyListener(new KeyInput(handler));
		
		new Window (WIDTH, HEIGHT, "Let's Build a Game", this);
		
		System.out.println("Thread: " + Thread.currentThread().getId() + " Just after creation of window in constructor public TheGame()");
		handler.addObject(new BasicEnemy(rand.nextInt(WIDTH - 16), rand.nextInt(HEIGHT - 36), 16, 16, 5, ID.BasicEnemy, handler, hud));
		handler.addObject(new HealObject(rand.nextInt(WIDTH - 16), rand.nextInt(HEIGHT - 36), 16, 16, ID.Heal, handler));
		
		System.out.println("Thread: " + Thread.currentThread().getId() + " Just after created healObject in constructor public TheGame()");
		Player player1 = new Player(rand.nextInt(WIDTH - 50), rand.nextInt(HEIGHT - 50), 32, 32, Color.WHITE, ID.Player, handler);
		handler.addObject(player1);
		Handler.players.add(player1);
		System.out.println("Handler.players.size() == " + Handler.players.size());
		this.addKeyListener(new Player1KeyInput(handler, player1));
		
	//	Player player2 = new Player(rand.nextInt(WIDTH - 50), rand.nextInt(HEIGHT - 50), 32, 32, Color.PINK, ID.Player, handler);
	//	handler.addObject(player2);
	//	Handler.players.add(player2);
	// 	this.addKeyListener(new Player2KeyInput(handler, player2));
	
		
		System.out.println("Thread: " + Thread.currentThread().getId() + " End of constructor public TheGame()");
		
		wakeUpGameThread();
		
		
	}
	

	private synchronized void wakeUpGameThread() {
		System.out.println("Entered wakeUpGameThread()");
			wokeUp = true;
			notify();
			System.out.println("Exiting wakeUpGameThread()");

	}


	@Override
	public void run() {
		System.out.println("Thread: " + Thread.currentThread().getId() + " Entered method public void run() in TheGame; isRunning == " + isRunning);
		waitForMainThread();
		this.requestFocus();
		final long second = 1000000000;
		long timeLast = System.nanoTime();
		long timeNow;
		long timer = System.currentTimeMillis();
		
		double amountOfTicks = 60.0;
		double periodTick = second / amountOfTicks;
		double deltaTick = 0;
		int frames = 0;
		
		System.out.println("Thread: " + Thread.currentThread().getId() + " Just before while(isRunning) in run() in TheGame; isRunning == " + isRunning);
		while (isRunning) {
			//System.out.println("Thread: " + Thread.currentThread().getId() + " Entered while(isRunning) in run() in TheGame; isRunning == " + isRunning);
			timeNow = System.nanoTime();
			// Tick
			deltaTick += (timeNow - timeLast) / periodTick;
			timeLast = timeNow;
			while (deltaTick >= 1) {
					tick();
					if (handler.getSizeOfObjectList() > 1000) {
					//	System.out.println("size of object list: " + handler.getSizeOfObjectList());
					//	System.out.println("deltaTick: " + deltaTick);
					}
					deltaTick--;
			}
				render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (handler.getSizeOfObjectList() > 800) {
					System.out.printf("\nsize of object list: %d; \t number of objects: %d\n", handler.getSizeOfObjectList(), handler.getSizeObject());
				}
			//	System.out.printf("size of object list: %d; \t number of objects: %d\n", handler.getSizeOfObjectList(), handler.getSizeObject());
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			
		}
		System.out.println("Thread: " + Thread.currentThread().getId() + " just before stop() in run() in TheGame.");
		stop();
		System.out.println("Thread: " + Thread.currentThread().getId() + " End of method public void run() in TheGame.");
	}

	private synchronized void waitForMainThread() {
		System.out.println("Entered waitForMainThread()");
		try {
			if (!wokeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Exiting waitForMainThread()");
	}


	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		hud.render(g);
		
		g.dispose();
		bs.show();
	}

	private void tick() {
		if (!isPaused) {
			handler.tick();
			hud.tick();
			spawner.tick();
		}
	}

	public synchronized void start() {
		System.out.println("Thread: " + Thread.currentThread().getId() + " Entered method public synchronized void start() in TheGame");
		thread = new Thread(this);		
		/*try {
				thread.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		thread.start();
		isRunning = true;
		System.out.println("Thread: " + Thread.currentThread().getId() + " End of method public synchronized void start() in TheGame");
	}
	public synchronized void stop() {
		System.out.println("Thread: " + Thread.currentThread().getId() + " Beginning of method public synchronized void stop() in TheGame");
		try {
			thread.join();
			isRunning = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Thread: " + Thread.currentThread().getId() + " End of method public synchronized void stop() in TheGame");
	}
	
	public static float clamp (float health, float min, float max) {
		if (health <= min) {
			health = min;
		}
		else if (health >= max) {
			health = max;
		}
		return health;
	}
	
	public static void switchPause () {
		isPaused = !isPaused;
		System.out.println("isPaused: " + isPaused);
	}
	
}
