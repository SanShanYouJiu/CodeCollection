package DesignMode.NewDesign.Specification.Specification;

/**
 * Created by han on 2017/3/29.
 */
public class NotSpecification  extends CompositeSpecification{
    private ISpecification spec;


    public NotSpecification(ISpecification spec) {
        this.spec = spec;
    }

    @Override
    public boolean isSatisfiedBy(Object candidate) {
        return !spec.isSatisfiedBy(candidate);
    }

}
