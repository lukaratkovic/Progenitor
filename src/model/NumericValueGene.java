package model;

import exceptions.ValueOutOfBoundsException;

/**
 * Base implementation of a numeric value gene with user-defined bounds
 * @param <T> Generic Comparable type for the value of the gene
 */
public abstract class NumericValueGene <T extends Comparable> extends ValueGene<T>{
    /***
     * Inclusive lower bound
     */
    T lowerBound;
    /***
     * Exclusive upper bound
     */
    T upperBound;


    /**
     * Constructor with allowed range and value
     * @param lowerBound lowest allowed value, inclusive
     * @param upperBound highest allowed value, exclusive
     * @param value value within given bounds
     */
    public NumericValueGene(T lowerBound, T upperBound, T value) {
        if(value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) >= 0)
            throw new ValueOutOfBoundsException("Passed value is out of defined bounds");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = value;
    }
}
