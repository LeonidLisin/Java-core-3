package Vessel;

import Path.StoreBay;
import Path.Way;
import Storage.Storage;

import java.util.ArrayList;

public class Vessel implements Runnable {
    private final static int CAPACITY = 500;
    private final static boolean TO_STORE = true, FROM_STORE = false;
    public int goods, storageIndex = 0, readyStorageCount = 0;
    private static int storageIndexInit = 0;
    private static Way way;
    private String name;
    public static ArrayList<Storage> storages = new ArrayList<>();


    public Vessel(String name, Way way, ArrayList<Storage> storages){
        this.name = name;
        this.way = way;
        goods = 0;
        this.storages = storages;
        storageIndexInit = storageIndexInit < storages.size() - 1 ? storageIndexInit + 1 : 0;
        storageIndex = storageIndexInit++;
    }

    private void load(int goodsToLoad, Storage storage) throws InterruptedException {
        storage.shipGoods(goodsToLoad, this);
    }

    private void move(Way way, boolean direction) throws InterruptedException {
        if (direction == TO_STORE) {
            for (int i = 0; i < way.getWay().size(); i++)
                way.getWay().get(i).transit(this, direction);
        }
        if (direction == FROM_STORE) {
            for (int i = way.getWay().size()-1; i >= 0; i--)
                way.getWay().get(i).transit(this, direction);
        }
    }

    public boolean tryToRedirect() throws InterruptedException {
        int c = 0;
        while (true) {
            storageIndex = storageIndex < storages.size() - 1 ? storageIndex + 1 : 0;
                c++;
            if (c == storages.size()) return false;
            if (!storages.get(storageIndex).ready){
                Way wayToOtherStore = new Way(new StoreBay());
                System.out.println(name + " передвигается к другому складу ----->");
                move(wayToOtherStore, TO_STORE);
                load(CAPACITY, storages.get(storageIndex));
                return true;
            }
        }
    }

    public String getName(){
        return name;
    }

    public void run(){
        while (true) {
            readyStorageCount = 0;
            while (true) {
                if (storages.get(storageIndex).ready){
                    storageIndex = storageIndex < storages.size() - 1 ? storageIndex + 1 : 0;
                    readyStorageCount++;}
                if (readyStorageCount == storages.size()) break;
                if (!storages.get(storageIndex).ready) break;
            }
            if (readyStorageCount == storages.size()) break;
            try {
                move(way, TO_STORE);
                load(CAPACITY, storages.get(storageIndex));
                move(way, FROM_STORE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
