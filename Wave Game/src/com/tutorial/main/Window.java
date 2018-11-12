package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240840600533728354L;

	public Window (int width, int height, String title, TheGame theGame) {
		// created the frame of the window
		JFrame jFrame = new JFrame(title);
		
		jFrame.setPreferredSize(new Dimension(width, height));
		jFrame.setMaximumSize(new Dimension(width, height));
		jFrame.setMinimumSize(new Dimension(width, height));
		
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);	// having the x-button in the top right button working
		jFrame.setResizable(false);	// can we resize our window?
		jFrame.setLocationRelativeTo(null);	// not needed, but then our window will start in the top left, but if set to null, then the frame will be in the middle
		jFrame.add(theGame);	// placing game class into the frame, or else will have some problems.
		jFrame.setVisible(true);
		theGame.start();
	}
}
