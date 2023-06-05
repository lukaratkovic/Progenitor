package helpers;

import java.util.Random;

/**
 * Singleton class for generating random values, used to avoid creation of multiple Rand objects
 */
public class Rand {
    public static Random rand;
    private static Rand instance = new Rand();
    private Rand(){rand = new Random();}
    public static Rand getInstance(){return instance;}

    /**
     * Generates a Boolean with a random value
     * @return true or false
     */
    public static Boolean getRandBool(){
        return rand.nextBoolean();
    }

    /**
     * Generates a random Integer within bounds
     * @param lowerBound Lower bound (inclusive)
     * @param upperBound Upper bound (exclusive)
     * @return Integer in range [lowerBound, upperBound>
     */
    public static Integer getRandInteger(int lowerBound, int upperBound){
        return rand.nextInt(lowerBound, upperBound);
    }

    /**
     * Generates a random Double within bounds
     * @param lowerBound Lower bound (inclusive)
     * @param upperBound Upper bound (exclusive)
     * @return Double in range [lowerBound, upperBound>
     */
    public static Double getRandDouble(double lowerBound, double upperBound){
        return rand.nextDouble(lowerBound, upperBound);
    }
}
