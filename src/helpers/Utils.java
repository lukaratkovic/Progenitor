package helpers;

import java.util.Random;

public class Utils {
    public static Random rand;
    private static Utils instance = new Utils();
    private Utils(){rand = new Random();}
    public static Utils getInstance(){return instance;}

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
