package examples.nnoptimization;

import model.Chromosome;
import model.Gene;
import model.IntegerValueGene;

import java.util.List;

public class HyperChromosome extends Chromosome {
    public HyperChromosome(List<Gene> genes) {
        super(genes);
    }

    public Integer getBatchSize(){
        return (Integer) getGenes().get(0).getValue();
    }

    public Double getLearnRate(){
        return (Double) getGenes().get(1).getValue();
    }

    public Double getMomentum(){
        return (Double) getGenes().get(2).getValue();
    }

    @Override
    public Chromosome getRandom() {
        Chromosome newChromosome = this.clone();
        newChromosome.getGenes().forEach(Gene::mutate);
        return newChromosome;
    }
}
