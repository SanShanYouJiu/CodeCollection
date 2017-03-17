package DesignMode.PrincipleDesign.LoD;

import java.util.List;

/**
 * Created by han on 2017/1/16.
 */
public class GroupLeader {

    private List<Girl> listGirls;

    public GroupLeader(List<Girl> listGirls) {
        this.listGirls = listGirls;
    }

    public void countGirls(){
        System.out.println("女生数量是："+listGirls.size());
    }
}
