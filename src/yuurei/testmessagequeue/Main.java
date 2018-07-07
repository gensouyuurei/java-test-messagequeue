package yuurei.testmessagequeue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    static final Random random = new Random(System.currentTimeMillis());
    volatile static long startTime;
    static final int numberOfMessages = 100;

    public static void main(String[] args){
        startTime = System.currentTimeMillis();
        ExecutorService execProducers = Executors.newFixedThreadPool(4);
        ExecutorService execSorter = Executors.newSingleThreadExecutor();
        ExecutorService execConsumers = Executors.newFixedThreadPool(4);

        BlockingQueue<Item> itemQueue = new LinkedBlockingQueue<>();

        for (int i = 0; i < 4; i++){
            execProducers.execute(new Producer(itemQueue));
        }

        Consumer[] consumers = new Consumer[4];
        for (int i = 0; i < 4; i++){
            consumers[i] = new Consumer();
        }

        Sorter sorter = new Sorter(itemQueue, consumers);
        execSorter.execute(sorter);

        for (int i = 0; i < 4; i++){
            execConsumers.execute(consumers[i]);
        }
        /*
        Thread[] consThreads = new Thread[4];
        for (int i = 0; i < 4; i++){
            consThreads[i] = new Thread(consumers[i]);
        }
        startTime = System.currentTimeMillis();
        for (Thread thread: consThreads) {
            thread.start();
        }
        */
        execConsumers.shutdown();
        execProducers.shutdown();
        execSorter.shutdown();
    }
}
