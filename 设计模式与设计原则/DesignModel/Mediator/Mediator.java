package DesignMode.DesignModel.Mediator;

/**
 * Created by han on 2017/2/26.
 */
public class Mediator extends AbstractMediator {
    @Override
    public void execute(String str, Object... objects) {
        if (str.equals("purchase.buy")) {
            this.buyComputer((Integer) objects[0]);
        }else if (str.equals("sale.sell")) {
            this.sellComputer((Integer) objects[0]);
        }else  if(str.equals("sale.offsell")){
            this.offSell();
        }else if (str.equals("stock.clear")) {
            this.clearStock();
        }
    }

    private void buyComputer(int number){
        int saleStatus =super.sale.getSaleStatus();
        if (saleStatus > 80) {
            System.out.println("采购IBM电脑：" + number + "台");
            super.stock.increase(number);
        }else {//销售情况不好
            int buyNumber =number/2;
            System.out.println("采购IBM电脑" + buyNumber + "台");
        }
    }

    //销售电脑
    private void sellComputer(int number) {
        if (super.stock.getStockNumber() < number) {
            super.purchase.buyIBMcomputer(number);
        }
        super.stock.decrease(number);
    }

    //折价销售电脑
    private  void offSell() {
        System.out.println("折价销售IBM电脑" + stock.getStockNumber());
    }


    //清仓处理
    private void clearStock() {
        //要求清仓销售
        super.sale.offSale();
        //要求采购的人不要采购
        super.purchase.refuseBuyIBM();
    }


}
