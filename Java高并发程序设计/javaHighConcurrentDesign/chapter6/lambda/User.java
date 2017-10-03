package javaHighConcurrentDesign.chapter6.lambda;

import java.io.Serializable;

/**
 */
public class User implements Serializable {

    private static final long serialVersionUID = -5172415443855774192L;

    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public   String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
