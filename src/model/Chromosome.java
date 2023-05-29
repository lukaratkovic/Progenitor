package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chromosome<T extends Gene> {
    private List<T> genes;
    private T geneInstance;
    private final int length;


    public Chromosome(List<T> genes){
        this.genes = genes;
        this.length = this.genes.size();
    }

    public Chromosome(int length, T geneInstance) {
        this.length = length;
        this.geneInstance = geneInstance;
        this.genes = (List<T>) IntStream.range(0, length)
                .mapToObj(i -> geneInstance.getInstance())
                .collect(Collectors.toList());
    }

    public Chromosome clone(){
        return new Chromosome(genes);
    }

    public Chromosome<T> getRandom(){
        return new Chromosome(length, geneInstance);
    }

    public int getLength() {
        return length;
    }

    public List<T> getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + genes +
                '}';
    }
}
