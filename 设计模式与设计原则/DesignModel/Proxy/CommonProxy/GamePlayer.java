package DesignMode.DesignModel.Proxy.CommonProxy;

/**
 * Created by han on 2017/2/22.
 */
public class GamePlayer implements IGamePlayer {

    private String name = "";


    public GamePlayer(IGamePlayer _gamePlayeer,String _name) throws Exception {
        if (_gamePlayeer==null)
            throw new Exception("不能创建真实角色");
        else
        this.name = _name;
    }

    @Override
    public void login(String user, String password) {
        System.out.println("登录名为 " + user + "的用户 " + this.name + "登录成功");
    }

    @Override
    public void killBoss() {
        System.out.println(this.name+"在打怪");
    }

    @Override
    public void upgrade() {
        System.out.println(name+"又升了一级");
    }
}
