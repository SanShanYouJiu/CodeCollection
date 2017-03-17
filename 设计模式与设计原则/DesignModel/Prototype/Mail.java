package DesignMode.DesignModel.Prototype;

/**
 * Created by han on 2017/2/23.
 */
public class Mail implements Cloneable{

    private String receiver;

    private String subject;

    private String application;

    private String context;

    private String tail;

    public Mail(AdvTemplate advTemplate) {
        this.context = advTemplate.getAdvContext();
        this.subject = advTemplate.getAdvSubject();
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getApplication() {
        return application;
    }

    public String getContext() {
        return context;
    }

    public String getTail() {
        return tail;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }


    @Override
    protected Mail clone() throws CloneNotSupportedException {
        Mail mail = null;
        try {
            mail = (Mail) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return mail;
    }
}
