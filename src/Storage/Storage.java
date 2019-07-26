package Storage;
import Vessel.*;

public class Storage {
    int CAPACITY, ENTIRE_GOODS_TO_LOAD, goods, goodsWeight;
    public boolean ready;
    String name, name2;

    public synchronized void shipGoods(int goodsToLoad, Vessel vessel) throws InterruptedException{
            if (!ready) {
                goodsToLoad+=goodsWeight;
                int i;
                for (i = 100; i <= goodsToLoad; i += 100) {
                    this.goods -= 100;
                    vessel.goods += 100;
                    Thread.sleep(1000);
                    System.out.println("Склад" + name + " отгрузил на " + vessel.getName() + " " + i + " единиц, осталось на складе " + goods);
                    if (this.goods == CAPACITY - ENTIRE_GOODS_TO_LOAD) {
                        System.out.println("Склад" + name + " отгрузил весь необходимый товар!");
                        i += 100;
                        ready = true;
                        break;
                    }
                }

                System.out.println(vessel.getName() + " загружено" + name2 + " на " + (i - 100) + " единиц");
                System.out.println("На складе" + name + " осталось " + goods + " единиц");
            }
            else if (!vessel.tryToRedirect()) System.out.println(vessel.getName() + " возвращается порожняком");

    }
    public int getGoods() {return goods;}
    public String getName(){return name;}
}
