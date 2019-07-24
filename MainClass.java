import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {
    private static final int CARS_AMOUNT = 4;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CountDownLatch cd = new CountDownLatch(CARS_AMOUNT), cd2 = new CountDownLatch(1);
        ExecutorService es = Executors.newFixedThreadPool(CARS_AMOUNT);
        Race race = new Race(new Road(60), new Tunnel(CARS_AMOUNT/2), new Road(40));
        Car[] cars = new Car[CARS_AMOUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cd, cd2);
        }
        for (int i = 0; i < CARS_AMOUNT ; i++)
            es.execute(cars[i]);

        cd.await();
        es.shutdown();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        cd2.countDown();
        while(!es.isTerminated());
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}


