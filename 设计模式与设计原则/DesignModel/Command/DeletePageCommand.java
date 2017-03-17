package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class DeletePageCommand extends Command {
    @Override
    public void execute() {
        super.pg.find();
        super.rg.delete();
        super.rg.plan();
        super.pg.delete();
        super.cg.delete();
    }
}
