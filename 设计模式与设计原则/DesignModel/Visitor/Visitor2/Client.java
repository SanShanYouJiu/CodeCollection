package DesignMode.DesignModel.Visitor.Visitor2;

/**
 * Created by han on 2017/3/9.
 */
public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //获得元素对象
            Element e1 = ObjectStruture.createElement();
            //接受访问者访问
            e1.accept(new Visitor());
        }
    }
}
