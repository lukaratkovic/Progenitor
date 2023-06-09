package model;

import exceptions.ValueOutOfBoundsException;
import helpers.Rand;

/**
 * Implementation of a Gene with Integer values within a user-defined range
 */
public class IntegerValueGene extends Gene<Integer> {
    /***
     * Inclusive lower bound
     */
    Integer lowerBound;
    /***
     * Exclusive upper bound
     */
    Integer upperBound;

    /**
     * Constructor with random value in range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     */
    public IntegerValueGene(Integer lowerBound, Integer upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = Rand.getRandInteger(lowerBound, upperBound);
    }

    /**
     * Constructor with a given value and range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     * @param value value within given bounds
     */
    public IntegerValueGene(Integer lowerBound, Integer upperBound, Integer value){
        if(value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) >= 0)
            throw new ValueOutOfBoundsException("Passed value is out of defined bounds");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = value;
    }

    /**
     * Set the value of the Gene to a new random value within defined bounds
     * @return instance of CharacterValueGene
     */
    @Override
    public Gene mutate() {
        value = Rand.getRandInteger(lowerBound, upperBound);
        return this;
    }

    /**
     * Creates a new instance of an integer value gene with same bounds
     * @return new instance of IntegerValueGene
     */
    @Override
    public Gene getInstance() {
        return new IntegerValueGene(lowerBound, upperBound);
    }

    /**
     * Creates a new instance of an integer value gene with same bounds and value
     * @return new instance of IntegerValueGene
     */
    @Override
    public Gene clone() {
        return new IntegerValueGene(lowerBound, upperBound, value);
    }
}