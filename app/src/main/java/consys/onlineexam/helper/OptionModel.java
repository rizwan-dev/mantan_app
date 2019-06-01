package consys.onlineexam.helper;

import java.io.Serializable;

public class OptionModel implements Serializable {
    private static final long serialVersionUID = 1;
    String Option;
    String Option_no;
    int Que_id;

    public int getQue_id() {
        return this.Que_id;
    }

    public void setQue_id(int que_id) {
        this.Que_id = que_id;
    }

    public String getOption_no() {
        return this.Option_no;
    }

    public void setOption_no(String option_no) {
        this.Option_no = option_no;
    }

    public String getOption() {
        return this.Option;
    }

    public void setOption(String option) {
        this.Option = option;
    }
}
