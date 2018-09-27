package concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    static volatile int count = 0;

    @Test
    public void testCyclicBarrier(){
        int N = 2;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(N);

        Thread thread1 = new Thread(()->{
            try {
                cyclicBarrier.await();
                count++;
                System.out.println(Thread.currentThread().getName() + " : " + count);
                cyclicBarrier.await();
                count*=2;
                System.out.println(Thread.currentThread().getName() + " : " + count);
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread1.setName("Thread1");

        Thread thread2 = new Thread(()->{
            try {
                cyclicBarrier.await();
                count++;
                System.out.println(Thread.currentThread().getName() + " : " + count);
                cyclicBarrier.await();
                count*=2;
                System.out.println(Thread.currentThread().getName() + " : " + count);
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread2.setName("Thread2");

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
