package concurrent.collection.unsafe;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    static Map<Integer,String> map = new HashMap<>();

    @Test
    public void testHashMap(){
        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10000; i+=2){
                map.put(i,Integer.toString(i));
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i = 1; i < 10000; i+=2){
                map.put(i,Integer.toString(i));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(map.size());
    }
}
