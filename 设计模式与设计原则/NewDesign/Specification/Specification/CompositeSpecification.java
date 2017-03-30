package DesignMode.NewDesign.Specification.Specification;

/**
 * Created by han on 2017/3/29.
 */
public abstract class CompositeSpecification implements ISpecification {

    @Override
    public abstract boolean isSatisfiedBy(Object candidate) ;

    @Override
    public ISpecification and(ISpecification spec) {
        return new AndSpecification(this,spec);
    }

    @Override
    public ISpecification or(ISpecification spec) {
        return new OrSpecification(this,spec);
    }

    @Override
    public ISpecification not() {
        return new NotSpecification(this);
    }


}
