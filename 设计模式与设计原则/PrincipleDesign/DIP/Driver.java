package DesignMode.PrincipleDesign.DIP;

/**
 * Created by han on 2017/1/15.
 */
public class Driver implements iDriver {


    @Override
    public void Driver(ICar car) {
         car.run();
    }
}
