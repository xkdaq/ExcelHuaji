package huaji.nanshan.bean;
import java.io.Serializable;
import java.util.List;

public class NanBean implements Serializable {
    private String msg;
    private long code;
    private List<Datum> data;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public long getCode() { return code; }
    public void setCode(long value) { this.code = value; }

    public List<Datum> getData() { return data; }
    public void setData(List<Datum> value) { this.data = value; }
}

