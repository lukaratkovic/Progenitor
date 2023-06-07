package model;

/**
 * Base implementation of a gene.
 * @param <T> Generic type for the value of the gene
 */
public abstract class Gene<T> {
    /**
     * Value of the gene.
     */
    public T value;

    /**
     * Abstract method for mutating the value of a Gene.
     * @return mutated Gene
     */
    public abstract Gene mutate();


    /**
     * Abstract method for returning a new instance of a gene with equal parameters.
     * @return
     */
    public abstract Gene getInstance();

    /**
     * Abstract method for creating a new instance of a gene with equal parameters and value.
     * @return
     */
    public abstract Gene clone();

    /**
     * Getter for the value of a Gene.
     * @return value of the gene
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns a string representation of a Gene, containing its value
     * @return a string representation of a Gene
     */
    @Override
    public String toString() {
        return "Gene{" +
                "value=" + value +
                '}';
    }
}
