package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.CharacterValueGene;
import model.Chromosome;
import model.Progenitor;

import java.util.List;

public class CharacterExample {
    public static void main(String[] args) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !";
        List<Character> charList = characters.chars().mapToObj(ch->(char)ch).toList();

        CharacterValueGene templateGene = new CharacterValueGene(charList);
        Chromosome c = new Chromosome(52, templateGene);

        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .endCondition(EndCondition.TARGET_FITNESS)
                .targetFitness(52)
                .crossoverMethod(CrossoverMethod.ONE_POINT)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.RANK)
                .elitismCount(2)
                .fitness(CharacterExample::fitness)
                .build();

        progenitor.run();
    }

    public static Double fitness(Chromosome c){
        double fitness = 0;
        String target = "Progenitor is a Java library for genetic algorithms!";
        List<Character> chars = target.chars().mapToObj(ch -> (char) ch).toList();
        for (int i = 0; i < c.getLength(); i++) {
            if(c.getGenes().get(i).getValue() == chars.get(i)) fitness++;
        }
        return fitness;
    }
}
