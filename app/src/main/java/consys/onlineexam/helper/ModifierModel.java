package consys.onlineexam.helper;

import java.io.Serializable;

public class ModifierModel implements Serializable {
    String cammand;
    String data;
    String flag;
    int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCammand() {
        return this.cammand;
    }

    public void setCammand(String cammand) {
        this.cammand = cammand;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
