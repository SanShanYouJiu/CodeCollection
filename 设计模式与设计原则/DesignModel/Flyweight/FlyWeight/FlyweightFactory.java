package DesignMode.DesignModel.Flyweight.FlyWeight;

import java.util.HashMap;

/**
 * Created by han on 2017/3/17.
 */
public class FlyweightFactory {
    public static HashMap<String, Flyweight> flyweightHashMap = new HashMap<String, Flyweight>();

    public static Flyweight FactoryFlyWeight(String key) {
        Flyweight flyweight = null;
        if (flyweightHashMap.containsKey(key)) {
            flyweight = flyweightHashMap.get(key);
        } else {
                 flyweightHashMap.put(key, new ConcerteFlyweight("ceshi"));
             }
        return  flyweight;
    }
}
