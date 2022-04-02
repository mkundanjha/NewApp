package com.galanto.GalantoHealth;

public class RomPerSession {
    private int thumbMcpMax;
    private int thumbMcpMin;
    private int thumbPipMax;
    private int thumbPipMin;
    private int indexMcpMax;
    private int indexMcpMin;
    private int indexPipMax;
    private int indexPipMin;
    private int middleMcpMax;
    private int middleMcpMin;
    private int middlePipMax;
    private int middlePipMin;
    private int RingMcpMax;
    private int RingMcpMin;
    private int RingPipMax;
    private int RingPipMin;
    private int LittleMcpMax;
    private int LittleMcpMin;
    private int LittlePipMax;
    private int LittlePipMin;
    private int wristMax;
    private int wristMin;

    public RomPerSession(int thumbMcpMax, int thumbMcpMin, int thumbPipMax, int thumbPipMin, int indexMcpMax, int indexMcpMin, int indexPipMax, int indexPipMin, int middleMcpMax, int middleMcpMin, int middlePipMax, int middlePipMin, int ringMcpMax, int ringMcpMin, int ringPipMax, int ringPipMin, int littleMcpMax, int littleMcpMin, int littlePipMax, int littlePipMin,int wristMax,int wristMin) {
        this.thumbMcpMax = thumbMcpMax;
        this.thumbMcpMin = thumbMcpMin;
        this.thumbPipMax = thumbPipMax;
        this.thumbPipMin = thumbPipMin;
        this.indexMcpMax = indexMcpMax;
        this.indexMcpMin = indexMcpMin;
        this.indexPipMax = indexPipMax;
        this.indexPipMin = indexPipMin;
        this.middleMcpMax = middleMcpMax;
        this.middleMcpMin = middleMcpMin;
        this.middlePipMax = middlePipMax;
        this.middlePipMin = middlePipMin;
        this.RingMcpMax = ringMcpMax;
        this.RingMcpMin = ringMcpMin;
        this.RingPipMax = ringPipMax;
        this.RingPipMin = ringPipMin;
        this.LittleMcpMax = littleMcpMax;
        this.LittleMcpMin = littleMcpMin;
        this.LittlePipMax = littlePipMax;
        this.LittlePipMin = littlePipMin;
        this.wristMax=wristMax;
        this.wristMin=wristMin;
    }

    public int getThumbMcpMax() {
        return thumbMcpMax;
    }

    public int getThumbMcpMin() {
        return thumbMcpMin;
    }

    public int getThumbPipMax() {
        return thumbPipMax;
    }

    public int getThumbPipMin() {
        return thumbPipMin;
    }

    public int getIndexMcpMax() {
        return indexMcpMax;
    }

    public int getIndexMcpMin() {
        return indexMcpMin;
    }

    public int getIndexPipMax() {
        return indexPipMax;
    }

    public int getIndexPipMin() {
        return indexPipMin;
    }

    public int getMiddleMcpMax() {
        return middleMcpMax;
    }

    public int getMiddleMcpMin() {
        return middleMcpMin;
    }

    public int getMiddlePipMax() {
        return middlePipMax;
    }

    public int getMiddlePipMin() {
        return middlePipMin;
    }

    public int getRingMcpMax() {
        return RingMcpMax;
    }

    public int getRingMcpMin() {
        return RingMcpMin;
    }

    public int getRingPipMax() {
        return RingPipMax;
    }

    public int getRingPipMin() {
        return RingPipMin;
    }

    public int getLittleMcpMax() {
        return LittleMcpMax;
    }

    public int getLittleMcpMin() {
        return LittleMcpMin;
    }

    public int getLittlePipMax() {
        return LittlePipMax;
    }

    public int getLittlePipMin() {
        return LittlePipMin;
    }

    public int getWristMax() {
        return wristMax;
    }

    public int getWristMin() {
        return wristMin;
    }
}
