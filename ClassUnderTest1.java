public class ClassUnderTest1 {

    @Test(priority = 1)
    public void method1(){
        System.out.println("m1");
    }

    @Test(priority = 5)
    public void method2(){
        System.out.println("m2");
    }

    @Test(priority = 2)
    public void method3(){
        System.out.println("m3");
    }

    @BeforeSuite
    public void beforer(){
        System.out.println("before");
    }

    @AfterSuite
    public void afterer(){
        System.out.println("after");
    }

}
