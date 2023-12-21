package com.lg.example.test;

import com.lg.tool.common.Base64Encoder;
import com.lg.tool.common.ImageUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * ProtocolFreeFramework
 * JMTest
 *
 * @author: ligang30
 * @date: 2022/12/20
 */

public class JMTest {
    public static void main(String[] args) {
        String data="object_type=general_handwriting&type=st_ocrapi_all&eng_granularity=word&disp_line_poly=\n" +
                "true&image=";
        String imageStr= ImageUtils.getBase64FromPath("/Users/ligang30/WorkSpace/IdeaProjects/ProtocolFreeFramework/src/main/resources/handWrite.jpg");
        data=data+imageStr;
        String jsonDataStr= Base64Encoder.encode(data.getBytes(StandardCharsets.UTF_8));
        Map<String,String> body=new HashMap<>();
        body.put("data",jsonDataStr);
        body.put("provider","default");
        System.out.println(body.toString());
    }
}
