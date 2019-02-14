package com.cybil.study.erection.util;

public class Gambler {
    private int stack;
    private boolean findKey = false;
    private String defaultStatus;
    private String[] statusList;
    private String status;
    private String hint;
    private int[] statusValueList;
    private String key;

    public Gambler (int stack, String defaultStatus, String[] statusList, int[] statusValueList, String hint, String key) {
        this.stack = stack;
        this.status = defaultStatus;
        this.defaultStatus = defaultStatus;
        this.statusList = statusList;
        this.statusValueList = statusValueList;
        this.hint = hint;
        this.key = key;
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
    public String getHint() { return this.hint; }
    public void setHint(String hint) { this.hint = hint; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setFindKey(boolean findKey) {
        this.findKey = findKey;
    }

    public boolean isFindKey() {
        return findKey;
    }
}
