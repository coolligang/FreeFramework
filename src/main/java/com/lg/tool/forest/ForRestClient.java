package com.lg.tool.forest;

import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Request;

/**
 * ProtocolFreeFramework
 * ForestClient
 *
 * @author: ligang30
 * @date: 2022/12/7
 */

public interface ForRestClient {
    @Request(
            url = "http://httpbin.org/post",
            type = "POST"
    )
    String simplePost(@Body("name") String name);
}
