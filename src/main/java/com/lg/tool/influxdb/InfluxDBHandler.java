package com.lg.tool.influxdb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class InfluxDBHandler {
    private static Logger logger = LogManager.getLogger(InfluxDBHandler.class);
    private InfluxDB db;
    private Point.Builder builder;

    public InfluxDBHandler(String host, String username, String password) {
        this.db = InfluxDBFactory.connect("http://" + host, username, password);
        if (this.db == null) {
            logger.error("InfluxDB connect failed~");
        }
    }

    public InfluxDBHandler(String host, String username, String password, String database, String measurement) {
        this.db = InfluxDBFactory.connect("http://" + host, username, password);
        if (this.db == null) {
            logger.error("InfluxDB connect failed~");
        }
        assert this.db != null;
        this.db.setDatabase(database);
        this.builder = Point.measurement(measurement);
    }

    public void setDB(String database, String measurement) {
        this.db.setDatabase(database);
        this.builder = Point.measurement(measurement);
    }

    public void insert(HashMap<String, String> tags, HashMap<String, Object> fields) {
        this.builder.tag(tags);
        this.builder.fields(fields);
        Point point = builder.build();
        this.db.write(point);
    }

    public void insert(HashMap<String, String> tags, HashMap<String, Object> fields, long timestamp) {
        this.builder.tag(tags);
        this.builder.fields(fields);
        this.builder.time(timestamp, TimeUnit.NANOSECONDS);
        Point point = builder.build();
        this.db.write(point);
    }
}


