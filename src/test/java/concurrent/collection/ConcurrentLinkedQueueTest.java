package concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
    static ConcurrentLinkedQueue<Integer> linkedQueue = new ConcurrentLinkedQueue<>();

    @Test
    public void testConcurrentLinkedQueue(){
        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                linkedQueue.add(i);
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                linkedQueue.add(i);
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

        System.out.println(linkedQueue.size());
    }

}
