package DesignMode.DesignModel.Prototype;

import java.util.Random;

/**
 * Created by han on 2017/2/24.
 */
public class Client {

    private static  int MAX_COUNT=6;

    public static void main(String[] args) throws CloneNotSupportedException {
        int i=0;
        Mail mail = new Mail(new AdvTemplate());
        mail.setTail("XX银行版权所有");
        while (i < MAX_COUNT) {
            Mail cloneMail =  mail.clone();
            cloneMail.setApplication(getRandString(5) + "先生");
            cloneMail.setReceiver(getRandString(5) + "@" + getRandString(8) + ".com");
            //然后发送邮件
            sendMail(cloneMail);
            i++;
        }
    }

   //发送邮件
    public static void sendMail(Mail mail) {
        System.out.println("标题:" + mail.getSubject() + "\t 收件人"
                + mail.getReceiver() + "\t...发送成功");
    }


  //获得指定长度的随机字符串
    public static String getRandString(int maxLength) {
        String source = "abhsdkasghfaskfklalhadskfaskjdlankashfihasasdfghjklzxcvbnmwertyuiop";
        StringBuffer sb = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < maxLength; i++) {
            sb.append(source.charAt(rand.nextInt(source.length())));
        }
        return sb.toString();
    }
}
