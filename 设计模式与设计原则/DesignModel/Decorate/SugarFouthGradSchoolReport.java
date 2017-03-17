package DesignMode.DesignModel.Decorate;

/**
 * Created by han on 2017/2/28.
 */
public class SugarFouthGradSchoolReport extends FouthGradeSchoolReport {

    private void reportHighScore() {
        System.out.println("这次考试语文最高75 数学78 自然是80");
    }

    private void  reportSort() {
        System.out.println("我是排名36名");
    }

    @Override
    public void report() {
        this.reportHighScore();
        super.report();
        this.reportSort();
    }
}
