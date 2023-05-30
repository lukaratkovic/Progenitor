package model;

import helpers.Rand;

import java.util.List;

public class CharacterValueGene extends ValueGene<Character> {
    List<Character> allowedValues;

    public CharacterValueGene(List<Character> allowedValues) {
        int index = Rand.getRandInteger(0, allowedValues.size());
        this.value = allowedValues.get(index);
        this.allowedValues = allowedValues;
    }

    public CharacterValueGene(List<Character> allowedValues, Character value){
        this.allowedValues = allowedValues;
        this.value = value;
    }

    @Override
    public Gene mutate() {
        int index = Rand.getRandInteger(0, allowedValues.size());
        value = allowedValues.get(index);
        return this;
    }

    @Override
    public Gene getInstance() {
        return new CharacterValueGene(allowedValues);
    }

    @Override
    public Gene clone() {
        return new CharacterValueGene(allowedValues, value);
    }
}
