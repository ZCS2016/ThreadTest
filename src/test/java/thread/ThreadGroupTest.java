package thread;

import org.junit.jupiter.api.Test;

public class ThreadGroupTest {

    @Test
    public void testGroup(){
        ThreadGroup threadGroup = new ThreadGroup("ThreadGroup");
        Thread thread1 = new Thread(threadGroup,()->{
            System.out.println(Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName());
        },"T1");
        Thread thread2 = new Thread(threadGroup,()->{
            System.out.println(Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName());
        },"T2");

        thread1.start();
        thread2.start();

        System.out.println("ActivCount:" + threadGroup.activeCount());
        threadGroup.list();
    }

}
