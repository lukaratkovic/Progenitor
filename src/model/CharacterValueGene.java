package model;

import exceptions.ValueOutOfBoundsException;
import helpers.Rand;

import java.util.List;

/**
 * Implementation of a Gene with Character values
 */
public class CharacterValueGene extends ValueGene<Character> {
    /**
     * List of allowed values
     */
    List<Character> allowedValues;

    /**
     * Constructor with random value
     * @param allowedValues List containing allowed Characters
     */
    public CharacterValueGene(List<Character> allowedValues) {
        int index = Rand.getRandInteger(0, allowedValues.size());
        this.value = allowedValues.get(index);
        this.allowedValues = allowedValues;
    }

    /**
     * Constructor with given value
     * @param allowedValues List containing allowed characters
     * @param value a
     */
    public CharacterValueGene(List<Character> allowedValues, Character value){
        this.allowedValues = allowedValues;
        if(!allowedValues.contains(value))
            throw new ValueOutOfBoundsException("Passed value is not in list of allowed values.");
        this.value = value;
    }

    /**
     * Set the value of the Gene to a new random value from allowed values
     * @return instance of CharacterValueGene
     */
    @Override
    public Gene mutate() {
        int index = Rand.getRandInteger(0, allowedValues.size());
        value = allowedValues.get(index);
        return this;
    }

    /**
     * Creates a new instance of a character value gene with same allowed values
     * @return new instance of CharacterValueGene
     */
    @Override
    public Gene getInstance() {
        return new CharacterValueGene(allowedValues);
    }

    /**
     * Creates a new instance of a character value gene with same allowed values and concrete value
     * @return new instance of CharacterValueGene
     */
    @Override
    public Gene clone() {
        return new CharacterValueGene(allowedValues, value);
    }
}
