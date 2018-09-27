package concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueTest {

    @Test
    public void testBlockingQueue(){
        int SIZE = 2;
        int N = 10;
        ArrayBlockingQueue<Integer> integers = new ArrayBlockingQueue<>(SIZE);

        Thread writeThread = new Thread(()->{
            for(int i = 0; i < N; i++){
                try {
                    integers.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        writeThread.setName("WriteThread");

        Thread readThread = new Thread(()->{
            for(int i = 0; i < N; i++){
                try {
                    Integer value = integers.take();
                    System.out.println(value);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        readThread.start();
        writeThread.start();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
