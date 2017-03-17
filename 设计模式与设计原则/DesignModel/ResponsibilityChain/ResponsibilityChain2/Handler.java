package DesignMode.DesignModel.ResponsibilityChain.ResponsibilityChain2;

/**
 * Created by han on 2017/2/28.
 */
public abstract class Handler {
    private Handler nextHandler = null;

 //每个处理器都必须对请求做出处理
    public final Response handleMessage(Request request) {
        Response response =null;
        //判断是否自己的处理基本
        if (this.getHandLevel().equals(request.getRequestLevel())) {
            response = this.echo(request);
        }else {//不属于自己的处理级别
            //判断是否有一下个处理级别
            if (this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            }else {
                //没有适当的处理者  业务自行处理
            }
        }
        return response;
    }

    //设置下一个处理器是谁
    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    //每个处理器都有一个自己的处理级别
    protected abstract Level getHandLevel();

    //每个处理器都必须实现处理任务
    protected abstract Response echo(Request request);
}
