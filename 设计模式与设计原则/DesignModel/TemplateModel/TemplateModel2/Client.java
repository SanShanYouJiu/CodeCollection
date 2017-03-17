package DesignMode.DesignModel.TemplateModel.TemplateModel2;

/**
 * Created by han on 2017/1/20.
 */
public class Client {

    public static void main(String[] args) {
        AbstractClass class1 = new ConcreteClass1();
        AbstractClass class2 = new ConcreteClass2();
        class1.templateMethod();
        class2.templateMethod();
    }
}
