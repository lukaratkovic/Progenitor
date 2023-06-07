package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Representation of a chromosome containing Genes
 */
public class Chromosome {
    /**
     * List of Gene objects
     */
    private List<Gene> genes;

    /**
     * Instance of a Gene object used for creating initial population
     */
    private Gene geneInstance;

    /**
     * Number of Genes in Chromosome
     */
    private final int length;


    /**
     * Constructor with defined list of Genes
     * @param genes list of Chromosome Genes
     */
    public Chromosome(List<Gene> genes){
        this.genes = genes;
        this.length = this.genes.size();
    }

    /**
     * Constructor for template Chromosome
     * @param length size of Chromosome
     * @param geneInstance instance of Gene class the Chromosome will contain
     */
    public Chromosome(int length, Gene geneInstance) {
        this.length = length;
        this.geneInstance = geneInstance;
        this.genes = IntStream.range(0, length)
                .mapToObj(i -> geneInstance.getInstance())
                .collect(Collectors.toList());
    }

    /**
     * Creates a new instance of a chromosome with same genes
     * @return new instance of Chromosome
     */
    public Chromosome clone(){
        return new Chromosome(genes);
    }

    /**
     * Creates a new Chromosome with same parameters and random value
     * @return new instance of Chromosome
     */
    public Chromosome getRandom(){
        return new Chromosome(length, geneInstance);
    }

    /**
     * Getter for the length parameter
     * @return number of Genes in Chromosome
     */
    public int getLength() {
        return length;
    }

    /**
     * Getter for the Genes of Chromosome
     * @return List of Genes
     */
    public List<Gene> getGenes() {
        return genes;
    }

    /**
     * Returns a string representation of a Chromosome, containing the values of its Genes
     * @return a string representation of a Chromosome
     */
    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + genes +
                '}';
    }
}
