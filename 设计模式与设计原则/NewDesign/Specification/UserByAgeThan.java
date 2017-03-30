package DesignMode.NewDesign.Specification;

/**
 * Created by han on 2017/3/29.
 */
public class UserByAgeThan extends CompositeSpecification {
    private int age;

    public UserByAgeThan(int age) {
        this.age = age;
    }


    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getAge() >age;
    }

}
