import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Tester {

    public static void start(Class classUnderTest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        LinkedList<Method> methodsList = new LinkedList<Method>();
        Method[] methods = classUnderTest.getDeclaredMethods();
        Object obj = classUnderTest.newInstance();
        int beforeCounter = 0, afterCounter = 0;

        System.out.println("Testings for " + classUnderTest.getName() + ":");

        for (Method m: methods)
            if(m.isAnnotationPresent(Test.class)) {
                if(m.getAnnotation(Test.class).priority() < 0)
                    throw new RuntimeException("Negative method priority");
                methodsList.add(m);
            }
        sortByPriority(methodsList);

        for (Method m: methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                beforeCounter++;
                if (beforeCounter > 1)
                    throw new RuntimeException("BeforeSuite annotation can be handled once only");
                methodsList.addFirst(m);
            }

            if (m.isAnnotationPresent(AfterSuite.class)) {
                afterCounter++;
                if (afterCounter > 1)
                    throw new RuntimeException("AfterSuite annotation can be handled once only");
                methodsList.addLast(m);
            }
        }

        for (Method m: methodsList)
                m.invoke(obj);

        System.out.println();
    }

    private static void sortByPriority(LinkedList<Method> methods){
        Collections.sort(methods, new Comparator<Method>() {
            public int compare(Method m1, Method m2) {
                if(m1.getAnnotation(Test.class).priority() >
                        m2.getAnnotation(Test.class).priority())
                    return 1;
                if(m1.getAnnotation(Test.class).priority() <
                        m2.getAnnotation(Test.class).priority())
                    return -1;
                return 1;
            }
        });
    }
}
