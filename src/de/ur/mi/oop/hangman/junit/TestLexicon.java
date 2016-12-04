package de.ur.mi.oop.hangman.junit;

import de.ur.mi.oop.hangman.lexicon.LexiconInterface;

public class TestLexicon implements LexiconInterface 
{
	
	private String defaultString;
	
	public TestLexicon(String defaultString) 
	{
		this.defaultString = defaultString;
	}

	public int getWordCount() 
	{
		return 1;
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) 
	{
		return defaultString;
	}
	
	public String getRandomWord() 
	{
		return defaultString;
	}

}
