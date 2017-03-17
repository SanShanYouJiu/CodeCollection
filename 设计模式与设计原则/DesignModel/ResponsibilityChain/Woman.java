package DesignMode.DesignModel.ResponsibilityChain;

/**
 * Created by han on 2017/2/28.
 */
public class Woman implements  IWomen {
    private int type=0;

    private String request = "";

    public Woman(int type, String _request) {
        this.type = type;
        switch (this.type) {
            case 1:this.request="女儿的请求是:"+_request;
                break;
            case 2:
                this.request = "妻子的请求是:" + _request;
                break;
            case 3:
                this.request = "母亲的请求是:" + _request;
                break;
        }
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public String getRequest() {
        return this.request;
    }
}
