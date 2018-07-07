package yuurei.testmessagequeue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Item> queue;
    private volatile static int counter = 0;

    public Producer(BlockingQueue<Item> q){
        this.queue = q;
    }

    synchronized private void produce(){
        int groupID = 1 + Main.random.nextInt(4);
        try {
            queue.put(new Item(++counter, groupID));
        } catch (InterruptedException e){
            System.out.println("Producer thread interrupted");
        }
    }
    @Override
    public void run(){
        while (counter < Main.numberOfMessages){
            produce();
        }
    }
}
