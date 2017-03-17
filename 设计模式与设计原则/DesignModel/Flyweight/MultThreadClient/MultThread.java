package DesignMode.DesignModel.Flyweight.MultThreadClient;

/**
 * Created by han on 2017/3/13.
 */
public class MultThread extends Thread {
    private SignInfo signInfo;

    public MultThread(SignInfo signInfo) {
        this.signInfo = signInfo;
    }

    @Override
    public void run() {
        if (!signInfo.getId().equals(signInfo.getLocation())) {
            System.out.println("编号"+signInfo.getId());
            System.out.println("地址" + signInfo.getLocation());
            System.out.println("线程不安全");
        }
    }
}
