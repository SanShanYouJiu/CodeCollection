package DesignMode.PrincipleDesign.LoD.LoD2;

/**
 * Created by han on 2017/1/16.
 */
public class Client {

    public static void main(String[] args) {
        InstallSoftware invoker = new InstallSoftware();
        invoker.installWizard(new Wizard());

    }
}
