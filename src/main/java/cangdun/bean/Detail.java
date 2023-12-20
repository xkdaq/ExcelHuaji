package cangdun.bean;

import java.util.List;

public class Detail {
    private long timuCount;
    private boolean datiAll;
    private long lasttimukey;
    private List<String> timuStudy;
    private boolean leftTimu;
    private List<Timu> timu;
//    private Map<String, Pdati> pdati;
    private long timuStudyCount;
    private List<Object> shouchang;
    private String lasttimuid;
    private long pdatiCount;

    public long getTimuCount() {
        return timuCount;
    }

    public void setTimuCount(long value) {
        this.timuCount = value;
    }

    public boolean getDatiAll() {
        return datiAll;
    }

    public void setDatiAll(boolean value) {
        this.datiAll = value;
    }

    public long getLasttimukey() {
        return lasttimukey;
    }

    public void setLasttimukey(long value) {
        this.lasttimukey = value;
    }

    public List<String> getTimuStudy() {
        return timuStudy;
    }

    public void setTimuStudy(List<String> value) {
        this.timuStudy = value;
    }

    public boolean getLeftTimu() {
        return leftTimu;
    }

    public void setLeftTimu(boolean value) {
        this.leftTimu = value;
    }

    public List<Timu> getTimu() {
        return timu;
    }

    public void setTimu(List<Timu> value) {
        this.timu = value;
    }

//    public Map<String, Pdati> getPdati() {
//        return pdati;
//    }
//
//    public void setPdati(Map<String, Pdati> value) {
//        this.pdati = value;
//    }

    public long getTimuStudyCount() {
        return timuStudyCount;
    }

    public void setTimuStudyCount(long value) {
        this.timuStudyCount = value;
    }

    public List<Object> getShouchang() {
        return shouchang;
    }

    public void setShouchang(List<Object> value) {
        this.shouchang = value;
    }

    public String getLasttimuid() {
        return lasttimuid;
    }

    public void setLasttimuid(String value) {
        this.lasttimuid = value;
    }

    public long getPdatiCount() {
        return pdatiCount;
    }

    public void setPdatiCount(long value) {
        this.pdatiCount = value;
    }
}
