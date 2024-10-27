package huaji.nanshan.bean;

import java.io.Serializable;

public class ListItBean implements Serializable {

    private long number;
    private long myScore;
    private String name;
    private long aveScore;
    private long id;
    private boolean isComplete;

    public long getNumber() { return number; }
    public void setNumber(long value) { this.number = value; }

    public long getMyScore() { return myScore; }
    public void setMyScore(long value) { this.myScore = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public long getAveScore() { return aveScore; }
    public void setAveScore(long value) { this.aveScore = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public boolean getIsComplete() { return isComplete; }
    public void setIsComplete(boolean value) { this.isComplete = value; }

}
