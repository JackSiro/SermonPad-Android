package com.jackson_siro.sermonpad.SQLite;

public class SermonItem {
    private int rid;
    private String rtitle, rpreacher, rplace, rcontent, raudios, rfiles, rstate, rcreated, rupdated, raccessed;

    public SermonItem(){}

    public SermonItem(String rtitle, String rpreacher, String rplace, String rcontent, String raudios, String rfiles, String rstate, String rcreated, String rupdated, String raccessed) {
        super();
        this.rtitle = rtitle;
        this.rpreacher = rpreacher;
        this.rplace = rplace;
        this.rcontent = rcontent;
        this.raudios = raudios;
        this.rfiles = rfiles;
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

    public String getRtitle() {
        return rtitle;
    }
    public void setRtitle(String rtitle) {
        this.rtitle = rtitle;
    }

    public String getRpreacher() {
        return rpreacher;
    }
    public void setRpreacher(String rpreacher) {
        this.rpreacher = rpreacher;
    }

    public String getRplace() {
        return rplace;
    }
    public void setRplace(String rplace) {
        this.rplace = rplace;
    }

    public String getRcontent() {
        return rcontent;
    }
    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public String getRaudios() {
        return raudios;
    }
    public void setRaudios(String raudios) {
        this.raudios = raudios;
    }

    public String getRfiles() {
        return rfiles;
    }
    public void setRfiles(String rfiles) {
        this.rfiles = rfiles;
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
