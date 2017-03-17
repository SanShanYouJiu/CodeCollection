package DesignMode.DesignModel.Facade;

/**
 * Created by han on 2017/3/7.
 */
public class LetterProcessImpl implements ILetterProcess {
    @Override
    public void writeContext(String context) {
        System.out.println("填写信的内容...."+context);
    }

    @Override
    public void fillEnvelope(String address) {
        System.out.println("填写收件人地址。。。" + address);
    }

    @Override
    public void letterInotoEnvelope() {
        System.out.println("把信放到信封中");
    }

    @Override
    public void sendLetter() {
        System.out.println("邮递信件");
    }
}
