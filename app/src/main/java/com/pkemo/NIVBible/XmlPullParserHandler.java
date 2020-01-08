package com.pkemo.NIVBible;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;
import android.util.Xml.Encoding;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson on 12/14/2016.
 */

public class XmlPullParserHandler extends DefaultHandler {
    public static String bnamba;
    public List<NIVBibleBooks> allBooks;
    private NIVBibleBooks NIVBibleBooks;
    private List<Chapter> cclist;
    private Chapter Chapter;
    int indexme;
    private Verse Verse;
    int f7x;
    InputStream xx;


    class C04031 implements StartElementListener {
        C04031() {
        }

        public void start(Attributes attributes) {
            XmlPullParserHandler.this.NIVBibleBooks = new NIVBibleBooks();
            XmlPullParserHandler.this.NIVBibleBooks.setBname(attributes.getValue("bname"));
            XmlPullParserHandler.this.NIVBibleBooks.setBnumber(attributes.getValue("bnumber"));
            XmlPullParserHandler.this.cclist = new ArrayList();
        }
    }


    class C04042 implements EndElementListener {
        C04042() {
        }

        public void end() {
            XmlPullParserHandler.this.allBooks.add(XmlPullParserHandler.this.NIVBibleBooks);
            XmlPullParserHandler.this.NIVBibleBooks.setCc(XmlPullParserHandler.this.cclist);
        }
    }


    class C04053 implements StartElementListener {
        C04053() {
        }

        public void start(Attributes attributes) {
            XmlPullParserHandler.this.Chapter = new Chapter();
            XmlPullParserHandler.this.Chapter.setCnumber(attributes.getValue("cnumber"));
        }
    }


    class C04064 implements EndElementListener {
        C04064() {
        }

        public void end() {
            XmlPullParserHandler.this.NIVBibleBooks.setChapter(XmlPullParserHandler.this.Chapter);
            XmlPullParserHandler.this.cclist.add(XmlPullParserHandler.this.Chapter);
        }
    }


    class C04075 implements StartElementListener {
        C04075() {
        }

        public void start(Attributes attributes) {
            XmlPullParserHandler.this.Verse = new Verse();
            XmlPullParserHandler.this.Verse.setVnumber(attributes.getValue("vnumber"));
        }
    }


    class C04086 implements EndTextElementListener {
        C04086() {
        }

        public void end(String body) {
            XmlPullParserHandler.this.Verse.setVerse(body);
            XmlPullParserHandler.this.Chapter.versses.add(XmlPullParserHandler.this.Verse);
        }
    }

    public XmlPullParserHandler() {
        this.allBooks = new ArrayList();
        this.cclist = null;
        this.Chapter = new Chapter();
    }

    public List<NIVBibleBooks> parse(InputStream inputStream) {
        Exception e;
        this.xx = inputStream;
        RootElement root = new RootElement("XMLBIBLE");
        Element rootitem = root.getChild("BIBLEBOOK");
        rootitem.setStartElementListener(new C04031());
        rootitem.setEndElementListener(new C04042());
        rootitem.getChild("CHAPTER").setStartElementListener(new C04053());
        rootitem.getChild("CHAPTER").setEndElementListener(new C04064());
        rootitem.getChild("CHAPTER").getChild("VERS").setStartElementListener(new C04075());
        rootitem.getChild("CHAPTER").getChild("VERS").setEndTextElementListener(new C04086());
        try {
            Xml.parse(inputStream, Encoding.UTF_8, root.getContentHandler());
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
            return this.allBooks;
        } catch (SAXException e3) {
            e = e3;
            e.printStackTrace();
            return this.allBooks;
        }
        return this.allBooks;
    }
}