package consys.onlineexam.helper;

import java.io.Serializable;

public class ItemDetail implements Serializable {
    String ch_name;
    private int chid;
    String parent;
    private String parent_name;

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public ItemDetail(int imgId, String name, String pname) {
        this.chid = imgId;
        this.ch_name = name;
        this.parent = pname;
    }

    public String getParent_name() {
        return this.parent_name;
    }

    public int getChid() {
        return this.chid;
    }

    public void setChid(int chid) {
        this.chid = chid;
    }

    public String getCh_name() {
        return this.ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }
}
