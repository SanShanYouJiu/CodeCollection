package DesignMode.DesignModel.Iterator;


/**
 * Created by han on 2017/3/3.
 */
public class Boss {
    public static void main(String[] args) {
        IProject project = new Project();
        project.add("星球大战项目", 10, 10000);
        project.add("扭转时空项目", 100, 100000);
        project.add("超人改造计算", 1000, 1000000);
        for (int i = 0; i <104 ; i++) {
            project.add("第" + i + "个项目", i * 5, i * 1000000);
        }
        IProjectIterator projectIterator = project.iterator();
        while (projectIterator.hasNext()) {
            IProject p = (IProject) projectIterator.next();
            System.out.println(p.getProjectInfo());
        }
    }
}
