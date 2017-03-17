package DesignMode.DesignModel.Proxy;

/**
 * Created by han on 2017/2/22.
 */
public class GamePlayerProxy implements IGamePlayer {
    private IGamePlayer gamePlayer = null;


    public GamePlayerProxy(IGamePlayer _gamePlayer) {
        this.gamePlayer=_gamePlayer;
    }

    @Override
    public void login(String user, String password) {
      this.gamePlayer.login(user,password);
    }

    @Override
    public void killBoss() {
        this.gamePlayer.killBoss();
    }

    @Override
    public void upgrade() {
        this.gamePlayer.upgrade();
    }
}
