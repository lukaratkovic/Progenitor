package model;

import helpers.Utils;

public class BinaryGene extends Gene<Boolean> {
    /***
     * Initializes a new binary gene with random value
     */
    public BinaryGene() {
        this.value = Utils.getRandBool();
    }

    /***
     * Initializes a new binary gene with passed value
     * @param value true or false
     */
    public BinaryGene(Boolean value){
        this.value = value;
    }

    /***
     * Initializes a new binary gene with passed value. Integer is parsed to boolean.
     * @param value 0 or 1
     */
    public BinaryGene(int value){
        this.value = switch(value){
            case 0 -> false;
            case 1 -> true;
            default -> throw new IllegalArgumentException("Binary gene value must be 0 (false) or 1 (true)");
        };
    }

    /***
     * Flips the value bit
     * @return instance of BinaryGene object
     */
    @Override
    public Gene mutate() {
        value = !value;
        return this;
    }
}
