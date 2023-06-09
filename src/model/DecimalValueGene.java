package model;

import helpers.Rand;

public class DecimalValueGene extends NumericValueGene<Double>{

    /**
     * Constructor with random value in range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     */
    public DecimalValueGene(Double lowerBound, Double upperBound){
        super(lowerBound, upperBound, Rand.getRandDouble(lowerBound,upperBound));
    }

    /**
     * Constructor with a given value and range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     * @param value value within given bounds
     */
    public DecimalValueGene(Double lowerBound, Double upperBound, Double value){
        super(lowerBound, upperBound, value);
    }

    /**
     * Set the value of the Gene to a new random value within defined bounds
     * @return instance of DecimalValueGene
     */
    @Override
    public Gene mutate() {
        value = Rand.getRandDouble(lowerBound, upperBound);
        return this;
    }

    /**
     * Creates a new instance of a decimal value gene with same bounds
     * @return new instance of DecimalValueGene
     */
    @Override
    public Gene getInstance() {
        return new DecimalValueGene(lowerBound, upperBound);
    }

    /**
     * Creates a new instance of a decimal value gene with same bounds and value
     * @return new instance of DecimalValueGene
     */
    @Override
    public Gene clone() {
        return new DecimalValueGene(lowerBound, upperBound, value);
    }
}
