package DesignMode.DesignModel.builder;

/**
 * Created by han on 2017/2/20.
 */
public class BmwModel extends CarModel {
    @Override
    protected void start() {
        System.out.println("宝马车开启");
    }

    @Override
    protected void stop() {
        System.out.println("宝马车停下来了");
    }

    @Override
    protected void alarm() {
        System.out.println("宝马车的响铃");
    }

    @Override
    protected void engineBoom() {
        System.out.println("宝马车的引擎声音");
    }
}
