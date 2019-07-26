package Path;

import Vessel.Vessel;

public class StoreBay extends Path {
    public void transit(Vessel vessel, boolean direction){
        String text = direction ? " причалило к складу" : " отчалило от склада";
        String text2 = vessel.storages.get(vessel.storageIndex).getName();
        System.out.println(vessel.getName() + text + text2);
    }
}
