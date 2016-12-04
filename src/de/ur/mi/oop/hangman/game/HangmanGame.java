package de.ur.mi.oop.hangman.game;

import de.ur.mi.oop.hangman.lexicon.LexiconInterface;


public class HangmanGame implements HangmanGameInterface 
{
	private char[] partlyGuessedWordCharArray; //Array to store characters of partlyGuessedWord
	
	private static final int MAX_GUESSES = 9;  //Maximum number of guesses
	
	// Declaring strings and integers to store the corresponding values. Please see the HangmanGameInterface for details
	 
	private String hangmanWord;
	private String guessedLetters;
	private String partlyGuessedWord;
	
	private int guessesLeft;
	private int incorrectGuesses;
	private int correctGuesses;

	public HangmanGame(LexiconInterface l) 
	{
		hangmanWord = l.getRandomWord();  //Initializes the hangmanWord with a random word from the lexicon
		
		guessedLetters="";  //At game start, no letters have been guessed
		partlyGuessedWord="";  //Initializes an empty string
		
		partlyGuessedWordCharArray = new char [hangmanWord.length()];  //size of array equals the length of the hangmanWord
		initPartlyGuessedWord();  //see method for description
		
		guessesLeft = MAX_GUESSES;  //At game start, you have the maximum number of guesses left
		
		incorrectGuesses = 0;
		correctGuesses = 0;	
	}	

	private void initPartlyGuessedWord() 
	{		
		for (int i=0; i<hangmanWord.length(); i++)  //iterates through hangmanWord
		{
			partlyGuessedWordCharArray[i]='+';  //fills partlyGuessedWordCharArray with + symbols
		}	
		updatePartlyGuessedWord();  //see method for description
	}
	
	private void updatePartlyGuessedWord()
	{
		String newPartlyGuessedWord ="";  //initializes an empty string
		for (int i = 0; i<partlyGuessedWordCharArray.length; i++)  //iterates through partlyGuessedWord array
		{
			newPartlyGuessedWord+=partlyGuessedWordCharArray[i];  //adds corresponding letters to string
		}
		partlyGuessedWord=newPartlyGuessedWord;  //returns the new string
	}

	@Override
	public boolean guess(char letter) 
	{	
		guessesLeft--;  //decrease amount of guessesLeft
		
		if (guessedLetters.indexOf(letter)<0)  //letter has not been guessed before
		{	
			guessedLetters+=letter;  //add letter to guessedLetters list
			
			if (hangmanWord.indexOf(letter)>=0) //letter is in hangmanWord
			{
				for (int i=0; i<hangmanWord.length(); i++)  //iterates through hangmanWord
				{
					if (hangmanWord.charAt(i)==letter)  //finds out if guessed letter is in hangmanWord
					{
						partlyGuessedWordCharArray[i]=letter;  //if true, guessed letter is added to partlyGuessedWord Array 
					}
				}
				updatePartlyGuessedWord();  //see method for description
				return true;  //correct letter was chosen
			}
			return false;  //letter was not in hangmanWord
		}	
		return false;  //letter had been guessed before
	}
	
	/*
	 * 
	 * The following methods return values needed for gameplay
	 * See de.ur.mi.oop.hangman.game.HangmanGameInterface
	 * for a detailed description
	 * 
	 */

	@Override
	public String getPartlyGuessedWord() 
	{	
		return partlyGuessedWord;
	}

	@Override
	public String getHangmanWord() 
	{
		return hangmanWord;
	}

	@Override
	public String getGuessedLetters() 
	{
		return guessedLetters;
	}

	@Override
	public boolean isGameLost() 
	{
		return guessesLeft<=0;
	}

	@Override
	public boolean isGameWon() 
	{
		return hangmanWord.equals(partlyGuessedWord);
	}

	@Override
	public int getGuessesLeft() 
	{
		return guessesLeft;
	}

	@Override
	public int getIncorrectGuesses() 
	{
		return incorrectGuesses;
	}

	@Override
	public int getCorrectGuesses() 
	{
		return correctGuesses;
	}
}