package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class CancelDeletePageCommand extends Command {
    //撤销一个删除的命令
    @Override
    public void execute() {
        super.pg.rollBack();
    }
}
