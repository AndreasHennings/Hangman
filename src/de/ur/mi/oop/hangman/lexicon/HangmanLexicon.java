package de.ur.mi.oop.hangman.lexicon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import de.ur.mi.util.RandomGenerator;


public class HangmanLexicon implements LexiconInterface 
{
	ArrayList<String> allLines;  //A list storing all lines of selected text file
	ArrayList<String> allWords;  //A list storing all words that match the criteria defined for the game
	
	private int level;  //An integer value needed to determine the length of selected words
	
	private static final int WORD_MIN_LENGTH=4;  //Minimum length of words to be selected
	
	RandomGenerator randomGenerator;  //Declares a new random generator

	public HangmanLexicon(String filename, int level) 
	{
		allLines = new ArrayList<String>();
		allWords = new ArrayList<String>();
		
		randomGenerator = new RandomGenerator();
		this.level=level;
		
		readTextFile(filename);  //see method for description
		separateWords(); //see method for description	
	}
	
	/*
	 * This method opens a text file, reads all lines of words and stores them in the 
	 * allLines array list. If an error occurs while loading, it is printed out in the stack trace
	 * 
	 */
	
	private void readTextFile(String filename) 
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));  //Declare and initialize a new file reader with the filename
			while(true)  //do the following instructions 
			{
				String line = br.readLine();  //read single line
				if (line==null)  //if there are no more lines to read
				{
					br.close();  //close file reader
					break;  //leave 
				}
				allLines.add(line);	//add line to allLines arrayList			
			}			
		} 
		
		catch (Exception e) //if an error occurs,
		{
			e.printStackTrace();  //print it to the stack trace
		}		
	}
	
	/*
	 * This method iterates through all lines of the allWords arrayList. Every line is splitted into single words
	 * using the regular expression \\s that finds whitespace characters. The single words are stored in the
	 * wordsInLine Array.
	 * Finally, all words that have the required length (Minimum length + level) are stored in the allWords arrayList.
	 * 
	 */

	private void separateWords() 
	{
		for (int i =0; i < allLines.size(); i++)  //iterate through lines
		{
			String line = allLines.get(i);  //single out every line
			String[] wordsInLine = line.split("\\s");  //break into single words and store them in wordsInLine array
			
			for (int x=0; x < wordsInLine.length; x++)  //iterate through wordsInLine array
			{
				
				if (wordsInLine[x].length()==WORD_MIN_LENGTH+level)  //if word has the required length (minimum length + level)
				{
					allWords.add(wordsInLine[x]);  //store word in allWords array
				}
			}
		}
	}

	@Override
	public int getWordCount() 
	{
		return allWords.size();  //returns the number of words in the allWords arrayList
	}

	@Override
	public String getWord(int index) 
	{
		return allWords.get(index);  //returns the word found at a specific index (for testing purposes, not used in this game)
	}

	/*
	 * The following method returns a single word from the allWords arrayList.
	 * First, a random word (singleRandomWord) is chosen from the allWords arrayList and its letters 
	 * are converted to upperCase characters.
	 * Next, a new (empty) string called legalWord is created which is used for storing.
	 * Finally, the methods iterates through all characters of singleRandomWord and stores only legal characters (A-Z)
	 * in the legalWord string, getting rid of symbols like brackets, commas etc.
	 * If a German 'Umlaut' is found, it is converted to two legal characters and added to legal word.
	 */
	
	@Override
	public String getRandomWord() 
	{
				
		String singleRandomWord = allWords.get(randomGenerator.nextInt(0,allWords.size()-1)).toUpperCase(); //get random word from list and convert it to upper case
		String legalWord="";  //Create empty legalWord string
		
		for (int i=0; i<singleRandomWord.length(); i++)  //iterate through single characters of singleRandomWord string
		{
			char singleLetter = singleRandomWord.charAt(i);  //Gets a single character
			
			if (singleLetter>= 'A' && singleLetter <= 'Z')  //excludes unwanted characters
			{
				legalWord+=singleLetter;  //Stores the single character in legalWord String if its legal
			}
			
			//Find German 'ß' and Umlaute and replace them:
			
			if (singleLetter=='ß')
			{
				legalWord+="SS";
			}
			
			if (singleLetter=='Ä')
			{
				legalWord+="AE";
			}
			
			if (singleLetter=='Ö')
			{
				legalWord+="OE";
			}
			
			if (singleLetter=='Ü')
			{
				legalWord+="UE";
			}			
		}		
		return legalWord;  //returns a random word with all wanted characteristics
	}	
}