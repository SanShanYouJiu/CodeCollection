package DesignMode.NewDesign.Specification.Specification;

/**
 * Created by han on 2017/3/29.
 */
public interface ISpecification {
    public  boolean isSatisfiedBy(Object candidate);

    public ISpecification and(ISpecification spec);

    public ISpecification or(ISpecification spec);

    public ISpecification not();

}
