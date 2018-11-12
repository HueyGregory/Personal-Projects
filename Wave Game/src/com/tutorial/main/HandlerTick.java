package com.tutorial.main;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;

import com.tutorial.GameObjects.GameObject;

public class HandlerTick extends Handler {

	public HandlerTick(int start, int end) {
		super(start, end);
	}
	
	@Override
	protected void compute() {
		int objectSize = this.end - this.start;
		if (objectSize < 800) {
		//	this.end = objectSize;
			tick();
		}
		else {
			int numTasks = (objectSize / 800) + 1;
			ArrayList<HandlerTick> tasks = new ArrayList<HandlerTick>(numTasks);
		//	System.out.println("numTasks == " + numTasks);
			for (int i = 0; i < numTasks;) {
				tasks.add(new HandlerTick(i * (objectSize / numTasks) + this.start, (++i * (objectSize / numTasks)) - 1 + this.start));
			}
			ForkJoinTask.invokeAll(tasks);
			tasks = null;
		}	
	}
	
	@Override
	public void tick () {
		for (int i = start; i <= end && i < object.length; i++) {
			GameObject tempObject = object[i];
			if (tempObject == null) {
				continue;
			}
			tempObject.tick();
		}
	}
}
