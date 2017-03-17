package DesignMode.DesignModel.ResponsibilityChain.ResponsibilityChain2;

/**
 * Created by han on 2017/2/28.
 */
public class ConcreateHandler1 extends Handler {
    @Override
    protected Level getHandLevel() {
        //设置自己的处理基本
        return null;
    }

    @Override
    protected Response echo(Request request) {
        //完成逻辑处理
        return null;
    }
}
