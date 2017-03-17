package DesignMode.DesignModel.Mediator;

import java.util.Random;

/**
 * Created by han on 2017/2/26.
 */
public class Sale extends AbstractColleague {

    public Sale(AbstractMediator _mediator) {
        super(_mediator);
    }

    public void sellIBMComputer(int number) {
        super.mediator.execute("sale.sell", number);
        System.out.println("销售IBM电脑" + number + "台");
    }

    public static void main(String[] args) {

    }

    public int getSaleStatus() {
        Random rand = new Random(System.currentTimeMillis());
        int saleStatus = rand.nextInt(100);
        System.out.println("IBM电脑的销售情况为" + saleStatus);
        return saleStatus;
    }


    public void offSale() {
        //库房有多少卖多少
        super.mediator.execute("sale.offsell");

    }
}
