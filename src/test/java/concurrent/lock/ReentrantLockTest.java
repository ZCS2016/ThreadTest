package concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static int count = 0;

    @Test
    public void testReentrantLock(){
        ReentrantLock lock = new ReentrantLock();

        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                lock.lock();
                try {
                    count++;
                }finally {
                    lock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10000; i++){
                lock.lock();
                try {
                    count++;
                }finally {
                    lock.unlock();
                }
            }
        });

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

    @Test
    public void testInterrupt(){
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(()->{
            try {
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+" get Lock1!");
                Thread.currentThread().sleep(1000);
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+" get Lock2!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock1!");
                }
                if(lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock2!");
                }
                System.out.println(Thread.currentThread().getName()+" Exit!");
            }
        });
        thread1.setName("Thread1");

        Thread thread2 = new Thread(()->{
            try {
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+" get Lock2!");
                Thread.currentThread().sleep(1000);
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+" get Lock1!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock1!");
                }
                if(lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock2!");
                }
                System.out.println(Thread.currentThread().getName()+" Exit!");
            }
        });
        thread2.setName("Thread2");

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(3000);
            thread1.interrupt();

            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTryLock(){
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(()->{
            try {
                if(lock1.tryLock(3, TimeUnit.SECONDS)){
                    System.out.println(Thread.currentThread().getName()+" get Lock1!");
                    Thread.currentThread().sleep(5000);
                }else{
                    System.out.println(Thread.currentThread().getName()+" Failed to get Lock1 !");
                }
                if(lock2.tryLock(3, TimeUnit.SECONDS)){
                    System.out.println(Thread.currentThread().getName()+" get Lock2!");
                }else{
                    System.out.println(Thread.currentThread().getName()+" Failed to get Lock2 !");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock1!");
                }
                if(lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock2!");
                }
                System.out.println(Thread.currentThread().getName()+" Exit!");
            }
        });
        thread1.setName("Thread1");

        Thread thread2 = new Thread(()->{
            try {
                if(lock2.tryLock(3, TimeUnit.SECONDS)){
                    System.out.println(Thread.currentThread().getName()+" get Lock2!");
                    Thread.currentThread().sleep(5000);
                }else{
                    System.out.println(Thread.currentThread().getName()+" Failed to get Lock2 !");
                }
                if(lock1.tryLock(3, TimeUnit.SECONDS)){
                    System.out.println(Thread.currentThread().getName()+" get Lock1!");
                }else{
                    System.out.println(Thread.currentThread().getName()+" Failed to get Lock1 !");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if(lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock1!");
                }
                if(lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName()+" release Lock2!");
                }
                System.out.println(Thread.currentThread().getName()+" Exit!");
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
    }

    @Test
    public void testCondition(){
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(()->{
            for(int i = 0; i < 10; i++){
                lock.lock();
                try {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " : " + count);
                    Thread.sleep(1000);
                    condition.signalAll();
                    condition.awaitUninterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread1.setName("Thread1");

        Thread thread2 = new Thread(()->{
            for(int i = 0; i < 10; i++){
                lock.lock();
                try {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " : " + count);
                    Thread.sleep(1000);
                    condition.signalAll();
                    condition.awaitUninterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
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
