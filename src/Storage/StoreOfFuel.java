package Storage;

public class StoreOfFuel extends Storage {
    public StoreOfFuel(){
        CAPACITY = 9000;
        goods = CAPACITY;
        ENTIRE_GOODS_TO_LOAD = 8500;
        goodsWeight = 0;
        ready = false;
        name = " топлива";
        name2 = " топливом";
    }
}
