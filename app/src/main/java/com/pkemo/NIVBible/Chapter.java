package com.pkemo.NIVBible;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson on 11/21/2016.
 */

public class Chapter extends ArrayList<Verse> {
    private String cnumber;
    public List<Verse> versses;

    public String getCnumber() {
        return this.cnumber;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public Chapter() {
        this.versses = new ArrayList();
    }
}
