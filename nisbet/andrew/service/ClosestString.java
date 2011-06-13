/**
 * 
 */
package nisbet.andrew.service;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Closest string is an algorithm that will find the nearest match of a 
 * series of strings.
 * @author anisbet
 *
 */
public class ClosestString 
{
	private StringBuffer[] matrix = null;
	private StringBuffer[] output = null;
	private int stringCount;
	private int minScore = Integer.MAX_VALUE;
	private int[] smallPair = new int[2];
	
	// temporary storage of strings for processing later.
	private Vector<String> tempMatrix;
	
	/**
	 * First of the strings to compare -- add others with the addString method.
	 * @param string
	 */
	public ClosestString( String string )
	{
		tempMatrix = new Vector<String>();
		tempMatrix.add( string );
	}
	
	/**
	 * A set of strings to compare.
	 * @param strings
	 */
	public ClosestString( String[] strings )
	{
		start(strings);
	}
	
	/**
	 * Adds a string to the list to be compared.
	 * @param string
	 */
	public void addString( String string )
	{
		tempMatrix.add(string);
	}
	
	/**
	 * Start if you have used the single argument constructor.
	 */
	public void start()
	{
		String[] strings = new String[tempMatrix.size()];
		int i = 0;
		for ( String string : tempMatrix )
		{
			strings[i++] = string;
		}
		start(strings);
	}
	
	/**
	 * @param strings
	 */
	public void start( String[] strings )
	{
		stringCount = strings.length;
		matrix = new StringBuffer[stringCount];
		output = new StringBuffer[stringCount];
		int longest = findLongestString(strings);
		// all strings have to be the same length so lengthen them from the '.' in the suffix.
		for ( int i = 0; i < stringCount; i++ )
		{
			matrix[i] = lengthenString( longest, strings[i] );
			// initialize the stringbuffers too.
			output[i] = new StringBuffer();
		}
		
		// now normalize the matrix
		Hashtable<Character, Integer> charFrequencies = null;
		for ( int i = 0; i < longest; i++ )
		{
			// make a note of the character frequencies for each string at this location.
			charFrequencies = new Hashtable<Character, Integer>();
			for ( StringBuffer string : matrix )
			{
				getFrequencies( charFrequencies, string, i );
			}
			// now we have a frequency count for each string at this character set the output matrix to appropriate values
			Hashtable<Character, String> orderedFrequencies = getOrderedFrequencies( charFrequencies );
			for ( int j = 0; j < output.length; j++ )
			{
				Character cin = matrix[j].charAt(i);
				String cout= orderedFrequencies.get( cin );
				output[j].append( cout );
			}
		}
		computeScore( 0 ); // Start the comparison with the zeroth element.
		System.out.println( "String " + 
				matrix[this.smallPair[0]] + " and " + matrix[this.smallPair[1]] + 
				" are the closest match with a score of " + this.minScore );
	}

	/**
	 * Computes the score of the closest pair giving a clear result of which of the strings is closest.
	 */
	private void computeScore( int which ) 
	{
	
		for ( int i = which +1; i < output.length; i++ )
		{
			int currentScore = xor( this.output[which], this.output[i] );
			if ( currentScore < this.minScore )
			{
				this.minScore = currentScore;
				this.smallPair[0] = which;
				this.smallPair[1] = i;
			}
		}
		if ( which +1 >= output.length )
		{
			return;
		}
		computeScore(which+1); // continue recursively
	}
	
	/**
	 * XORs each string and returns the number of bits that are different between two strings.
	 * @param str0
	 * @param str1
	 * @return 0 if the strings match and greater than 0 if they don't
	 */
	private int xor( StringBuffer str0, StringBuffer str1 )
	{
		int bitDiffCount = 0;
		
		for ( int i = 0; i < str0.length(); i++ )
		{			
			if ( str0.charAt(i) != str1.charAt(i) )
			{
				bitDiffCount++;
			}
		}
		
		return bitDiffCount;
	}

	/**
	 * Computes a hashtable whose buckets are the computed character based on the frequency of the key, where
	 * the value 'a' is the most common character from the sample, 'b' would be the next most common and so on.
	 * @param charFrequencies
	 * @return hashtable of Character input and character as a String output.
	 */
	private Hashtable<Character, String> getOrderedFrequencies( Hashtable<Character, Integer> charFrequencies ) 
	{
		Hashtable<Character, String> orderedFrequencies = new Hashtable<Character, String>();
		Set<Character> keySet = charFrequencies.keySet();
		Hashtable<Integer, Character> reverseLookup = computeReverseFrequency( charFrequencies );
		for ( Character characterKey : keySet )
		{
			Integer frequency = charFrequencies.get( characterKey );
			Character character = reverseLookup.get( frequency );
			orderedFrequencies.put( characterKey, character.toString() );
		}

		return orderedFrequencies;
	}


	/**
	 * @param charFrequencies
	 * @return
	 */
	private Hashtable<Integer, Character> computeReverseFrequency( Hashtable<Character, Integer> charFrequencies ) 
	{
		Set<Character> keySet = charFrequencies.keySet();
		Integer[] intArray = new Integer[ keySet.size() ];
		Iterator<Character> itKeys = keySet.iterator();
		int i = 0;
		// get the keys and pack an array with the values.
		while ( itKeys.hasNext() )
		{
			Character myKey = itKeys.next();
			intArray[ i++ ] = charFrequencies.get( myKey );
		}
		// Sortem
		Arrays.sort(intArray);
		
		// create the return type and place them so I can give a frequency and it will return the letter.
		Hashtable<Integer, Character> lookupTable = new Hashtable<Integer, Character>();
		for ( int j = intArray.length -1, k = 97; j >= 0; j--, k++ ) // k integer value of character.
		{
			lookupTable.put(intArray[j], new Character( Character.valueOf( (char) k )));
		}
		
		return lookupTable;
	}

	/**
	 * Counts how many characters are at each character position.
	 * @param string
	 * @param whichChar
	 */
	private void getFrequencies( Hashtable<Character, Integer> hashTable, StringBuffer string, int whichChar ) 
	{
		
		char key = string.charAt(whichChar);
		Integer i = hashTable.get(key);
		if ( i == null )
		{
			insertValue( hashTable, key, 1 );
			//hashTable.put(key, 1); // initialize this bucket.
			return;
		}
		// else increment the bucket
		int myInt = i.intValue();
		myInt++;
		insertValue( hashTable, key, myInt );
		//hashTable.put(key, myInt);
	}

	/**
	 * Each frequency must have a unique value -- even if the value matches another. For example if we are comparing
	 * 4 strings e,e,f,x we see that e has a frequency of 2 but both f and x have frequencies of 1 each. This causes
	 * a problem because in the worst case c,d,e,f each is different character but all could claim they are the highest
	 * frequency and the result would be d,d,d,d which would match all strings as closest. To get around that we 
	 * are going to, in the case of a tie, rank the frequencies in decending order depending on their order in the 
	 * array of Strings. I change the frequency by the following formula: 97 + index of parent string.
	 * @param hashTable
	 * @param key of the current frequency to be added to the table
	 * @param i the current frequency to be added to the table
	 */
	private void insertValue( Hashtable<Character, Integer> hashTable, char key, int i )
	{
		int j = i;
		if ( hashTable.containsKey(key))
		{
			//we add the frequencies we have with the ones already there
			j += hashTable.get(key);
		}
		while ( hashTable.containsValue(j) )
		{
			j++;
		}
		hashTable.put( key, j );
	}

	/**
	 * @param longest
	 * @param string
	 * @return string padded with 'a's to the length of the longest string.
	 */
	private StringBuffer lengthenString( int longest, String string ) 
	{
		StringBuffer buffer = new StringBuffer();
		int pad = longest - string.length();
		for ( int i = 0; i < pad; i++ )
		{
			buffer.append("a");
		}
		buffer.append( string );
		return buffer;
	}

	/**
	 * @param strings
	 * @return the length of the longest string.
	 */
	private int findLongestString(String[] strings) 
	{
		int max = 0;
		for ( String string : strings )
		{
			if ( max < string.length() )
			{
				max = string.length();
			}
		}
		return max;
	}
	
	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for ( StringBuffer string : output )
		{
			buffer.append( string + "\n" );
		}
		return buffer.toString();
	}
	
}
