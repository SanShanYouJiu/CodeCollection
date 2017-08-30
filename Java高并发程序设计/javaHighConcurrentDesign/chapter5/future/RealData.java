package javaHighConcurrentDesign.chapter5.future;

/**
 */
public class RealData implements Data {

    protected final String result;

    //RealData可能构造很慢 需要用户等待很久 这里使用Sleep模拟
    public RealData(String para) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        result = sb.toString();
    }

    public String getResult() {
        return result;
    }

}
