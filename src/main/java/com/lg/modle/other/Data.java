package com.lg.modle.other;

public class Data {
    private String filename;
    private String storageId;

    public Data() {
    }

    public Data(String filename, String storageId) {
        this.filename = filename;
        this.storageId = storageId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    @Override
    public String toString() {
        return "Data{" +
                "filename='" + filename + '\'' +
                ", storageId='" + storageId + '\'' +
                '}';
    }
}
