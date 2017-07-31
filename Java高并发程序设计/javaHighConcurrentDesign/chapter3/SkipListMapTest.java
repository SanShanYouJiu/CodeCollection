package javaHighConcurrentDesign.chapter3;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class SkipListMapTest {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (int i = 30; i > 0; ) {
            map.put(i, i);
            i--;
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
