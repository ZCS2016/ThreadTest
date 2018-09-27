package thread;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();

    class ParseDate implements Runnable{

        @Override
        public void run() {
            if(sdf.get() == null){
                sdf.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            String nowStr = sdf.get().format(new Date());
            System.out.println(Thread.currentThread().getName() + " : " + nowStr);
        }

    }

    @Test
    public void testThreadLocal(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 100; i++){
            executorService.execute(new ParseDate());
        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
