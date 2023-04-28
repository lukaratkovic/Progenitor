package model;

public abstract class Gene<T> {
    public T value;
    public Gene(){}
    public abstract Gene mutate();

}
