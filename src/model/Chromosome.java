package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chromosome {
    private List<Gene> genes;
    private Gene geneInstance;
    private final int length;


    public Chromosome(List<Gene> genes){
        this.genes = genes;
        this.length = this.genes.size();
    }

    public Chromosome(int length, Gene geneInstance) {
        this.length = length;
        this.geneInstance = geneInstance;
        this.genes = IntStream.range(0, length)
                .mapToObj(i -> geneInstance.getInstance())
                .collect(Collectors.toList());
    }

    public Chromosome clone(){
        return new Chromosome(genes);
    }

    public Chromosome getRandom(){
        return new Chromosome(length, geneInstance);
    }

    public int getLength() {
        return length;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + genes +
                '}';
    }
}
