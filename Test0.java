import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test0 {
    private UUT test1;

    @Before
    public void init(){
        test1 = new UUT();
    }

    @Test()
    public void afterFourTest1() {
        Assert.assertArrayEquals(
                test1.afterFour(new int[]{1, 2, 4, 41, 2, 3, 43, 1, 7}),
                new int[]{41, 2, 3, 43, 1, 7});
    }

    @Test()
    public void afterFourTest2() {
        Assert.assertArrayEquals(
                test1.afterFour(new int[]{1, 2, 4, 41, 2, 3, 4, 1, 7}),
                new int[]{1, 7});
    }

    @Test(expected = RuntimeException.class)
    public void afterFourTest3() {
        Assert.assertArrayEquals(
                test1.afterFour(new int[]{}),
                new int[]{});
    }

    @Test(expected = RuntimeException.class)
    public void afterFourTest4() {
        Assert.assertArrayEquals(
                test1.afterFour(new int[]{1, 2, 0, 41, 2, 3, 0, 1, 7}),
                new int[]{});
    }

    @Test()
    public void afterFourTest5() {
        Assert.assertArrayEquals(
                test1.afterFour(new int[]{4}),
                new int[]{});
    }

    @Test()
    public void checkOneFourTest1() {
        Assert.assertTrue(test1.checkOneFour(new int[]{1, 2, 0, 41, 2, 3, 0, 1, 7}));
    }

    @Test()
    public void checkOneFourTest2() {
        Assert.assertFalse(test1.checkOneFour(new int[]{8, 2, 0, 41, 2, 3, 0, 8, 7}));
    }

    @Test()
    public void checkOneFourTest3() {
        Assert.assertFalse(test1.checkOneFour(new int[]{}));
    }

}
