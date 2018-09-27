package thread;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ThreadTest {

    @Test
    public void testRun(){
        Thread thread = new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        });
        thread.start();
    }

    @Test
    public void testInterrupt(){
        Thread thread = new Thread(()->{
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Exit...");
                    break;
                }
                System.out.println(LocalDateTime.now());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(3000);
            thread.interrupt();
            System.out.println("???");
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWaitAndNotify(){
        Boolean flag = false;

        Thread thread = new Thread(()->{
            while(true){
                System.out.println(LocalDateTime.now());
                try {
                    Thread.sleep(1000);
                    synchronized (flag) {
                        flag.wait();
                        System.out.println("Exit...");
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(3000);
            System.out.println("???");
            synchronized (flag) {
                flag.notify();
                Thread.sleep(1000);
                System.out.println("wait a second...");
            }
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
