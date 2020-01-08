package com.pkemo.NIVBible;

/**
 * Created by Nelson on 1/14/2017.
 */

public class NIVBookMark {
    private String b_verse;
    private String b_versenumber;
    private String bookname;
    private String chapter_no;
    private int colorcode;
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getB_versenumber() {
        return this.b_versenumber;
    }

    public void setB_versenumber(String b_versenumber) {
        this.b_versenumber = b_versenumber;
    }

    public String getBookname() {
        return this.bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getChapter_no() {
        return this.chapter_no;
    }

    public void setChapter_no(String chapter_no) {
        this.chapter_no = chapter_no;
    }

    public String getVerse() {
        return this.b_verse;
    }

    public void setVerse(String verse) {
        this.b_verse = verse;
    }

    public int getColorcode() {
        return this.colorcode;
    }

    public void setColorcode(int colorcode) {
        this.colorcode = colorcode;
    }
}