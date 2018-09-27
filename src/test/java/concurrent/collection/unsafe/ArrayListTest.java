package concurrent.collection.unsafe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ArrayListTest {
    static ArrayList<Integer> arrayList = new ArrayList<>(10);

    @Test
    public void testArrayList(){
        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                arrayList.add(i);
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                arrayList.add(i);
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

        System.out.println(arrayList.size());
    }
}
