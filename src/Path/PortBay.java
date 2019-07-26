package Path;

import Vessel.Vessel;

public class PortBay extends Path {
    public void transit(Vessel vessel, boolean direction){
        String text = direction ? " отчалило от главного порта" : " причалило к главному порту";
        System.out.println(vessel.getName() + text);
        if(!direction){
            String text1 = vessel.storages.get(vessel.storageIndex).getName();
            String text2 = vessel.goods > 0 ? " выгрузило " + vessel.goods + " единиц" + text1 + " в главном порту" : " вернулось порожняком";
            System.out.println(vessel.getName() + text2);
            vessel.goods = 0; // выгрузили товар
        }

    }
}
