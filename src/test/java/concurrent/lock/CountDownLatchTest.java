package concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    @Test
    public void testCountDownLatch(){
        final int N = 10;
        CountDownLatch end = new CountDownLatch(N);

        ExecutorService executorService = Executors.newFixedThreadPool(N);
        for(int i = 0; i < N; i++) {
            executorService.submit(new Thread(()->{
                try {
                    Thread.sleep(new Random().nextInt(N) * 1000);
                    System.out.println(Thread.currentThread().getName() + " Done!");
                    end.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        try {
            end.await();
            System.out.println("All done!");
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
