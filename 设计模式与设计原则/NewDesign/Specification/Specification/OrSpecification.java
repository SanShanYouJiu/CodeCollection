package DesignMode.NewDesign.Specification.Specification;

/**
 * Created by han on 2017/3/29.
 */
public class OrSpecification extends CompositeSpecification {

    private ISpecification left;
    private ISpecification right;


    public OrSpecification(ISpecification left, ISpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(Object candidate) {
        return left.isSatisfiedBy(candidate)||right.isSatisfiedBy(candidate);
    }


}
