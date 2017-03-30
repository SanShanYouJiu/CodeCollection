package DesignMode.NewDesign.Specification;

/**规格书
 * Created by han on 2017/3/29.
 */
public interface IUserSpecification {

    public boolean isSatisfiedBy(User user);

    public IUserSpecification and(IUserSpecification spec);

    public IUserSpecification or(IUserSpecification spec);

    public IUserSpecification not();
}
