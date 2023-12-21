package com.lg.example.performance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lg.framework.impl.performance.TaskObject;
import com.lg.modle.performance.WorkReport;
import com.lg.tool.common.ChoiceFunc;
import com.lg.tool.httpClient.HttpHelper;

import java.time.Instant;
import java.util.HashMap;

public class TestTask extends TaskObject {
    private ChoiceFunc<HttpHelper> choiceFunc = new ChoiceFunc<>();

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public void work(WorkReport workReport) throws AssertionError {
        String url = "http://127.0.0.1:12312/staticdb/search/multiple";
        HashMap<String, Object> req = new HashMap<>();
        req.put("app_id", "system");
        req.put("app_secret", "12345");
        req.put("groupId", "14E");
        req.put("topN", 1);
//        req.put("perGroup","mode");
        String feature = "AxsQFAr0Cw/u/h4D8gr+/Q8R/wLj9vkC/hUEEOQiGhcZ4wny9/DsHwUND/T5BfoQBwQX6iQF+ArpIAfy8uwJ6N7uDPwJ/BPjAiPXKPsM1ADm0ucQ7hoI+QEM/QLj9CT08CQfDwz7BPMA/OruFgYB4fX1D/IWDw0U7v4T+w/w8PYH/wEUBP76+fAYDQMGEfH79P8WBgn9+ej+CRX8BgkF7vkK7RP5+wLzDRn6CfsACgwI8RX8AQ8J9SMC8g32BRXxIgX7//EQCu37BQIN8uT9Fe8KAA3sBxvz8en9F/cX6Rn3GQgIAAv8CfEB6PkSDgMT/wXn6Pj9DvQH9gP49w4MGATsCvnu7+wQCQb/5AEkERbt/g387//5/A8D8Qf8BPny8vD0//HuDhT0EPT/3P0D/fQT8fENC/PnC+3xBQoM/Qz77Pf18gD6BPwR7/j5EAsEBRABB/oC+hkBEQLlAP7/FPsGCv3t/wf/B/7xCQv9AvoC+xQM/fz/Bg0DBgUJDPvq";
        req.put("feature", feature);
        HttpHelper httpHelper = this.choiceFunc.getObjRandom(TestManager.httpHelpers);
        workReport.setStart(Instant.now());
        HashMap<String, String> header = httpHelper.getDefaultHeader();
        header.put("User-Agent", "Apache-HttpClient/4.5.3 (Java/1.8.0_31)");
        header.remove("accept");
        String res = httpHelper.post(url, req, header);
        workReport.setEnd(Instant.now());
        JSONObject jsonObject = JSON.parseObject(res);
        workReport.setAssertResult(Integer.parseInt(jsonObject.get("result").toString()) == 0);
    }
}
