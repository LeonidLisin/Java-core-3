import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CAR_NUMBER;
    private static CountDownLatch cd, cd2;
    static {
        CAR_NUMBER = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    Car(Race race, int speed, CountDownLatch cd, CountDownLatch cd2) {
        this.race = race;
        this.speed = speed;
        this.cd = cd;
        this.cd2 = cd2;
        CAR_NUMBER++;
        this.name = "Участник #" + CAR_NUMBER;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        cd.countDown();
        try {
            cd.await();
            cd2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}