package model;

import exceptions.ValueOutOfBoundsException;
import helpers.Rand;

public class DecimalValueGene extends Gene<Double>{
    /***
     * Inclusive lower bound
     */
    Double lowerBound;
    /***
     * Exclusive upper bound
     */
    Double upperBound;

    /**
     * Constructor with random value in range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     */
    public DecimalValueGene(Double lowerBound, Double upperBound){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = Rand.getRandDouble(lowerBound, upperBound);
    }

    /**
     * Constructor with a given value and range
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     * @param value value within given bounds
     */
    public DecimalValueGene(Double lowerBound, Double upperBound, Double value){
        if(value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) >= 0)
            throw new ValueOutOfBoundsException("Passed value is out of defined bounds");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = value;
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
