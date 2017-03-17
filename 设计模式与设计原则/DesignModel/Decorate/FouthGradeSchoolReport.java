package DesignMode.DesignModel.Decorate;

/**
 * Created by han on 2017/2/28.
 */
public class FouthGradeSchoolReport extends SchoolReport {
    @Override
    public void report() {
        System.out.println("尊敬的XXX家长");
        System.out.println("............");
        System.out.println("语文 62 数学62 体育98 自然 63");
        System.out.println("............");
        System.out.println("    家长签名：");
    }


    @Override
    public void sign(String name) {
        System.out.println("家长签名："+name);
    }
}
