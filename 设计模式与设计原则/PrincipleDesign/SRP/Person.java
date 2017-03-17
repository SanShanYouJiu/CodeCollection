package DesignMode.PrincipleDesign.SRP;

/**
 * Created by han on 2017/1/14.
 */
public class Person   implements iPhone  {

    @Override
    public void call() {
        System.out.println("call");
    }

    @Override
    public void close() {
        System.out.println("close phone");
    }
}
