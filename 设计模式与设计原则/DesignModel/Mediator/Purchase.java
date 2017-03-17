package DesignMode.DesignModel.Mediator;

/**
 * Created by han on 2017/2/26.
 */
public class Purchase extends  AbstractColleague{

    public Purchase(AbstractMediator _mediator) {
        super(_mediator);
    }

    public void buyIBMcomputer(int number) {
        super.mediator.execute("purchase.buy", number);
    }


    public void refuseBuyIBM() {
        System.out.println("不要采购IBM的电脑");
    }
}
