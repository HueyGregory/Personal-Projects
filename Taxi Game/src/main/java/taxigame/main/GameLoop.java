package taxigame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import taxigame.entities.ObjectList;
import taxigame.entities.dynamic.vehicles.Player;
import taxigame.input.Keyboard;
import taxigame.render.Level;
import taxigame.render.PixelLevelDefinition;
import taxigame.render.Screen;
import taxigame.render.sprites.SpriteEntity;

public class GameLoop extends Canvas implements Runnable {
	private JFrame frame;
	private Screen screen;
	private boolean running;
	private Thread gameThread;
	private Keyboard keyboard;
	private Player player;
	private int width = 300, height = width * 9/16, scale = 3;		// pixel-precision
	
	public static PixelLevelDefinition[][] map;		// holds the entire map/level's tiles - tile-precision
	
	private String frameTitle = "Player";
	private static Level currentLevel;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public GameLoop () {
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);	
		setCurrentLevel(Level.level1);
		map = currentLevel.initializeMap(map);
		screen = new Screen(width, height);		// pixel-precision
		keyboard = new Keyboard();
		addKeyListener(keyboard);
	//	player = new Player(5, 5, keyboard, screen);
		ObjectList.players.add(player = new Player(5, 5, keyboard, SpriteEntity.Taxi, screen));
		
	}
	
	public void buildJFrame(GameLoop game) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(frameTitle);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void tick () {
		keyboard.tick();
		ObjectList.players.tick();
//		player.tick();
		ObjectList.dynamicEntities.tick();
	}
	
	private void render () {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			bufferStrategy = getBufferStrategy();
		}
		screen.clear();
		screen.renderTiles();
		screen.renderEntities();
		for(int x = 0; x < screen.getWidthPixel(); x++) {
			for (int y = 0; y < screen.getHeightPixel(); y++) {
				imagePixels[x + y * screen.getWidthPixel()] = screen.getPixels()[x][y];	// Pixel-precision
			}
		}
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, width*scale, height*scale);
		g.drawImage(image, 0, 0, width*scale, height*scale, null);
		g.dispose();
		bufferStrategy.show();
	}

	@Override
	public void run() {
		long timeNow = System.currentTimeMillis();
		int ticks = 0;
		int fps = 0;
		int second = 1000;
		double numberOfMillisecondsForOneTick = second/60;
		long numberOfMillisecondsForOneTickTimeNow = System.currentTimeMillis();
		while (running) {
			
			if (System.currentTimeMillis() - numberOfMillisecondsForOneTickTimeNow >= numberOfMillisecondsForOneTick) {
				tick();
				ticks++;
				numberOfMillisecondsForOneTickTimeNow += numberOfMillisecondsForOneTick;
			}
			fps++;
			render();
			if (System.currentTimeMillis() - timeNow >= second) {
				frame.setTitle(frameTitle + "| FPS: " + fps + "| Ticks: " + ticks);
				timeNow += second;
				fps = 0;
				ticks = 0;
			}
		}
	}
	
	public synchronized void start() {
		running = true;
		try {
			gameThread = new Thread(this, "GameThread");
			gameThread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Starting main");
		GameLoop game = new GameLoop();
		game.setFocusable(true);
		game.buildJFrame(game);
		game.start();
	}

	public static Level getCurrentLevel() {
		return GameLoop.currentLevel;
	}

	public static void setCurrentLevel(Level currentLevel) {
		GameLoop.currentLevel = currentLevel;
	}

}
