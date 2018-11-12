package com.tutorial.main;
import gettingFromWeb.FreeRice;
import gettingFromWeb.LoggingIn;

public class Start {

	public static void main(String[] args) {
		System.out.println("Entered main of Game1 - space implementation");
		Start start = new Start();
		start.run();
		System.out.println("Exited main");
	}
	
	public Start () {
		System.out.println("At the start of Start");
	//	LoggingIn loggingIn = new LoggingIn("https://homework.russianschool.com/#/login", "form", "username", "password", "y.wasserman", "hj", "Sign in");
	//	FreeRice freeRice = new FreeRice();
		
		System.out.println("At the end of Start");
		
	}
	private void run() {
		TheGame theGame = new TheGame();
	}
}
