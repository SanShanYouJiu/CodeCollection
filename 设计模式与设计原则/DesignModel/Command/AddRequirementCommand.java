package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class AddRequirementCommand extends Command {
    @Override
    public void execute() {
        super.pg.find();
        super.rg.add();
        super.pg.add();
        super.cg.add();
        super.rg.plan();
    }

}
