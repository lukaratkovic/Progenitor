package model;

import exceptions.ValueOutOfBoundsException;

public abstract class ValueGene <T extends Comparable> extends Gene<T>{

    /***
     * Inclusive lower bound
     */
    T lowerBound;
    /***
     * Exclusive upper bound
     */
    T upperBound;

    public ValueGene(T lowerBound, T upperBound, T value){
        if(value.compareTo(lowerBound) < 0 || value.compareTo(upperBound) >= 0)
            throw new ValueOutOfBoundsException("Passed value is out of defined bounds");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.value = value;
    }
}
