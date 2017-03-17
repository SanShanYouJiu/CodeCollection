package DesignMode.DesignModel.Factory.CommonFacory;

/**
 * Created by han on 2017/1/19.
 */
public class Client {

    public static void main(String[] args) {
        Creator creator =new ConcreteCreator();
        Product product=creator.createProduct(ConcreteProduct1.class);
        /* 继续业务处理
        * */
    }
}
