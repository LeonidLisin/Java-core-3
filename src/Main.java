import Path.*;
import Storage.*;
import Vessel.*;

import java.util.ArrayList;
import java.util.concurrent.*;


public class Main {
    private static final int VESSELS_NUMBER = 5;
    public static void main(String[] args) {
        ArrayList<Storage> storages = new ArrayList<>();
        storages.add(new StoreOfClothes());
        storages.add(new StoreOfFuel());
        storages.add(new StoreOfMeals());
        ExecutorService es = Executors.newFixedThreadPool(VESSELS_NUMBER);
        Way directWay = new Way(new PortBay(), new Narrowness(), new StoreBay());
        for (int i = 0; i < VESSELS_NUMBER ; i++) {
            es.execute(new Vessel("Судно-" + (i+1), directWay, storages));
        }
        es.shutdown();
    }
}
