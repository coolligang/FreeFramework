package com.lg.tool.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class ConfigPropertiesService {
    private static Logger logger = LogManager.getLogger(ConfigPropertiesService.class);
    private Properties prop = new Properties();

    /**
     * @param configName 放在resources目录下的文件名
     */
    public ConfigPropertiesService(String configName) {
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(configName)), StandardCharsets.UTF_8);
        try {
            this.prop.load(inputStreamReader);
            inputStreamReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 根据配置文件中的key，获取value
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String info = null;
        try {
            for (String s : this.prop.stringPropertyNames()) {
                if (key.equals(s)) {
                    info = this.prop.getProperty(key);
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("read config file error", ex);
        }
        return info;
    }
}
