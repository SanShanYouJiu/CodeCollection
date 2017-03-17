package DesignMode.DesignModel.builder;

/**
 * Created by han on 2017/2/20.
 */
public class Client {

    public static void main(String[] args) {
//        ArrayList<String> sequence = new ArrayList<>();
//        sequence.add("engine boom");
//        sequence.add("start");
//        sequence.add("stop");
//
//        BenzBuilder benzBuilder = new BenzBuilder();
//        benzBuilder.setSequence(sequence);
//        BenzModel benz = (BenzModel) benzBuilder.getCarModel();
//        benz.run();
//
//        System.out.println("----------------------------");
//
//        BmwBuilder bmwBuilder = new BmwBuilder();
//        bmwBuilder.setSequence(sequence);
//        BmwModel bmwModel = (BmwModel) bmwBuilder.getCarModel();
//        bmwModel.run();


        Director director = new Director();
        int cycleIndex =1;
        for (int i = 0; i <cycleIndex ; i++) {
            director.getABenzModel().run();
        }
        System.out.println("--------------------");
        for (int i = 0; i <cycleIndex ; i++) {
            director.getBBenzModel().run();
        }
        System.out.println("--------------------");
        for (int i = 0; i <cycleIndex ; i++) {
            director.getCBenzModel().run();
        }
    }
}
