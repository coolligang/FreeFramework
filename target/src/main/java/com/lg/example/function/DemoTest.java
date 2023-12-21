package com.lg.example.function;

import com.lg.framework.impl.function.ResearchQADescription;
import com.lg.framework.impl.function.ResearchQARunListener;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;

//修改后的JUnit用例，此处RunWith注解注解用引入自定义Runner
@Listeners({ResearchQARunListener.class})
public class DemoTest {
    private String is;

//    public boolean isRun(String modelInfo) {
//        return true;
//    }

    @Test()
    @ResearchQADescription(name = "测试项1")
    public void testAdd_Positive() {
        assertEquals("加法运算出错", 6, 3 + 3);
    }

    @Ignore()
    @Test(description = "这里是TEST描述...")
    @ResearchQADescription(name = "测试项3", step = {
            "step1:这里是每一步",
            "step2:这里是每二步",
            "step3:这里是第三步"})
    public void testMinus_Positive() {
        assertEquals("减法运算出错", 0, 3 - 3);
    }

}
