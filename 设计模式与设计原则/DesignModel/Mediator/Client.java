package DesignMode.DesignModel.Mediator;

/**
 * Created by han on 2017/2/26.
 */
public class Client {

    public static void main(String[] args) {
        AbstractMediator mediator = new Mediator();
        System.out.println("--------采购人员采购电脑-----------");
        Purchase purchase = new Purchase(mediator);
        purchase.buyIBMcomputer(100);
        //销售人员销售电脑
        System.out.println("\n----------- 销售人员销售电脑----------");
        Sale sale = new Sale(mediator);
        sale.sellIBMComputer(1);
        System.out.println("\n -----------库房管理人员清库人员------------");
        Stock stock = new Stock(mediator);
        stock.clearStock();
    }
}
