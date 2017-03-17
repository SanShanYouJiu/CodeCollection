package DesignMode.DesignModel.Facade;

/**
 * Created by han on 2017/3/7.
 */
public interface ILetterProcess {
    public void writeContext(String context);

    public void fillEnvelope(String address);

    public void letterInotoEnvelope();

    public void sendLetter();

}
