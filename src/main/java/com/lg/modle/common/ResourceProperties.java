package com.lg.modle.common;

public class ResourceProperties {
    private String ip;             // 主机地址
    private String os;             // 操作系统
    private String chip;           // 芯片类型
    private int process;           // CPU核数
    private String ram;            // 硬盘信息
    private String gpu;            // 显卡型号
    private String gpu_driver;     // 显卡驱动

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return chip;
    }

    public void setCpu(String cpu) {
        this.chip = cpu;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getGpu_driver() {
        return gpu_driver;
    }

    public void setGpu_driver(String gpu_driver) {
        this.gpu_driver = gpu_driver;
    }

    @Override
    public String toString() {
        return "ResourceProperties{" +
                "ip='" + ip + '\'' +
                ", os='" + os + '\'' +
                ", cpu='" + chip + '\'' +
                ", process=" + process +
                ", ram='" + ram + '\'' +
                ", gpu='" + gpu + '\'' +
                ", gpu_driver='" + gpu_driver + '\'' +
                '}';
    }
}
