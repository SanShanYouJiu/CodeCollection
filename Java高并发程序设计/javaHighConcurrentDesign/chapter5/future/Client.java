package javaHighConcurrentDesign.chapter5.future;

/**
 */
public class Client {

    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        new Thread(){
            @Override
            public void run() {
                //RealData的构造很慢
                //所以在单独的线程中进行
                RealData realData = new RealData(queryStr);
                future.setRealData(realData);
            }
        }.start();
     return  future; //FutureData会立即被返回
    }

    public static void main(String[] args) {
        Client client = new Client();
        //这里会立即返回 因为得到的是FutureData而不是RealData
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            //这里可以一个sleep代替了对其他业务逻辑的处理
            //在处理这些业务逻辑的过程中 RealData被创建 从而充分利用了等待时间
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        System.out.println("数据=" + data.getResult());
    }
}
