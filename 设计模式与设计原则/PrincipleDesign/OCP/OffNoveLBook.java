package DesignMode.PrincipleDesign.OCP;

/**
 * Created by han on 2017/1/18.
 */
public class OffNoveLBook  extends NovelBook{

    public OffNoveLBook(String name, int price, String author) {
        super(name, price, author);
    }


    @Override
    public int getPrice() {
        int selfPrice = super.getPrice();
        int offPrice =0;
        if (selfPrice >4000){
            offPrice=selfPrice *90/100;
        }else {
            offPrice =selfPrice*80/100;
        }
        return offPrice;
    }
}
