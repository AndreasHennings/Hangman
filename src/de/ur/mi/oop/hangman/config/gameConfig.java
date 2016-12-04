package de.ur.mi.oop.hangman.config;

public class gameConfig 
{	
	//Screen resolution
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	//Size of text to be displayed
	
	public static final int TEXTSIZE_SMALL = 25;
	public static final int TEXTSIZE_LARGE = 40;
	
	//Folder where all sounds are stored
	
	public static final String SOUND_GAME_MUSIC = "../assets/sounds/correct.wav";
	public static final String SOUND_GUESS_CORRECT = "../assets/sounds/correct.wav";
	public static final String SOUND_GUESS_WRONG = "../assets/sounds/fail.wav";
	public static final String SOUND_GAME_LOST = "../assets/sounds/lost.wav";
	public static final String SOUND_GAME_WON = "../assets/sounds/won.wav";
	
	//Parts of filenames for the lexicon and the image files, completed in HangManApp
	
	public static final String LEXICON_FILENAME= "../assets/lexicon";
	public static final String IMAGENAME="../assets/images/pic";

			
}