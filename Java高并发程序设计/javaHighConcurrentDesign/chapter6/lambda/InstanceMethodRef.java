package javaHighConcurrentDesign.chapter6.lambda;


import java.util.ArrayList;
import java.util.List;

/**
 */
public class InstanceMethodRef {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "billy" + Integer.toString(i)));
        }
        users.stream().map(User::getName).forEach(System.out::println);
    }
}
