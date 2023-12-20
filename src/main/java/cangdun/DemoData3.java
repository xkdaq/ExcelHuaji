package cangdun;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class DemoData3 {

    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("出处")
    private String chuchu; //无
    @ExcelProperty("题型")
    private String tixing; //单选题/多选题
    @ExcelProperty("题干")
    private String tigan; //无
    @ExcelProperty("题目")
    private String timu;
    @ExcelProperty("选项")
    private String xuanxiang; //帝都|魔都|雾都|妖都|神都
    @ExcelProperty("答案")
    private String daan;  //A AB
    @ExcelProperty("解析")
    @ContentStyle(wrapped = true)
    private WriteCellData<String> jiexi;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChuchu() {
        return chuchu;
    }

    public void setChuchu(String chuchu) {
        this.chuchu = chuchu;
    }

    public String getTixing() {
        return tixing;
    }

    public void setTixing(String tixing) {
        this.tixing = tixing;
    }

    public String getTigan() {
        return tigan;
    }

    public void setTigan(String tigan) {
        this.tigan = tigan;
    }

    public String getXuanxiang() {
        return xuanxiang;
    }

    public void setXuanxiang(String xuanxiang) {
        this.xuanxiang = xuanxiang;
    }

    public String getTimu() {
        return timu;
    }

    public void setTimu(String timu) {
        this.timu = timu;
    }

    public String getDaan() {
        return daan;
    }

    public void setDaan(String daan) {
        this.daan = daan;
    }

    public WriteCellData<String> getJiexi() {
        return jiexi;
    }

    public void setJiexi(WriteCellData<String> jiexi) {
        this.jiexi = jiexi;
    }
}
