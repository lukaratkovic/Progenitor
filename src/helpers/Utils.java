package helpers;

import java.util.Random;

public class Utils {
    static Random rand;
    private static Utils instance = new Utils();
    private Utils(){rand = new Random();}
    public static Utils getInstance(){return instance;}

    public static Boolean getRandBool(){
        return rand.nextBoolean();
    }
}
