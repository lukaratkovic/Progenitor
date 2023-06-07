package model;

import helpers.Rand;

/**
 * Implementation of a Gene with a binary value
 */
public class BinaryGene extends Gene<Boolean> {
    /***
     * Constructor with random value
     */
    public BinaryGene() {
        this.value = Rand.getRandBool();
    }

    /***
     * Constructor with given value
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

    /**
     * Creates a new instance of a binary gene
     * @return new instance of BinaryGene
     */
    @Override
    public Gene getInstance() {
        return new BinaryGene();
    }

    /**
     * Creates a new instance of a binary gene with equal value
     * @return new instance of a BinaryGene
     */
    @Override
    public Gene clone() {
        return new BinaryGene(value);
    }
}
