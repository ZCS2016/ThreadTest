package concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    @Test
    public void testSemaphore(){
        Semaphore semaphore = new Semaphore(2);

        final int N = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(N);
        for(int i=0; i < N; i++){
            executorService.submit(new Thread(()->{
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getId()+" done!");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
