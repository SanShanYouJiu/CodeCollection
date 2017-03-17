package DesignMode.DesignModel.Mediator.Mediator2;

/**
 * Created by han on 2017/2/26.
 */
public abstract class Colleague {
    protected  Mediator mediator;

    public Colleague(Mediator _mediator) {
        this.mediator = _mediator;
    }

}
