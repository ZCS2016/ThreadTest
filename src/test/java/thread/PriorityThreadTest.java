package thread;

import org.junit.jupiter.api.Test;

public class PriorityThreadTest {

    @Test
    public void testPriority(){
        Thread low = new Thread(()->{
            for(int i = 0; i < 10; i++) {
                synchronized (PriorityThreadTest.class) {
                    System.out.println("Low:"+i);
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        low.setPriority(Thread.MIN_PRIORITY);

        Thread high = new Thread(()->{
            for(int i = 0; i < 10; i++) {
                synchronized (PriorityThreadTest.class) {
                    System.out.println("High:"+i);
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        high.start();

        try {
            low.join();
            high.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
