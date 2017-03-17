package DesignMode.DesignModel.Composite;

import java.util.ArrayList;

/**
 * Created by han on 2017/3/4.
 */
public class Client {
    public static void main(String[] args) {
        Branch ceo = compositeCorpTree();
        System.out.println(ceo.getInfo());
        System.out.println(getTreeInfo(ceo));

    }

    /**把整个树整出来
     *
     * @return
     */
    public static  Branch compositeCorpTree(){
        Branch root = new Branch("王大麻子", "总经理", 10000);
        Branch developDep = new Branch("刘大瘸子", "研发部经理", 100000);
        Branch salesDep = new Branch("马二拐子", "销售部经理", 2000);
        Branch financeDep = new Branch("赵三驼子", "财务部经理", 3000);
        Branch firstDevGroup = new Branch("杨三乜斜", "开发组一组组长", 5000);
        Branch secondDevGroup = new Branch("吴大棒槌", "开发组二组组长", 6000);

        Leaf a = new Leaf("a", "开发人员", 2000);
        Leaf b = new Leaf("b", "开发人员", 2000);
        Leaf c = new Leaf("c", "开发人员", 2000);
        Leaf d = new Leaf("d", "开发人员", 2000);
        Leaf e = new Leaf("e", "开发人员", 2000);
        Leaf f = new Leaf("f", "开发人员", 2000);
        Leaf g = new Leaf("g", "开发人员", 2000);
        Leaf h = new Leaf("h", "销售人员", 5000);
        Leaf i = new Leaf("i", "销售人员", 4000);
        Leaf j = new Leaf("j", "财务人员", 5000);
        Leaf k = new Leaf("k", "CEO秘书", 8000);
        Leaf zhangLaoLiu = new Leaf("赵老六", "研发部副总", 20000);

        root.addSubordinate(developDep);
        root.addSubordinate(salesDep);
        root.addSubordinate(financeDep);

        developDep.addSubordinate(firstDevGroup);
        developDep.addSubordinate(secondDevGroup);
        developDep.addSubordinate(zhangLaoLiu);

        firstDevGroup.addSubordinate(a);
        firstDevGroup.addSubordinate(b);
        firstDevGroup.addSubordinate(c);
        secondDevGroup.addSubordinate(d);
        secondDevGroup.addSubordinate(e);
        secondDevGroup.addSubordinate(f);

        salesDep.addSubordinate(h);
        salesDep.addSubordinate(i);

        financeDep.addSubordinate(j);
        return root;
    }

    /**
     *遍历所有的树枝结点 打印出信息
     */
    private static String getTreeInfo(Branch root) {
        ArrayList<Corp> subordinateList = root.getSubordinateInfo();
        String info = "";
        for (Corp s : subordinateList) {
            if (s instanceof Leaf) {
                info = info + s.getInfo() + "\n";
            }else {
                info = info + s.getInfo() + "\n" + getTreeInfo((Branch) s);
            }
        }
        return info;
    }

    private static  String  getTreeInfoBack(Corp corp) {
        String info = "";
        info=info+"\n"+corp.getInfo();
//        Branch branch = (Branch) corp.getParent();
//         info = info + "\n " + branch.getInfo();
        Branch branch = (Branch) corp.getParent();
        String Branchinfo = getTreeInfo(branch);
        info = info +" \n" + Branchinfo;

        return info;
    }

}
