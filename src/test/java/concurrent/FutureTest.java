package concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class FutureTest {

    class LongTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            Thread.sleep(3000);
            return Thread.currentThread().getName() + " done!";
        }
    }

    @Test
    public void testFuture(){
        FutureTask<String> futureTask = new FutureTask<>(new LongTask());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);
        System.out.println("Submit!");

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
