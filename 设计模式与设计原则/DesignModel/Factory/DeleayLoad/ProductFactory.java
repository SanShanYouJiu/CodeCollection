package DesignMode.DesignModel.Factory.DeleayLoad;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han on 2017/1/19.
 */
public class ProductFactory {
    private static final Map<String, Product> prMap = new HashMap<String,Product>();
    public  static  synchronized  Product createProduct(String type)throws Exception{
        Product product=null;
        if (prMap.containsKey(type)) {
            product = prMap.get(type);
        }else {
            if (type.equals("Product1")) {
                product = new ConcreteProduct1();
            }else {
                product = new ConcreteProduct2();
            }
            prMap.put(type, product);
        }
        return  product;
    }
}
