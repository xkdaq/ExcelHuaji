package huaji.nanshan.bean;

import java.io.Serializable;
import java.util.List;

public class ListBen implements Serializable {
    private String msg;
    private long code;
    private List<ListItBean> data;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public long getCode() { return code; }
    public void setCode(long value) { this.code = value; }

    public List<ListItBean> getData() { return data; }
    public void setData(List<ListItBean> value) { this.data = value; }
}
