package Storage;

public class StoreOfClothes extends Storage {
    public StoreOfClothes(){
        CAPACITY = 8000;
        goods = CAPACITY;
        ENTIRE_GOODS_TO_LOAD = 2700;
        goodsWeight = 500;
        ready = false;
        name = " одежды";
        name2 = " одеждой";
    }
}
