package Path;
import Vessel.*;

public abstract class Path {

    public abstract void transit(Vessel vessel, boolean direction) throws InterruptedException;
}
