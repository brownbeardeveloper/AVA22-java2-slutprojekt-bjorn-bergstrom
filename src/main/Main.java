package main;

import Controller.RegulatorController;

public class Main 
{
	public static void main(String[] args) 
	{
		RegulatorController regulatorController = RegulatorController.getInstance();
		regulatorController.startUp();
	}
}
