package DesignMode.NewDesign.Specification;

import java.util.ArrayList;

/**
 * Created by han on 2017/3/29.
 */
public interface IUserProvider {

    public ArrayList<User> findUser(IUserSpecification userSpec);
}
