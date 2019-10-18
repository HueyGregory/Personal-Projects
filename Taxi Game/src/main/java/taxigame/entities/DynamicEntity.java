package taxigame.entities;

import taxigame.Entity;
import taxigame.render.Screen;

public abstract class DynamicEntity extends Entity {

	public abstract void tick(); 
	public abstract void render(double x, double y, Screen screen);

}
