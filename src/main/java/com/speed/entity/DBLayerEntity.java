package com.speed.entity;

public class DBLayerEntity {
    private String workspace;
    private String storename;
    private String layername;
    private String srs;
    private String defaultstyle;

    public void setDefaultstyle(String defaultstyle) {
        this.defaultstyle = defaultstyle;
    }

    public String getDefaultstyle() {
        return defaultstyle;
    }

    public void setLayername(String layername) {
        this.layername = layername;
    }

    public String getLayername() {
        return layername;
    }

    public void setSrs(String srs) {
        this.srs = srs;
    }

    public String getSrs() {
        return srs;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getStorename() {
        return storename;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getWorkspace() {
        return workspace;
    }
}
