package DesignMode.NewDesign.Specification;

import java.util.ArrayList;

/**
 * Created by han on 2017/3/29.
 */
public class UserProvider implements IUserProvider {
    //用户列表
    private ArrayList<User> userList;

    //传递用户列表
    public UserProvider(ArrayList<User> userList) {
        this.userList = userList;
    }


    //根据指定的规格书查找用户
    @Override
    public ArrayList<User> findUser(IUserSpecification userSpec) {
        ArrayList<User> result = new ArrayList<User>();
        for (User u : userList) {
            if (userSpec.isSatisfiedBy(u)) {
                result.add(u);
            }
        }
        return result;
    }

}
