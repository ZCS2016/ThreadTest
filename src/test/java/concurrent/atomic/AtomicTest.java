package concurrent.atomic;

import org.junit.jupiter.api.Test;
import thread.ThreadLocalTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    static AtomicInteger count = new AtomicInteger();

    @Test
    public void testAtomicInteger(){
        count.set(0);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 10000; i++){
            executorService.execute(new Thread(()->{
                int currentCount = count.incrementAndGet();
                System.out.println(Thread.currentThread().getName() + " : " + currentCount);
            }));
        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(count.get());
    }

}
