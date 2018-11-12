package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import com.tutorial.GameObjects.GameObject;
import com.tutorial.GameObjects.Player;

// maintain and render all the object in the room. 
public class Handler extends RecursiveAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5767380893768168969L;
	protected static GameObject[] object = new GameObject[50];
	protected static ArrayList<Player> players = new ArrayList<Player>();
	private static ConcurrentLinkedDeque<Integer> freeSlots = fillFreeSlots();
	public static int[][] map = new int[TheGame.HEIGHT][TheGame.WIDTH];
	//private ForkJoinPool pool;
	protected int start, end;
	protected static int sizeOfObject;

	public static ConcurrentLinkedDeque<Integer> fillFreeSlots () {
		ConcurrentLinkedDeque<Integer> tempFreeSlots = new ConcurrentLinkedDeque<Integer>();
		for (int i = object.length - 1; i > sizeOfObject; i--) {
			tempFreeSlots.addFirst(i);
		}
		return tempFreeSlots;
	}
	
	public Handler(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public void tick() {		
		ForkJoinTask.invokeAll(new HandlerTick(0, object.length));
	}

	public void render (Graphics g) {
		ForkJoinTask.invokeAll(new HandlerRender(0, object.length, g));
	//	System.out.println("Just after new HandlerRender() in Handler class; playerRemaining == " + playerRemaining);
		if (players.size() < 1) {
			gameOver(g);
		}
	}
	
	public static GameObject getObject (int i) {
		return object[i];
	}
	
	public void addObject (GameObject object) {
	//	System.out.printf("\nBeginning of addObject; thread: %d, size of freeSlots == %d", Thread.currentThread().getId(), freeSlots.size());
		sizeOfObject++;
		int index = maybeResizeObject(true);
		this.object[index] = object;
		object.setIndex(index);
	//	System.out.printf("End of addObject; thread: %d, size of freeSlots == %d\n", Thread.currentThread().getId(), freeSlots.size());

	}
	
	private synchronized int maybeResizeObject (boolean bigger) {
	//	System.out.println("\nbeginning of maybeResizeObject(" + bigger + ");  Thread #: " + Thread.currentThread().getId() + " size of freeSlots == " + freeSlots.size() + " size of object == " + object.length + "number of objects == " + sizeOfObject);
		if (freeSlots.isEmpty()) {
			GameObject[] resized = Arrays.copyOf(object, 2*object.length);
			object = resized;
			freeSlots = fillFreeSlots();
		}
	//	System.out.println("returning freeSlots.removeFirst; Thread #: " + Thread.currentThread().getId() + " size of freeSlots == " + freeSlots.size() + " size of object == " + object.length + "number of objects == " + sizeOfObject);			
		return freeSlots.removeFirst();
		
	}

	public void removeObject (GameObject object, int i) {
	//	System.out.printf("\nbeginning of removeObject(); thread: %d, size of freeSlots == %d, size of object == %d, number of objects == %d\n", Thread.currentThread().getId(), freeSlots.size(), this.object.length, sizeOfObject);
	//	maybeDiminishObject();
		if (this.object[i] == object) {
			this.object[i] = null;
			object = null;
			this.freeSlots.addFirst(i);
			sizeOfObject--;
	//		if (object.getId() != ID.Trail && object.getId() != ID.HUD) {
	//			resetObjectsMapArea(object);
	//		}
		}
		else {
			System.out.printf("In removeObject(); thread: %d, start == %d, end == %d, object size == %d, number of objects == %d; index == %d\n", Thread.currentThread().getId(), start, end, this.object.length, sizeOfObject, i);
			System.out.println("Failed to remove object");
		}
	//	System.out.printf("end of removeObject(); thread: %d, size of freeSlots == %d\n", Thread.currentThread().getId(), freeSlots.size());

	}
	
	private void resetObjectsMapArea(GameObject object2) {
		Rectangle rect = object2.getBounds();
		for (long x = Math.round(rect.getX()); x < rect.getX() + rect.getWidth(); x++) {
			for (long y = Math.round(rect.getY()); y < rect.getY() + rect.getHeight(); y++) {
				System.out.printf("\nx == %d; \ty == %d\n", (int) x, (int) y);
				try {
					map[(int) x][(int) y] = -1;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void maybeDiminishObject () {
		if(sizeOfObject < (object.length/4)) {
			GameObject[] resized = new GameObject[object.length/2 + 10];
			int resizedIndex = 0;
			
			for (int i = 0; i < object.length; i++) {
				if (object[i] != null) {
					resized[resizedIndex] = object[i];
					resized[resizedIndex].setIndex(resizedIndex++);
				}
			}
			object = resized;
			sizeOfObject = resizedIndex;
			freeSlots = fillFreeSlots();
		}
	//	System.out.println("end of maybeDiminishObject; Thread #: " + Thread.currentThread().getId() + " size of freeSlots == " + freeSlots.size());

	}
	
	
	public void gameOver(Graphics g) {
		g.setColor(Color.WHITE);
		System.out.println(g.getColor());
		g.drawString("GAME OVER", TheGame.WIDTH/2, TheGame.HEIGHT/2);
		g.drawString("Score: ", 100, 300);
		g.drawString("Level: ", 100, 300);
		System.out.println("Game over! Thank you for playing!");
		System.out.println(TheGame.WIDTH + " " + TheGame.HEIGHT);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
		System.exit(1);
	}
	
	public int getSizeObject () {
		return sizeOfObject;
	}
	public int getSizeOfObjectList () {
		return object.length;
	}
	
	public ArrayList<Player> getPlayers () {
		return players;
	}

//	@Override
//	protected abstract void compute();
//	protected abstract void run();
	@Override
	protected void compute() {
		for (int i = start; i <= end && i < object.length; i++) {
			GameObject tempObject = object[i];
			if (tempObject == null) {
				continue;
			}
			tempObject.tick();
		}
	}

	
}
