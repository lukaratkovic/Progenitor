package model;

public class Progenitor {
    public static class Builder{
        private int populationSize, maxGenerations, tournamentK, elitismCount;
        private double mutationLikelihood;

        public Builder(int populationSize){
            this.populationSize = populationSize;
        }
        
    }

    private Progenitor(){}
}
