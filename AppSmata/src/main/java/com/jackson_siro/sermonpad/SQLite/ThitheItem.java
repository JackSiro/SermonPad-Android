package com.jackson_siro.sermonpad.SQLite;

public class ThitheItem {
    private int rid, ramount;
    private String rsource, rplace, rstate, rcreated, rupdated, raccessed;

    public ThitheItem(){}

    public ThitheItem(int ramount, String rsource, String rplace, String rstate, String rcreated, String rupdated, String raccessed) {
        super();
        this.ramount = ramount;
        this.rsource = rsource;
        this.rplace = rplace;
        this.rstate = rstate;
        this.rcreated = rcreated;
        this.rupdated = rupdated;
        this.raccessed = raccessed;
    }

    public int getRid() {
        return rid;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getRamount() {
        return ramount;
    }
    public void setRamount(int ramount) {
        this.ramount = ramount;
    }

    public String getRsource() {
        return rsource;
    }
    public void setRsource(String rsource) {
        this.rsource = rsource;
    }

    public String getRplace() {
        return rplace;
    }
    public void setRplace(String rplace) {
        this.rplace = rplace;
    }

    public String getRstate() {
        return rstate;
    }
    public void setRstate(String rstate) {
        this.rstate = rstate;
    }

    public String getRcreated() {
        return rcreated;
    }
    public void setRcreated(String rcreated) {
        this.rcreated = rcreated;
    }

    public String getRupdated() {
        return rupdated;
    }
    public void setRupdated(String rupdated) {
        this.rupdated = rupdated;
    }

    public String getRaccessed() {
        return raccessed;
    }
    public void setRaccessed(String raccessed) {
        this.raccessed = raccessed;
    }

}
