package DesignMode.NewDesign.Specification.Specification;

import java.util.ArrayList;

/**
 * Created by han on 2017/3/30.
 */
public class Client {


    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<Object>();
        ISpecification spec1 = new BizSpecification(new Object());
        ISpecification selc2 = new BizSpecification(new Object());
        for (Object obj : list) {
            if (spec1.and(selc2).isSatisfiedBy(obj)) {
                System.out.println(obj);
            }
        }
    }
}
