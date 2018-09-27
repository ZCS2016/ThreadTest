package concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    static int count = 0;

    @Test
    public void testReadWriteLock(){
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10; i++) {
                try {
                    readLock.lock();
                    count++;
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            }
        });
        thread1.setName("Thread1");

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10; i++) {
                try {
                    readLock.lock();
                    count++;
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
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
