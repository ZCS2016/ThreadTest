package thread;

import org.junit.jupiter.api.Test;

public class DaemonThreadTest {

    @Test
    public void testDaemon(){
        Thread thread = new Thread(()->{
            while(true) {
                System.out.println("Hello???");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
