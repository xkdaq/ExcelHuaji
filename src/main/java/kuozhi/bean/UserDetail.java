package kuozhi.bean;

import java.util.List;

class UserDetail {
    private List<Long> qtime;
    private List<Long> isright;
    private long finishStatus;
    private List<Long> favorite;
    private long usetime;

    public List<Long> getQtime() {
        return qtime;
    }

    public void setQtime(List<Long> value) {
        this.qtime = value;
    }

    public List<Long> getIsright() {
        return isright;
    }

    public void setIsright(List<Long> value) {
        this.isright = value;
    }

    public long getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(long value) {
        this.finishStatus = value;
    }

    public List<Long> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Long> value) {
        this.favorite = value;
    }

    public long getUsetime() {
        return usetime;
    }

    public void setUsetime(long value) {
        this.usetime = value;
    }
}
