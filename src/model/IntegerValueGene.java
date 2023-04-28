package model;

import helpers.Utils;

public class IntegerValueGene extends ValueGene {
    /**
     * Initializes a new binary gene with random value in range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     */
    public IntegerValueGene(Integer lowerBound, Integer upperBound) {
        super(lowerBound, upperBound, Utils.getRandInteger(lowerBound, upperBound));
    }

    public IntegerValueGene(Integer lowerBound, Integer upperBound, Integer value){
        super(lowerBound, upperBound, value);
    }
    @Override
    public Gene mutate() {
        value = Utils.getRandInteger((Integer) lowerBound, (Integer) upperBound);
        return this;
    }
}