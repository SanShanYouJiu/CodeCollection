package DesignMode.DesignModel.Command;


/**
 * Created by han on 2017/2/27.
 */
public abstract  class Command {
    protected RequirementGroup rg = new RequirementGroup();
    protected PageGroup pg = new PageGroup();
    protected CodeGroup cg = new CodeGroup();

    public abstract  void execute();
}
