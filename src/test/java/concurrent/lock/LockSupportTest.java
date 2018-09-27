package concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    static int count = 0;

    @Test
    public void testLockSupport(){
        Thread thread1 = new Thread(()->{
            count++;
            System.out.println(count);
            LockSupport.park();
        });

        Thread thread2 = new Thread(()->{
            count++;
            System.out.println(count);
            LockSupport.park();
        });

        thread1.start();
        thread2.start();

        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);

        System.out.println(count);
    }

}
