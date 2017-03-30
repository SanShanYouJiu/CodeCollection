package DesignMode.NewDesign.Specification;

import java.util.ArrayList;

/**
 * Created by han on 2017/3/29.
 */
public class Client {
    public static void main(String[] args) {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("苏大", 3));
        userList.add(new User("牛二", 8));
        userList.add(new User("张三",10));
        userList.add(new User("李四",15));
        userList.add(new User("王五",18));
        userList.add(new User("赵六",20));
        userList.add(new User("马七",25));
        userList.add(new User("杨八",30));
        userList.add(new User("侯九",35));
        userList.add(new User("布十", 40));
        userList.add(new User("国庆", 40));

        IUserProvider userProvider = new UserProvider(userList);
        System.out.println("年龄大于20岁的用户");
        IUserSpecification userSpec = new UserByAgeThan(20);
        IUserSpecification userSpec2 = new UserByNameLike("%国庆%");

        for (User u : userProvider.findUser(userSpec.and(userSpec2))) {
            System.out.println(u);
        }
    }
}
