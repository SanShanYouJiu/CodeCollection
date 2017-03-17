package DesignMode.DesignModel.State;

/**
 * Created by han on 2017/3/11.
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setLiftState(new OpenningState());
        context.open();
        context.close();
        context.run();
        context.stop();
    }
}
