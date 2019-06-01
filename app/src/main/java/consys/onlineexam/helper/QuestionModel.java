package consys.onlineexam.helper;

import java.io.Serializable;

public class QuestionModel implements Serializable {
    String Answer_type;
    int Chapter_id;
    String Currect_ans;
    String Direction;
    int Exam_id;
    int Que_id;
    String Question;
    String Solution;
    int Subject_id;
    String URL;
    int marks;

    public String getDirection() {
        return this.Direction;
    }

    public void setDirection(String direction) {
        this.Direction = direction;
    }

    public int getMarks() {
        return this.marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getQue_id() {
        return this.Que_id;
    }

    public void setQue_id(int que_id) {
        this.Que_id = que_id;
    }

    public int getExam_id() {
        return this.Exam_id;
    }

    public void setExam_id(int exam_id) {
        this.Exam_id = exam_id;
    }

    public int getSubject_id() {
        return this.Subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.Subject_id = subject_id;
    }

    public int getChapter_id() {
        return this.Chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.Chapter_id = chapter_id;
    }

    public String getQuestion() {
        return this.Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public String getCurrect_ans() {
        return this.Currect_ans;
    }

    public void setCurrect_ans(String currect_ans) {
        this.Currect_ans = currect_ans;
    }

    public String getSolution() {
        return this.Solution;
    }

    public void setSolution(String solution) {
        this.Solution = solution;
    }

    public String getURL() {
        return this.URL;
    }

    public void setURL(String uRL) {
        this.URL = uRL;
    }

    public String getAnswer_type() {
        return this.Answer_type;
    }

    public void setAnswer_type(String answer_type) {
        this.Answer_type = answer_type;
    }
}
