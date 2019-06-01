package consys.onlineexam.helper;

import java.io.Serializable;

public class TipModel implements Serializable {
    String creation_time;
    String display_time;
    String tip_content;
    int tip_id;

    public int getTip_id() {
        return this.tip_id;
    }

    public void setTip_id(int tip_id) {
        this.tip_id = tip_id;
    }

    public String getTip_content() {
        return this.tip_content;
    }

    public void setTip_content(String tip_content) {
        this.tip_content = tip_content;
    }

    public String getCreation_time() {
        return this.creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getDisplay_time() {
        return this.display_time;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }
}
