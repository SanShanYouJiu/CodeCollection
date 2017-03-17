package DesignMode.DesignModel.Facade;

/**
 * Created by han on 2017/3/7.
 */
public class Client {
    public static void main(String[] args) {
        ModenPostOffice hellRoadPostOffice = new ModenPostOffice();
        String address = "Happy Road No .666 God Province ,Heaven";
        String context = "Hello ,Its me  do you konw who i am? Im your old lover Id like to..";
        hellRoadPostOffice.sendLetter(context, address);

    }
}
