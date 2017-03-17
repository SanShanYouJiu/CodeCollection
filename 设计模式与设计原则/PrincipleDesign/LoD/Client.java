package DesignMode.PrincipleDesign.LoD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 2017/1/16.
 */
public class Client {

    public static void main(String[] args) {
        List<Girl> listGirls = new ArrayList<Girl>();
        for (int i=0;i<20;i++) {
            listGirls.add(new Girl());
        }
        Teacher teacher = new Teacher();
        teacher.commond(new GroupLeader(listGirls));
    }
}
