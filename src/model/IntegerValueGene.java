package model;

import helpers.Rand;

public class IntegerValueGene extends NumericValueGene<Integer> {
    /**
     * Initializes a new binary gene with random value in range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     */
    public IntegerValueGene(Integer lowerBound, Integer upperBound) {
        super(lowerBound, upperBound, Rand.getRandInteger(lowerBound, upperBound));
    }

    public IntegerValueGene(Integer lowerBound, Integer upperBound, Integer value){
        super(lowerBound, upperBound, value);
    }
    @Override
    public Gene mutate() {
        value = Rand.getRandInteger(lowerBound, upperBound);
        return this;
    }

    @Override
    public Gene getInstance() {
        return new IntegerValueGene(lowerBound, upperBound);
    }

    @Override
    public Gene clone() {
        return new IntegerValueGene(lowerBound, upperBound, value);
    }
}