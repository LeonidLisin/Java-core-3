import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Tester.start(ClassUnderTest1.class);
        Tester.start(ClassUnderTest2.class);
        Tester.start(ClassUnderTest3.class);
    }
}
