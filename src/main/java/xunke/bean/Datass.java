package xunke.bean;

import java.util.List;

public class Datass {
    private long lastQId;
    private List<Questions> questions;
    private String title;
    private long lastQNo;

    public long getLastQId() { return lastQId; }
    public void setLastQId(long value) { this.lastQId = value; }

    public List<Questions> getQuestions() { return questions; }
    public void setQuestions(List<Questions> value) { this.questions = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public long getLastQNo() { return lastQNo; }
    public void setLastQNo(long value) { this.lastQNo = value; }
}
