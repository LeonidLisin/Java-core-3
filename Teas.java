import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Teas {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(4);
        Semaphore semaphore = new Semaphore(1);

        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println("n1");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }
        });

        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println("n2");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }
        });

        es.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("n3");
            }
        });

        es.shutdown();


    }
}
