package com.pkemo.NIVBible;

import java.util.List;
import java.io.Serializable;

/**
 * Created by Nelson on 11/21/2016.
 */

public class NIVBibleBooks implements Serializable {
    private String bname;
    private String bnumber;
    private List<Chapter> cc;
    private Chapter Chapter;

    public List<Chapter> getCc() {
        return this.cc;
    }

    public void setCc(List<Chapter> cc) {
        this.cc = cc;
    }

    public String getBname() {
        return this.bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBnumber() {
        return this.bnumber;
    }

    public void setBnumber(String bnumber) {
        this.bnumber = bnumber;
    }

    public Chapter getChapter() {
        return this.Chapter;
    }

    public void setChapter(Chapter chapter) {
        this.Chapter = chapter;
    }
}
