package main.entities;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

import main.entities.dynamic.vehicles.Player;
import main.render.Screen;

public class ObjectList <E extends Entity> {
	
	public static ObjectList<DynamicEntity> dynamicEntities = new ObjectList<DynamicEntity>(DynamicEntity.class, 100);
	public static ObjectList<Player> players = new ObjectList<Player>(Player.class, 2);
	//public static ObjectList<Tile> tiles = new ObjectList<Tile>(Tile.class, 100);
	
	private E[] list;
	private LinkedList<Integer> freeList;
	private Class<E> clazz;
	
	private int numberOfObjects;
	
	@SuppressWarnings("unchecked")
	public ObjectList (Class<E> clazz, int size) {
		this.clazz = clazz;
		list = (E[]) Array.newInstance(clazz, size);
		freeList = new LinkedList<Integer>();
	}
	
	public void tick() {
		for (int i = 0; i < this.size(); i++) {
			 if (this.get(i) != null) {
				 this.get(i).tick();
			 }
		}
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < this.size(); i++) {
			Entity currEntity = this.get(i);
			 if (currEntity != null) {
				 if (withinBounds(currEntity.getX(), currEntity.getY(), screen)) {
					 currEntity.render(currEntity.getX(), currEntity.getY(), screen);
				 }
				 else {
					System.out.printf("[ObjectList - render()] currEntity is NOT within bounds; currEntity.getX(): %d; currEntity.getY(): %d \n", currEntity.getX(), currEntity.getY());
				}
			 }
		}
	}


	private boolean withinBounds(int x, int y, Screen screen) {
		if ((x >= screen.getXPixel()) && (x <= screen.getXPixel() + screen.getWidthPixel()) && (y >= screen.getYPixel()) && (y <= screen.getYPixel() + screen.getHeightPixel())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public E[] getListAsArray () {
		return list;
	}
	
	public E get(int i) {
		return list[i];
	}
	
	private synchronized void doubleListSize() {
		int sizeOfList = list.length;
		E[] newList = Arrays.copyOf(list, sizeOfList * 2);
		for (int i = newList.length; i > sizeOfList; i--) {
			freeList.addFirst(i);
		}
		list = newList;
		
	}
	
	private synchronized void halveListSize() {
		int sizeOfList = list.length;
		E[] newList = (E[]) Array.newInstance(this.clazz, sizeOfList/2);
		freeList = new LinkedList<Integer>();
		int indexNewList = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null) {
				list[i].setIndex(indexNewList);
				newList[indexNewList++] = list[i];
			}
		}
		list = newList;
		fillFreeList();
	}
	
	public int size() {
		return list.length;
	}
	
	public int getNumberOfObjects() {
		return numberOfObjects;
	}
	
	public void add(E obj) {
		if (this.numberOfObjects >= this.list.length - 2) {
			doubleListSize();
		}
		int i = freeList.removeFirst();
		this.list[i] = obj;
		numberOfObjects++;
	}
	
	public void remove(int i, E object) {
		if (list[i].equals(object)) {
			list[i] = null;
			freeList.addFirst(i);
			numberOfObjects--;
		}
		else {
			System.out.println("Failed to remove object at index " + i);
		}
		if (this.numberOfObjects <= list.length/4) {
			halveListSize();
		}
		
	}
	
	private void fillFreeList () {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == null) {
				freeList.addFirst(i);
			}
		}
	}
}
