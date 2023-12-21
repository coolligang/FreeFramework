package com.lg.modle.common;

public class Status {
    // 标记性能测试当前请求WorkReport的 暂停、测试中、结束 指令
    public static final int testSUSPEND = 0;   // 暂时结束
    public static final int testEND = -1;      // 测试结束
    public static final int testWORKING = 1;   // 初始值

    // 控制性能测试的启停
    public static boolean threadRUNNING = true;  // 运行状态
    public static boolean threadSTOP = false;    // 停止状态

    // 控制接收数据的开始结束
    public static boolean receiveYes = true;     // 正在接受数据
    public static boolean receiveNo = false;     // 停止接收数据

    // 设置性能测试的模型
    public static boolean LOCAL = true;   // 该值为true, 则结果输出到csv文件中，否则上传到influx
}
