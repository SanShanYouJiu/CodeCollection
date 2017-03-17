package DesignMode.DesignModel.ResponsibilityChain.ResponsibilityChain2;

/**一般会有一个封装类对责任模式进行封装 也就是替代Client类 直接返回链中的第一个处理器 具体链的设置不需要
 * 高层次模块关系 这样 更简化高层次模块的调用 减少模块间的耦合 提高系统的灵活性
 * Created by han on 2017/2/28.
 */
public class Client {

    public static void main(String[] args) {
        //声明所有的处理结点
        Handler handler1 = new ConcreateHandler1();
        Handler handler2 = new ConcreateHandler2();
        Handler handler3 = new ConcreateHandler3();
        //设置链中的阶段处理
        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);
        //提交请求 返回结果
        Response response = handler1.handleMessage(new Request());
    }
}

