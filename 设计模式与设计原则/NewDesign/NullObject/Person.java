package DesignMode.NewDesign.NullObject;

/**
 * Created by han on 2017/3/30.
 */
public class Person {
    //听到动物叫声
    public void hear(Animal animal) {
        if (animal !=new NullAnimal()) {
          //这里采用一个空对象进行比较
        }
    }
}
