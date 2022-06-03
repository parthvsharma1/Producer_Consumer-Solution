
// this code is for multiple consumer and producers
// I have used wait and notify method to implement this
import java.util.ArrayList;

import java.util.List;

public class TestConsumerProducer2 {
    public static int buffercapacity=2;
    int x=2;
    int x2=100;

    public static void main(String[] args) {
        List<Integer> goods = new ArrayList<>();
        Consumer consumer11 = new Consumer(goods,"consumer A",2);
        Consumer consumer22 = new Consumer(goods,"consumer B",5);
        Producer producer11 = new Producer(goods,"producer A",4);
        Producer producer22 = new Producer(goods,"producer B",5);

        Thread consumer1 = new Thread(consumer11);
        Thread consumer2 = new Thread(consumer22);

        Thread producer1 = new Thread(producer11);
        Thread producer2 = new Thread(producer22);

        //starting the threads
        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();

        try { // ensure all threads have completed before proceeding
            consumer1.join();
            consumer2.join();
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---- THANK YOU ----");
    }

}

class Producer implements Runnable {
    List<Integer> goods;
    protected static int maxSize = TestConsumerProducer2.buffercapacity;
    String threadName;
    int prodCapacity;// this
    public Producer(List<Integer> goods,String threadName,int prodCapacity) {
        this.goods = goods;
        this.threadName=threadName;
        this.prodCapacity=prodCapacity;

    }

    public String getName(){
        return this.threadName;
    }
    public void produce(int i) {
        synchronized (goods) { // only 1 thread can access the goods array at a time

            while (goods.size() >= maxSize) {
                try {
                    System.out.println("meanwhile "+this.getName() +" is waiting for elements to be cleared ");

                    goods.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(this.getName() + ">>> produced  " + i);
            goods.add(i);
            goods.notifyAll();// release the lock and allow any other thread to proceed
        }
    }

    @Override
    public void run() {

        for (int i = 0; i < this.prodCapacity; i++) {
            produce(i);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

 class Consumer implements Runnable {
    List<Integer> goods;
    String threadName;
    int consumerReq;// amount of items this consumer wants to use
    public Consumer(List<Integer> goods,String threadName,int consumerReq) {
        this.goods = goods;
        this.threadName=threadName;
        this.consumerReq=consumerReq;
    }

    public void consume() {
        synchronized (goods) {// only 1 thread can access the goods array at a time

            while (goods.size() <= 0) {
                try {
                    System.out.println("meanwhile "+this.getName() +" is waiting for elements to join");
                    goods.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(this.getName() + " >>>> consumed  " + goods.remove(0));
            goods.notifyAll();
        }
    }

    public String getName(){
        return this.threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < consumerReq; i++) {
            consume();

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
