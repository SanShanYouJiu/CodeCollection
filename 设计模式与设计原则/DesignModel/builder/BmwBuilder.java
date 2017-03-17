package DesignMode.DesignModel.builder;


import java.util.ArrayList;

/**
 * Created by han on 2017/2/21.
 */
public class BmwBuilder extends CarBuilder {

    private BmwModel bmw = new BmwModel();

    @Override
    public void setSequence(ArrayList<String> sequence) {
         this.bmw.setSequence(sequence);
    }


    @Override
    public CarModel getCarModel() {
        return bmw;
    }


}
