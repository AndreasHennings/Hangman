package de.ur.mi.oop.hangman;

import de.ur.mi.graphicsapp.GraphicsApp;
import de.ur.mi.oop.hangman.game.HangmanGame;
import de.ur.mi.oop.hangman.game.HangmanGameInterface;
import de.ur.mi.oop.hangman.lexicon.HangmanLexicon;
import de.ur.mi.oop.hangman.scene.HangmanScene;
import de.ur.mi.oop.hangman.scene.MenuScene;
import de.ur.mi.sound.Sound;
import de.ur.mi.oop.hangman.config.gameConfig;

/*
 * A dark and gloomy version of the well-known game Hangman. 
 * Sounds are from http://www.freesound.org
 * Words to be guessed are from the English and German Wikipedia articles on "gallow" and "Galgen".
 * 
 * All images in this game are my own drawings. I decided to use them instead of constructing a simple figure
 * of a hangman using line and circle methods because I felt that they better fit to the atmosphere I wanted
 * to create in this game. 
 * 
 * Written by Andreas Hennings, January 2015
 */

@SuppressWarnings("serial")
public class HangManApp extends GraphicsApp 
{
	private boolean isGameRunning;  //true: game scene is running. false: game in menu scene mode
	private boolean isGameMusicPlaying;  //determines if gameMusic is playing
	private boolean isGameWaiting;  //true if game is waiting for the player to press '+'-key to continue
	
	private char language;  //Can be (G)erman or (E)nglish
	private int level;  //Used to store level value in. An integer from 1 to 9
	
	// Declaring sounds used in this game. See initSounds()-method below for description
	
	public Sound gameMusic;
	public Sound fail;
	public Sound correct;
	public Sound lost;
	public Sound won;
	
	private String filename;  //Stores the name of the text file to be loaded and processed in the hangmanLexicon class
	
	//Declare instances of the classes used in this game. See individual classes for description
	
	private HangmanGameInterface hangmanGame;
	private HangmanScene hangmanScene;
	private MenuScene menuScene;
	private HangmanLexicon hangmanLexicon;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public void setup() 
	{
		isGameRunning=false;  //Start with menu screen
		
		initSounds();  //Initialize all sounds	
		gameMusic.loop();  //Start loop-playing game music
		isGameMusicPlaying=true;
		
		size(WIDTH, HEIGHT);
		frameRate(3);
		
		language='G'; //Default starting language is German
		level=1;  //Default starting level
		
		filename = gameConfig.LEXICON_FILENAME+'G'+".txt";
		
		hangmanLexicon = new HangmanLexicon(filename,level);
		hangmanGame = new HangmanGame(hangmanLexicon);
		hangmanScene = new HangmanScene(WIDTH, HEIGHT, hangmanGame,level);
		menuScene=new MenuScene(width, height, language, level);
	}

	/*
	 * Load and initialize all sounds
	 */

	private void initSounds() 
	{
		gameMusic = new Sound(gameConfig.SOUND_GAME_MUSIC);  //Loop-plays during game
		correct = new Sound(gameConfig.SOUND_GUESS_CORRECT);  //plays once if correct letter is guessed
		fail = new Sound(gameConfig.SOUND_GUESS_WRONG);  //plays once if wrong letter is guessed
		won = new Sound(gameConfig.SOUND_GAME_WON);  //plays once if game is won
		lost = new Sound(gameConfig.SOUND_GAME_LOST); //plays once if game is lost
	}

	public void draw()
	{
		if (!isGameMusicPlaying)  //turns on sound if sound is off
		{
			gameMusic.loop();  //loop-play gameMusic
			isGameMusicPlaying=true;
		}
		
		if (isGameRunning && !isGameWaiting)  //draws game scene if game is running and program is not waiting for '+'-key
		{
			drawHangmanScene();  //See method for details
		}
				
		if (!isGameRunning && !isGameWaiting)  //draws menu scene if game is running and program is not waiting for '+'-key
		{
			menuScene.draw();  //See method for details
		}
	}
	
	private void drawHangmanScene() 
	{
		
		hangmanScene.draw();  //see corresponding method for details
		
		if (hangmanGame.isGameWon())
		{
			hangmanScene.setText("CONGRATULATIONS - YOU WON!");  //set new comment
			hangmanScene.draw();  //draw it to screen
			
			gameMusic.stop();  //stop music
			isGameMusicPlaying=false;
			won.play();  //play won sound
			
			isGameRunning=false;  //switch to menu scene mode
			isGameWaiting=true;  //wait for '+'-key to be pressed
		}
		
		if (isGameRunning && hangmanGame.isGameLost())  //needs to check both to avoid game is both lost and won
		{
			hangmanScene.setText("GAME OVER - YOU'RE DEAD! The Word was: "+hangmanGame.getHangmanWord());  //create new comment, show hangmanWord
			hangmanScene.draw();  //draw comment to screen
			
			gameMusic.stop();
			isGameMusicPlaying=false;  //stop music
			lost.play();  //play game lost sound once
			
			isGameRunning=false;  //switch to menu scene mode
			isGameWaiting=true;  //wait for '+'-key to be pressed
		}			
	}
	
	public void keyPressed() 
	{
		if (isGameWaiting)  //game is waiting for player to press a key
		{
			if (key=='+')  //if the key is '+'
			{
				isGameWaiting=false;  //the game continues
			}
		}
		
		if (isGameRunning && !isGameWaiting)  //game is in game scene mode			
		{
			keyInputGame(key);	//call method with the pressed key		
		}
		
		if (!isGameRunning && !isGameWaiting)  //game is in menu scene mode
		{
			keyInputMenu(key);  //call method with the pressed key
		}
				
	}

	//The following method is used to set level and language. Called when game is in menu scene mode

	private void keyInputMenu(char key) 
	{
		if (key>='1' && key<='9') //A digit key is pressed
		{
			level=key-48;  //Converting ASCII-key input to an integer value by subtracting 48
			menuScene.setLevel(level);  //Tell menu about which level to display
		}
		
		if (key=='e'||key=='E')  //The 'e' key is pressed. Player wants to guess an English word
		{
			language=Character.toUpperCase(key);  //Convert input to upper case
			menuScene.setLanguage(language);  //Tell menu about which language to display
			filename = gameConfig.LEXICON_FILENAME+language+".txt";  //set filename string to point at corresponding lexicon
		}
		
		if (key=='g'||key=='G')  //The 'e' key is pressed. Player wants to guess a German word
		{
			language=Character.toUpperCase(key);  //Convert input to upper case
			menuScene.setLanguage(language);  //Tell menu about which language to display
			filename = gameConfig.LEXICON_FILENAME+language+".txt";  //set filename string to point at corresponding lexicon
		}
		
		if (key=='s'||key=='S')  //The player wants to start a new game
		{
			//Initialize instances of used classes with the correct values
			
			hangmanLexicon = new HangmanLexicon(filename,level);
			hangmanGame = new HangmanGame(hangmanLexicon);		
			hangmanScene = new HangmanScene(WIDTH, HEIGHT, hangmanGame, level);
			
			isGameRunning=true;  //Game is in game scene mode
		}		
	}

	//The following method is used when game is in game scene mode to tell hangmanGame about which letter was guessed

	private void keyInputGame(char key) 
	{
		char guessedKey = Character.toUpperCase(key);  //Convert pressed key to upper case
		if (guessedKey >= 'A' && guessedKey <= 'Z')  //if letter is between A and Z
		{
			boolean result=hangmanGame.guess(guessedKey);  //Call guess-Method of hangmanGame with letter, receive result. 
			if (result)  //If letter was guessed correctly
			{
				hangmanScene.setText("Correct! You Rock!");  //Send text to hangmanScene
				correct.play();  //play sound for correctly guessed letter once
			}
			
			if (!result)  //If letter was guessed before or is not in hangmanWord
			{
				hangmanScene.setText("Totally wrong, Dude!");  //Send text to hangmanScene
				hangmanScene.incPicNum();  //switch to next image in hangmanScene
				fail.play();  //play sound for falsely guessed letter once
			}
		}
		
		if (key=='+')  //if '+'-key is pressed
		{
			isGameRunning=false;  //escape from game scene and switch to menu mode
		}		
	}	
}
