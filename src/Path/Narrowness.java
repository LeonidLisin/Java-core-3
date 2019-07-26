package Path;
import Vessel.*;

import java.util.concurrent.Semaphore;

public class Narrowness extends Path {
    private int NARROWNESS_BANDWITH = 2;
    private Semaphore semaphore;
    public Narrowness(){
        semaphore = new Semaphore(NARROWNESS_BANDWITH);
    }
    public void transit(Vessel vessel, boolean direction) throws InterruptedException {
        System.out.println(vessel.getName() + " приблизилось к узкости");
        semaphore.acquire();
        Thread.sleep(100);
        System.out.println(vessel.getName() + " миновало узкость");
        semaphore.release();
    }
}
