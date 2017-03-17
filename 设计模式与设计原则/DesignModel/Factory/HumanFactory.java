package DesignMode.DesignModel.Factory;

/**
 * Created by han on 2017/1/19.
 */
public class HumanFactory extends AbstractHumanFactory {
    @Override
    public <T extends Human> T crewateHuman(Class<T> c) {
     Human human=null;
        try {
            human = (T) Class.forName(c.getName()).newInstance();
        }catch (Exception e){
            System.out.println("人种生成错误");
        }
        return (T)human;
    }
}
