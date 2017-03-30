package DesignMode.NewDesign.Specification;

/**
 * Created by han on 2017/3/29.
 */
public class UserByNameEqual extends CompositeSpecification {
    private String name;

    public UserByNameEqual(String name) {
        this.name = name;
    }


    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getName().equals(name);
    }
}
