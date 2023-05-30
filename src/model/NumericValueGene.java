package model;

import exceptions.ValueOutOfBoundsException;

public abstract class NumericValueGene <T extends Comparable> extends ValueGene<T>{
    /***
     * Inclusive lower bound
     */
    T lowerBound;
    /***
     * Exclusive upper bound
     */
    T upperBound;


    public NumericValueGene(T lowerBound, T upperBound, T value) {
        if(value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) >= 0)
            throw new ValueOutOfBoundsException("Passed value is out of defined bounds");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = value;
    }
}
