package com.cybil.study.erection.util;

public class Gambler {
    private int stack;
    private boolean animationIsEnd = true;
    private String defaultStatus;
    private String[] statusList;
    private String status;
    private int[] statusValueList;

    public Gambler (int stack, String defaultStatus, String[] statusList, int[] statusValueList) {
        this.stack = stack;
        this.status = defaultStatus;
        this.defaultStatus = defaultStatus;
        this.statusList = statusList;
        this.statusValueList = statusValueList;
    }

    public void setStack(int stack) { this.stack = stack; }
    public void addStack(int stack) { this.stack += stack; }
    public int getStack() {
        return stack;
    }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDefaultStatus() { return defaultStatus; }
    public void setDefaultStatus(String defaultStatus) { this.defaultStatus = defaultStatus; }
    public String[] getStatusList() { return statusList; }
    public void setStatusList(String[] statusList) { this.statusList = statusList; }
    public int[] getStatusValueList() { return this.statusValueList; }
    public void setStatusValueList(int[] statusValueList) { this.statusValueList = statusValueList; }

    public boolean getAnimationIsEnd(){ return this.animationIsEnd; }
    public void setAnimationIsEnd(boolean animationIsEnd) { this.animationIsEnd = animationIsEnd; }
}
