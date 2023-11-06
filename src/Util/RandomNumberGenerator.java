package Util;

public abstract class RandomNumberGenerator {
	
	/**
	 * Generates a random integer between the specified minimum and maximum values (inclusive).
	 * @param min The minimum value (inclusive) of the generated random integer.
	 * @param max The maximum value (inclusive) of the generated random integer.
	 * @return A random integer between the specified minimum and maximum values.
	 * @throws IllegalArgumentException if min is greater than max.
	 */
	public static int getInt(int min, int max) 
	{
	    if (min > max) 
	    {
	        throw new IllegalArgumentException("min must be less than or equal to max");
	    }
	    return (int) Math.round(Math.random() * (max - min) + min);
	}
}
