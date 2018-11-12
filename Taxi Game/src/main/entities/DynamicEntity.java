package main.entities;

import main.Screen;

public abstract class DynamicEntity extends Entity {

	public abstract void tick(); 
	public abstract void render(double x, double y, Screen screen);

}
