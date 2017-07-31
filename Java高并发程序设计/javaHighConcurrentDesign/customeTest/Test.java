package javaHighConcurrentDesign.customeTest;

/**
SkipList是排序的
 */
public class Test {

    public static void main(String[] args) {
        //int i = 1;
        //if ( (i*=2) ==0){
        //    System.out.println("无"+i);
        //}
        //System.out.println(i);

        //Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
        //for (int i = 0; i < 30; i++) {
        //    map.put(i, i);
        //}
        //for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        //    System.out.println(entry.getKey());
        //}

        int v1=1073741827;
        int v2=1473741575;
        int ave=(v1+v2)/2;
        System.out.println(ave);

    }
}
