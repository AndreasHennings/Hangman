package de.ur.mi.oop.hangman.scene;

import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Image;
import de.ur.mi.graphics.Label;
import de.ur.mi.oop.hangman.config.gameConfig;

public class MenuScene 
{
	Image[] backgroundImage;  //Declare an array to store images in
	
	//Screen resolution values, initialized by constructor
	
	int width;
	int height;
	
	int level;  //Variable to store the integer value that is displayed on screen
	
	private int picNum;  //The number of the picture to be displayed
	
	private char language;  //Value to store language in
	
	//Declare Labels displayed on screen
	
	private Label selectLevelLabel;
	private Label selectLanguageLabel;
	private Label startGameLabel;
	
	public MenuScene(int width, int height, char language, int level)	
	{
		picNum=0;  //Start with image #0
		
		//initialize values to those received from HangManApp through constructor
		
		this.language=language;
		this.level=level;
		this.width=width;
		this.height=height;		
		
		backgroundImage=new Image[10];  //The array is initialized to hold 10 images
		initImages();  //See method for details
		initLabels();  //See method for details
	}
		
	/*
	 * The following method initialize the individual labels to the corresponding text and determine
	 * their position, color, and text size
	 * 
	 */
	
	private void initLabels() 
	{
		selectLevelLabel = new Label(10, 200, "Press keys 1-9 to select level: "+level, Color.RED, gameConfig.TEXTSIZE_SMALL );
		selectLanguageLabel = new Label(10, 300, "Press (G)erman or (E)nglish to select language: "+language, Color.RED, gameConfig.TEXTSIZE_SMALL );
		startGameLabel = new Label(10, 400, "Press (S) to start game ", Color.RED, gameConfig.TEXTSIZE_SMALL );		
	}

	//Initializes the backgroundImage Array with the corresponding images
	
	private void initImages() 
	{
		for (int i=0; i<10; i++)  //iterate from 1 to 9
		{
			backgroundImage[i]=new Image(0, 0, width, height, gameConfig.IMAGENAME+i+".JPG");  //load corresponding image and store it in backgroundImage array.
		}		
	}

	//draws all elements to screen
	
	public void draw() 
	{
		switchBackgroundImage();  //See method for details
		backgroundImage[picNum].draw();  //draw corresponding image to screen
		drawLabels();  //See method for details
	}
	
	//Circle through all images by increasing the number of the image to be displayed, creating an animation

	private void switchBackgroundImage() 
	{
		picNum++;  //increase the identifier of the picture to be displayed
		if (picNum>9)  //There's only pictures 0-9
		{
			picNum=0;  //Start again from picture #0
		}		
	}

	// This method displays all labels on screen
	
	private void drawLabels() 
	{
		selectLevelLabel.draw();
		selectLanguageLabel.draw();
		startGameLabel.draw();		
	}
	
	//This method is called from HangManApp and is used to set the language option displayed
	
	public void setLanguage(char language)
	{
		this.language=language;
		initLabels();  //update the label that displays the language
	}
	
	//This method is called from HangManApp and is used to set the level displayed
	
	public void setLevel(int level) 
	{
		this.level=level;
		initLabels();  //update the label that displays the level
	}
}
