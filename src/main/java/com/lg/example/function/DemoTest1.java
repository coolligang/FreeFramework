package com.lg.example.function;

import com.lg.framework.impl.function.ResearchQADescription;
import com.lg.framework.impl.function.ResearchQARunListener;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;

//修改后的JUnit用例，此处RunWith注解注解用引入自定义Runner
@Listeners({ResearchQARunListener.class})
public class DemoTest1 extends DemoTest {

    public boolean isRun(String modelInfo) {
        return true;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    @ResearchQADescription(name = "测试项111")
    public void testAdd_Positive() {
        assertEquals("加法运算出错", 6, 3 + 3);
    }

    @Ignore()
    @Test(expectedExceptions = ArithmeticException.class)
    @ResearchQADescription(name = "测试项2")
    public void testAdd_Negetive() {
        assertEquals("请检查运算结果是否溢出及测试平台支持的基本数据类型取值范围", 220000000, 1100000000 + 1100000000);
    }

    @Test(description = "这里是TEST描述...")
    @ResearchQADescription(name = "测试项33333", step = {
            "step1:这里是每一步",
            "step2:这里是每二步",
            "step3:这里是第三步"})
    public void testMinus_Positive() {
        assertEquals("减法运算出错", 0, 3 - 3);
    }

    @Test(timeOut = 200)
    @ResearchQADescription(name = "测试项45555", step = {
            "step1:这里是每一步",
            "step2:这里是每二步",
            "step3:这里是第三步"})
    public void testTimeout() throws InterruptedException {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            Thread.sleep(1000);
            sum = i + sum;
        }
    }
}
