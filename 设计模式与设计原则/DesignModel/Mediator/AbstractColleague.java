package DesignMode.DesignModel.Mediator;

/**
 * Created by han on 2017/2/26.
 */
public abstract class AbstractColleague {
    protected  AbstractMediator mediator;

    public AbstractColleague(AbstractMediator _mediator) {
        this.mediator=_mediator;
    }

}
