package com.caravan.play;

import com.caravan.entities.Entity;
import com.caravan.windows.Screen;
import com.caravan.windows.State;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameLoop extends Canvas implements Runnable {

    private boolean running, paused;
    private Thread gameThread;
    private JFrame frame;
    private final String frameTitle = "Player";
    private int widthPixel = Screen.getWidthPixel(), heightPixel = Screen.getHeightPixel(), scale = Screen.getScale();
    private static State currentState = State.TRAVELING;

    private KeyboardInput keyboardInput;
    private MouseInput mouseInput;

    public void startGame() {
        Render.INSTANCE.setScreen(new Screen());
        Dimension size = new Dimension(widthPixel *scale, heightPixel *scale);
        setPreferredSize(size);

        keyboardInput = new KeyboardInput();
        addKeyListener(keyboardInput);
        mouseInput = new MouseInput();
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    private void runGame() {

    }

    @Override
    public void run() {
        int ticks = 0;
        int fps = 0;
        int second = 1000;
        double numberOfMillisecondsForOneTick = second / 60;
        List<Entity> oldEntites = null;
        Graphics2D graphics2D = initializeGraphics();

        long timeNow = System.currentTimeMillis();
        long numberOfMillisecondsForOneTickTimeNow = System.currentTimeMillis();
        while(running) {
            boolean ticked = false;
            if (!paused) {

                if (System.currentTimeMillis() - numberOfMillisecondsForOneTickTimeNow >= numberOfMillisecondsForOneTick) {
                    Tick.INSTANCE.tick();
                    ticked = true;
                    ticks++;
                    numberOfMillisecondsForOneTickTimeNow += numberOfMillisecondsForOneTick;
                }
            }
            fps++;
            if (!ticked) {
                Render.INSTANCE.render(null, graphics2D);
            } else {
                oldEntites = Render.INSTANCE.render(oldEntites, graphics2D);
            }
            if (System.currentTimeMillis() - timeNow >= second) {
                frame.setTitle(frameTitle + "| FPS: " + fps + "| Ticks: " + ticks);
                timeNow += second;
                fps = 0;
                ticks = 0;
            }
        }
    }

    private Graphics2D initializeGraphics() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy();
        }
        Render.INSTANCE.setBufferStrategy(bufferStrategy);
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, widthPixel * scale, heightPixel *scale);
        return g;
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

    public static State getCurrentState() {
        return currentState;
    }

    public synchronized void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        System.out.println("Starting main");
        GameLoop game = new GameLoop();
        game.startGame();
        game.setFocusable(true);
        game.buildJFrame(game);
        game.start();
    }
}
