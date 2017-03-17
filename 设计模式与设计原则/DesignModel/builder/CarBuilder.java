package DesignMode.DesignModel.builder;

import java.util.ArrayList;

/**
 * Created by han on 2017/2/21.
 */
public abstract class CarBuilder {
    public abstract void setSequence(ArrayList<String> sequence);

    public abstract  CarModel getCarModel();
}
