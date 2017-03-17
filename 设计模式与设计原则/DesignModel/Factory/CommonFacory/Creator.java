package DesignMode.DesignModel.Factory.CommonFacory;

/**
 * Created by han on 2017/1/19.
 */
public abstract  class Creator {
    /**
     * 创建一个产品对象 其输入参数类型可以自己设置
     * 通常为String Enum,Class 等 当然也可以为空
     */
    public abstract <T extends Product> T createProduct(Class<T> c);
}
