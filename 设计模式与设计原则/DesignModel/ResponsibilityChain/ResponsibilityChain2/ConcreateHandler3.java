package DesignMode.DesignModel.ResponsibilityChain.ResponsibilityChain2;

/**
 * Created by han on 2017/2/28.
 */
public class ConcreateHandler3 extends Handler {
    @Override
    protected Level getHandLevel() {
        //设置自己的处理级别
        return null;
    }

    @Override
    protected Response echo(Request request) {
        //完成处理逻辑
        return null;
    }
}
