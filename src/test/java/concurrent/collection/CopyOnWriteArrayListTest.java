package concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    static CopyOnWriteArrayList<Integer> integers = new CopyOnWriteArrayList<>();

    @Test
    public void testCopyOnWriteArrayList(){

        Thread readThread = new Thread(()->{
            int initSize = 0;
            int currentSize = 0;

            for(int i = 0; i < 10000; i++){
                try {
                    currentSize = integers.size();
                    if(currentSize > initSize){
                        System.out.println(Thread.currentThread().getName() + " : " + integers);
                        initSize = currentSize;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        readThread.setName("ReadThread");

        Thread writeThread = new Thread(()->{
            for(int i = 0; i < 100; i++){
                try {
                    integers.add(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        writeThread.setName("WriteThread");

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
