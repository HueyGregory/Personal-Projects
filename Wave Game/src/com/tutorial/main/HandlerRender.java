package com.tutorial.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;

import com.tutorial.GameObjects.GameObject;

public class HandlerRender extends Handler {
	
	private Graphics g;

	public HandlerRender(int start, int end, Graphics g) {
		super(start, end);
	//	System.out.println("Inside constructor for HandlerRender()");
		this.g = g;
	}

	@Override
	protected void compute() {
	//	System.out.println("Entered compute() of HandlerRender()' length of object = " + object.length);
	//	int objectSize = this.end - this.start;
	//	if (objectSize < 100) {
		//	this.end = objectSize;
			rendering();
	/*	}
		else {
			int numTasks = (objectSize / 100) + 1;
		//	ArrayList<Handler> tasks = new ArrayList<Handler>(numTasks);
			ArrayList<HandlerRender> tasks = new ArrayList<HandlerRender>(numTasks);
			System.out.println("numTasks == " + numTasks);
			for (int i = 0; i < numTasks;) {
		//		tasks.add(new Handler(i * (objectSize / numTasks), (++i * (objectSize / numTasks)) - 1));
				tasks.add(new HandlerRender(i * (objectSize / numTasks) + this.start, (++i * (objectSize / numTasks)) - 1 + this.start, g));
			}
			ForkJoinTask.invokeAll(tasks);
			tasks = null;
		}
		*/
	//	System.out.println("At the end of compute() of HandlerRender(); playerRemaining == " + playerRemaining);
	}
	
	private void rendering () {
	//	System.out.println("Inside Rendering of HandlerRender(); playerRemaining == " + playerRemaining);
		for (int i = start; i <= end && i < object.length; i++) {
	//		System.out.println("start = " + start + " end = " + end + " playerRemaining == " + playerRemaining + " g is not null: " + (g != null));
			GameObject tempObject = object[i];
			if(tempObject == null) {
				continue;
			}
	//		if (tempObject.id == ID.Player) {
	//			playerRemaining = true;
	//		}
			tempObject.render(g);
		}
	}
	
	
}
