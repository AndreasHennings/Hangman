package de.ur.mi.oop.hangman.scene;

import de.ur.mi.graphics.Color;
import de.ur.mi.graphics.Image;
import de.ur.mi.graphics.Label;
import de.ur.mi.oop.hangman.config.gameConfig;
import de.ur.mi.oop.hangman.game.HangmanGameInterface;


public class HangmanScene
{
	private Image backgroundImage;  //Declares an image object to store graphics in
	
	//Declaring all Labels needed to display information during game play:
	
	private Label commentLabel;
	private Label partlyGuessedWordLabel;
	private Label guessesLeftLabel;
	private Label guessedLettersLabel;
	private Label gameInfoLabel;
	private Label escapeInfoLabel;
	
	private HangmanGameInterface hangmanGame;  //Needed for communication with the hangmanGame class
	private String comment;  //A String containing comments on the players action
	
	private int width;
	private int height;
	private int level;
	private int numberOfImage;  //A value to store the number of the image which is displayed
	
	public HangmanScene(int width, int height, HangmanGameInterface hangmanGame, int level) 
	{	
		//Initializing variables to values received from the HangManApp
		
		this.width=width;
		this.height=height;
		this.hangmanGame=hangmanGame;
		this.level=level;
		
		numberOfImage=0;  //Start with image #0	
		comment="I'm waiting for your first guess!";  //Initial comment
		
		backgroundImage = new Image(0, 0, width, height, gameConfig.IMAGENAME+numberOfImage+".JPG");  //load image #0
	}

	public void draw() 
	{
		backgroundImage.draw();  //Draws background image to screen
		drawLabels();  //see method for description		
	}

	//draw all labels to the screen:
	
	private void drawLabels() 
	{
		drawCommentLabel();
		drawPartlyGuessedWordLabel();
		drawGuessesLeftLabel();
		drawguessedLettersLabel();	
		drawGameInfoLabel();
		drawEscapeInfoLabel();
	}
	
	/*
	 * The following method initialize the individual labels to the corresponding text and determine
	 * their position, color, and text size. Not commented individually as they're pretty much
	 * self-explaining
	 * 
	 */
	
	private void drawCommentLabel() 
	{
		commentLabel = new Label(10, 100, comment, Color.RED, gameConfig.TEXTSIZE_SMALL );
		commentLabel.draw();		
	}

	private void drawPartlyGuessedWordLabel() 
	{
		partlyGuessedWordLabel = new Label(10, 200, hangmanGame.getPartlyGuessedWord(), Color.RED, gameConfig.TEXTSIZE_LARGE);
		partlyGuessedWordLabel.draw();	
	}
	
	private void drawGuessesLeftLabel() 
	{
		guessesLeftLabel=new Label(10, 300,"You have "+hangmanGame.getGuessesLeft()+" guesses left", Color.RED, gameConfig.TEXTSIZE_SMALL);
		guessesLeftLabel.draw();		
	}
	
	private void drawguessedLettersLabel() 
	{
		guessedLettersLabel=new Label(10, 350,"Letters already guessed: "+hangmanGame.getGuessedLetters(), Color.RED, gameConfig.TEXTSIZE_SMALL);
		guessedLettersLabel.draw();
	}
	
	private void drawGameInfoLabel() 
	{
		gameInfoLabel= new Label(10, 450, "Level: "+level,Color.BLACK,gameConfig.TEXTSIZE_SMALL);
		gameInfoLabel.draw();		
	}

	private void drawEscapeInfoLabel() 
	{
		escapeInfoLabel=new Label(10, 500,"Hit grave key (+) to escape",Color.BLACK, gameConfig.TEXTSIZE_SMALL);
		escapeInfoLabel.draw();		
	}
	
	//Set comment Text. Called from HangManApp when a key is pressed
	
	public void setText(String comment) 
	{
		this.comment=comment;		
	}

	//Skip to next image. Called from HangManApp when the wrong key is pressed
	
	public void incPicNum() 
	{
		if (numberOfImage<9)
		{
			numberOfImage++;
		}
		backgroundImage = new Image(0, 0, width, height, gameConfig.IMAGENAME+numberOfImage+".JPG");
	}
}