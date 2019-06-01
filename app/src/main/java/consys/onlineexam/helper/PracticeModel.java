package consys.onlineexam.helper;

import java.io.Serializable;

public class PracticeModel implements Serializable {
    int exam_id;
    String exam_name;
    int que_id;

    public int getQue_id() {
        return this.que_id;
    }

    public void setQue_id(int que_id) {
        this.que_id = que_id;
    }

    public int getExam_id() {
        return this.exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_name() {
        return this.exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }
}
