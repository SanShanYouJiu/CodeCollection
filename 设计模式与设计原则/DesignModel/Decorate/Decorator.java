package DesignMode.DesignModel.Decorate;

/**
 * Created by han on 2017/2/28.
 */
public abstract class Decorator extends SchoolReport {
    private SchoolReport sr;

    public Decorator(SchoolReport sr) {
        this.sr=sr;
    }

    public void report(){
        this.sr.report();
    }

    public void sign(String  name) {
        this.sr.sign(name);
    }
}
