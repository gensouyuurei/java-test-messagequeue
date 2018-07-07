package yuurei.testmessagequeue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<Item> group;
    public volatile static int counter = 0;

    public Consumer(){
        group = new LinkedBlockingQueue();
    }

    public void addTask(Item task){
        try {
            group.put(task);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    synchronized private void consume() throws InterruptedException{

        Item toConsume = group.take();
        ++counter;
        System.out.println(
                "Item consumed: itemID=0" + toConsume.getItemID() +
                        " groupID=" + toConsume.getGroupID() +
                        " time elapsed: " + (System.currentTimeMillis() - Main.startTime)
        );
    }
    @Override
    public void run() {
        while (counter < Main.numberOfMessages){
            try {
                consume();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
