public class ClassUnderTest2 {

    @Test(priority = 5)
    public void method1(){
        System.out.println("m1");
    }

    @AfterSuite
    public void afterer(){
        System.out.println("after");
    }

    @Test(priority = 5)
    public void method2(){
        System.out.println("m2");
    }

}
