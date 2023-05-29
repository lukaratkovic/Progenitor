package helpers;

import java.util.Random;

public class Rand {
    public static Random rand;
    private static Rand instance = new Rand();
    private Rand(){rand = new Random();}
    public static Rand getInstance(){return instance;}

    public static Boolean getRandBool(){
        return rand.nextBoolean();
    }

    public static Integer getRandInteger(int lowerBound, int upperBound){
        return rand.nextInt(lowerBound, upperBound);
    }

    public static Double getRandDouble(double lowerBound, double upperBound){
        return rand.nextDouble(lowerBound, upperBound);
    }
}
