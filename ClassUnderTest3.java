public class ClassUnderTest3 {

    @Test(priority = 1)
    public void method1(){
        System.out.println("m1");
    }

    @Test(priority = 1)
    public void method2(){
        System.out.println("m2");
    }

    @BeforeSuite
    public void beforer1(){
        System.out.println("before");
    }

    @BeforeSuite
    public void beforer2(){
        System.out.println("before2");
    }

}

