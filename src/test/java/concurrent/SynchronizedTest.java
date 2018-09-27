package concurrent;

import org.junit.jupiter.api.Test;

public class SynchronizedTest {

    private static volatile Integer count = 0;

    @Test
    public void testCount(){
        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                count++;
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                count++;
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

        System.out.println(count);
    }

    @Test
    public void testCountWithSynchronized(){
        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                synchronized (SynchronizedTest.class) {
                    count++;
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                synchronized (SynchronizedTest.class) {
                    count++;
                }
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

        System.out.println(count);
    }

}
