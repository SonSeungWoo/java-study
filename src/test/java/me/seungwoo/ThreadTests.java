package me.seungwoo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Leo.
 * User: ssw
 * Date: 2019-06-07
 * Time: 15:25
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ThreadTests {

    @Test
    public void executorServiceTest() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            futures.add(executor.submit(() -> {
                Thread.sleep(3000);
                return Thread.currentThread().getName() + ", index=" + index + ", ended at " + new Date();
            }));
        }
        for (Future<String> eachFuture: futures) {
            String result = eachFuture.get();
            System.out.println("Thread result: " + result);
        }
        executor.shutdown();
    }

    @Test
    public void streamParallelTest01() throws ExecutionException, InterruptedException {
        ForkJoinPool myPool = new ForkJoinPool(7);
        myPool.submit(() -> {
            IntStream.range(0, 10).parallel().forEach(index -> {
                System.out.println("Starting " + Thread.currentThread().getName() + ", index=" + index + ", " + new Date());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            });
        }).get();
    }

    @Test
    public void streamParallelTest02() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        IntStream.range(0, 10).parallel().forEach(index -> {
            System.out.println("Starting " + Thread.currentThread().getName()
                    + ", index=" + index + ", " + new Date());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) { }
        });
    }


    @Test
    public void giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal()
            throws InterruptedException, ExecutionException {

        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed()
                .collect(Collectors.toList());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        long actualTotal = customThreadPool.submit(
                () -> aList.parallelStream().reduce(0L, Long::sum)).get();

        assertEquals((lastNum + firstNum) * lastNum / 2, actualTotal);
    }

}
