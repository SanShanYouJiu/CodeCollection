package javaHighConcurrentDesign.chapter7.PSO;

/**
 */
public class PBestMsg {
    final PsoValue value;

    public PBestMsg(PsoValue value) {
        this.value = value;
    }

    public PsoValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
