package keya.xunke;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class DemoData6 {

    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("题目")
    private String timu;
    @ExcelProperty("题型")
    private String tixing; //单选题/多选题
    @ExcelProperty("分数")
    private String fenshu;  //1 2
    @ExcelProperty("难度")
    private String nandu;  //简单、一般、困难
    @ExcelProperty("选项A")
    private String xuanxiangA;
    @ExcelProperty("选项B")
    private String xuanxiangB;
    @ExcelProperty("选项C")
    private String xuanxiangC;
    @ExcelProperty("选项D")
    private String xuanxiangD;
    @ExcelProperty("答案")
    private String daan;  //A AB
    @ExcelProperty("解析")
    private String jiexi;
    @ExcelProperty("一级目录")
    private String mulu1;
    @ExcelProperty("二级目录")
    private String mulu2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimu() {
        return timu;
    }

    public void setTimu(String timu) {
        this.timu = timu;
    }

    public String getTixing() {
        return tixing;
    }

    public void setTixing(String tixing) {
        this.tixing = tixing;
    }

    public String getFenshu() {
        return fenshu;
    }

    public void setFenshu(String fenshu) {
        this.fenshu = fenshu;
    }

    public String getNandu() {
        return nandu;
    }

    public void setNandu(String nandu) {
        this.nandu = nandu;
    }

    public String getXuanxiangA() {
        return xuanxiangA;
    }

    public void setXuanxiangA(String xuanxiangA) {
        this.xuanxiangA = xuanxiangA;
    }

    public String getXuanxiangB() {
        return xuanxiangB;
    }

    public void setXuanxiangB(String xuanxiangB) {
        this.xuanxiangB = xuanxiangB;
    }

    public String getXuanxiangC() {
        return xuanxiangC;
    }

    public void setXuanxiangC(String xuanxiangC) {
        this.xuanxiangC = xuanxiangC;
    }

    public String getXuanxiangD() {
        return xuanxiangD;
    }

    public void setXuanxiangD(String xuanxiangD) {
        this.xuanxiangD = xuanxiangD;
    }

    public String getDaan() {
        return daan;
    }

    public void setDaan(String daan) {
        this.daan = daan;
    }

    public String getJiexi() {
        return jiexi;
    }

    public void setJiexi(String jiexi) {
        this.jiexi = jiexi;
    }

    public String getMulu1() {
        return mulu1;
    }

    public void setMulu1(String mulu1) {
        this.mulu1 = mulu1;
    }

    public String getMulu2() {
        return mulu2;
    }

    public void setMulu2(String mulu2) {
        this.mulu2 = mulu2;
    }
}
