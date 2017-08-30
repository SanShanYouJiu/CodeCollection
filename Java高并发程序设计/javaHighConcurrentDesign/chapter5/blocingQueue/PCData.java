package javaHighConcurrentDesign.chapter5.blocingQueue;

/**
 */
public class PCData {//任务相关的数据

    private final int intData;  //数据

    public PCData(int intData) {
        this.intData = intData;
    }

    public int getData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data:"+intData;
    }
}
