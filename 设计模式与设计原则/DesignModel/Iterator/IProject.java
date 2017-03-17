package DesignMode.DesignModel.Iterator;

/**项目信息接口
 * Created by han on 2017/3/3.
 */
public interface IProject {
    public void add(String name, int num, int cost);

    public String getProjectInfo();

    public  IProjectIterator iterator();

}
