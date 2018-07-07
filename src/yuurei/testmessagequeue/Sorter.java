package yuurei.testmessagequeue;

import java.util.concurrent.BlockingQueue;

public class Sorter implements Runnable {
    private final BlockingQueue<Item> queue;
    private final Consumer[] consumers;

    public Sorter(BlockingQueue<Item> queue, Consumer[] consumers){
        this.queue = queue;
        this.consumers = consumers;
    }

    synchronized private void sort() throws InterruptedException {
        Item task = queue.take();
        consumers[task.getGroupID()-1].addTask(task);
    }

    @Override
    public void run() {
        while (Consumer.counter < Main.numberOfMessages) {
            try {
                sort();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
