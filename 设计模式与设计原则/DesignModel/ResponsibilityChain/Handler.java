package DesignMode.DesignModel.ResponsibilityChain;

/**
 * Created by han on 2017/2/28.
 */
public abstract class Handler {
    public final static  int FATHER_LEVEL_REQUEST=1;
    public final  static  int HUSBAND_LEVEL_REQUEST=2;
    public final  static  int SON_LEVEL_REQUEST=2;
    private int level =0;
    private Handler nextHandler ;

    public Handler(int level) {
        this.level = level;
    }

    public final void HandleMessage(IWomen women) {
        if (women.getType() == this.level) {
            this.response(women);
        }else {
            if (this.nextHandler != null) {
                this.nextHandler.HandleMessage(women);
            }else {
                System.out.println("-------没地方指示 按不同意处理-----");
            }
        }
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void response(IWomen women);
}
