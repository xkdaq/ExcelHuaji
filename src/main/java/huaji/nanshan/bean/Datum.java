package huaji.nanshan.bean;

import java.io.Serializable;

public class Datum implements Serializable {
    private String explain;
    private String a;
    private String b;
    private String c;
    private boolean isMSelect;
    private String d;
    private String correct;
    private String simSource;
    private long simlId;
    private long pId;
    private String title;
    private long bookId;
    private long classId;
    private long teaid;
    private long id;
    private long tkExamId;
    private long teatkid;
    private long cId;

    @Override
    public String toString() {
        return "Datum{" +
                "explain='" + explain + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", isMSelect=" + isMSelect +
                ", d='" + d + '\'' +
                ", correct='" + correct + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    public String getExplain() { return explain; }
    public void setExplain(String value) { this.explain = value; }

    public String getA() { return a; }
    public void setA(String value) { this.a = value; }

    public String getB() { return b; }
    public void setB(String value) { this.b = value; }

    public String getC() { return c; }
    public void setC(String value) { this.c = value; }

    public boolean getIsMSelect() { return isMSelect; }
    public void setIsMSelect(boolean value) { this.isMSelect = value; }

    public String getD() { return d; }
    public void setD(String value) { this.d = value; }

    public String getCorrect() { return correct; }
    public void setCorrect(String value) { this.correct = value; }

    public String getSimSource() { return simSource; }
    public void setSimSource(String value) { this.simSource = value; }

    public long getSimlId() { return simlId; }
    public void setSimlId(long value) { this.simlId = value; }

    public long getPId() { return pId; }
    public void setPId(long value) { this.pId = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public long getBookId() { return bookId; }
    public void setBookId(long value) { this.bookId = value; }

    public long getClassId() { return classId; }
    public void setClassId(long value) { this.classId = value; }

    public long getTeaid() { return teaid; }
    public void setTeaid(long value) { this.teaid = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public long getTkExamId() { return tkExamId; }
    public void setTkExamId(long value) { this.tkExamId = value; }

    public long getTeatkid() { return teatkid; }
    public void setTeatkid(long value) { this.teatkid = value; }

    public long getCId() { return cId; }
    public void setCId(long value) { this.cId = value; }
}