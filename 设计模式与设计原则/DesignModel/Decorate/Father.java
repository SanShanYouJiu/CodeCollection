package DesignMode.DesignModel.Decorate;

/**
 * Created by han on 2017/2/28.
 */
public class Father {

    public static void main(String[] args) {
        SchoolReport sr = new FouthGradeSchoolReport();
        sr = new HighSchoolDecorator(sr);
        sr = new SortDecorator(sr);
        sr.report();
        sr.sign("老三");

    }
}
