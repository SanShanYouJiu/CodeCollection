package DesignMode.DesignModel.builder;

/**
 * Created by han on 2017/2/20.
 */
public class BenzModel extends CarModel  {
    @Override
    protected void start() {
        System.out.println("奔驰车开启了");
    }

    @Override
    protected void stop() {
        System.out.println("奔驰车停止了");
    }

    @Override
    protected void alarm() {
        System.out.println("奔驰车打起了响铃");
    }

    @Override
    protected void engineBoom() {
        System.out.println("奔驰车开启了引擎");
    }
}
