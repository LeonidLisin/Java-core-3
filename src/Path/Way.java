package Path;

import java.util.ArrayList;
import java.util.Arrays;

public class Way {
    private ArrayList<Path> fullPath;
    public Way(Path...pahtes){
        fullPath = new ArrayList<>(Arrays.asList(pahtes));
    }

    public ArrayList<Path> getWay(){
        return fullPath;
    }
}
