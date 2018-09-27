package thread.threadpool;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {
    private class CountTask extends RecursiveTask<Long>{
        private static final int THRESHOLD = 100;
        private long start;
        private long end;

        public CountTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            boolean canCompute = (end - start) < THRESHOLD;
            if(canCompute){
                for(long i=start; i <= end; i++){
                    sum += i;
                }
            }else{
                long step = (start + end)/THRESHOLD;
                ArrayList<CountTask> subTasks = new ArrayList<>();
                long pos = start;
                for(int i = 0; i < THRESHOLD; i++){
                    long lastOne = pos + step;
                    if(lastOne > end){
                        lastOne = end;
                    }
                    CountTask subTask = new CountTask(pos,lastOne);
                    pos += step + 1;
                    subTasks.add(subTask);
                    subTask.fork();
                }
                for(CountTask countTask:subTasks){
                    sum += countTask.join();
                }
            }

            return sum;
        }
    }

    @Test
    public void testForkJoinPool(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(0, 1000);
        ForkJoinTask<Long> result = forkJoinPool.submit(countTask);
        try {
            Long sum = result.get();
            System.out.println(sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
