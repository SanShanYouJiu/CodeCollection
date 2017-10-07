package javaHighConcurrentDesign.chapter7.PSO;

/**
 */
public final class GBestMsg {
    final PsoValue value;

    public GBestMsg(PsoValue value) {
        this.value = value;
    }

    public PsoValue getValue() {
        return value;
    }
}
