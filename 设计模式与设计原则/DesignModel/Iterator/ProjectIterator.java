package DesignMode.DesignModel.Iterator;

import java.util.ArrayList;

/**项目迭代器
 * Created by han on 2017/3/3.
 */
public class ProjectIterator implements IProjectIterator {
    private ArrayList<IProject> projectList = new ArrayList<IProject>();
    private int currentItem = 0;

    public ProjectIterator(ArrayList<IProject> projectList) {
        this.projectList = projectList;
    }

    /**
     * 判断是否还有元素 必须实现
     * @return
     */
    @Override
    public boolean hasNext() {
        boolean b = true;
        if (this.currentItem >=projectList.size() || this.projectList.get(this.currentItem) == null) {
            b = false;
        }
        return b;
    }

    /**
     * 取下一个值
     * @return
     */
    @Override
    public IProject next() {
        return  this.projectList.get(this.currentItem++);
    }

    public  void remove(){
        //暂时没有使用到
    }
}
