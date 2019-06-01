package consys.onlineexam.helper;

import java.io.Serializable;

public class ExamModel implements Serializable {
    static int CurrentQuestion;
    static long Millis;
    int Attempts;
    int Chapter_id;
    int College_id;
    String Creater_name;
    String Creation_time;
    String End_Time;
    String Exam_Date;
    String Exam_Type;
    String Exam_duration;
    int Exam_id;
    String Exam_name;
    int Marks;
    int MyAttempts;
    float Negative;
    int No_of_question;
    String Start_Time;
    int Subject_id;
    String image_dir;
    String is_free;

    public long getMillis() {
        return Millis;
    }

    public void setMillis(long millis) {
        Millis = millis;
    }

    public int getCurrentQuestion() {
        return CurrentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        CurrentQuestion = currentQuestion;
    }

    public float getNegative() {
        return this.Negative;
    }

    public String getImage_dir() {
        return this.image_dir;
    }

    public void setImage_dir(String image_dir) {
        this.image_dir = image_dir;
    }

    public void setNegative(float negative) {
        this.Negative = negative;
    }

    public String getCreater_name() {
        return this.Creater_name;
    }

    public String getCreation_time() {
        return this.Creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.Creation_time = creation_time;
    }

    public void setCreater_name(String creater_name) {
        this.Creater_name = creater_name;
    }

    public int getCollege_id() {
        return this.College_id;
    }

    public void setCollege_id(int college_id) {
        this.College_id = college_id;
    }

    public int getChapter_id() {
        return this.Chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.Chapter_id = chapter_id;
    }

    public int getMyAttempts() {
        return this.MyAttempts;
    }

    public void setMyAttempts(int myAttempts) {
        this.MyAttempts = myAttempts;
    }

    public int getAttempts() {
        return this.Attempts;
    }

    public void setAttempts(int attempts) {
        this.Attempts = attempts;
    }

    public String getIs_free() {
        return this.is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public int getExam_id() {
        return this.Exam_id;
    }

    public void setExam_id(int exam_id) {
        this.Exam_id = exam_id;
    }

    public String getExam_name() {
        return this.Exam_name;
    }

    public void setExam_name(String exam_name) {
        this.Exam_name = exam_name;
    }

    public int getSubject_id() {
        return this.Subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.Subject_id = subject_id;
    }

    public String getExam_duration() {
        return this.Exam_duration;
    }

    public void setExam_duration(String string) {
        this.Exam_duration = string;
    }

    public int getNo_of_question() {
        return this.No_of_question;
    }

    public void setNo_of_question(int no_of_question) {
        this.No_of_question = no_of_question;
    }

    public int getMarks() {
        return this.Marks;
    }

    public void setMarks(int marks) {
        this.Marks = marks;
    }

    public String getExam_Type() {
        return this.Exam_Type;
    }

    public void setExam_Type(String exam_Type) {
        this.Exam_Type = exam_Type;
    }

    public String getExam_Date() {
        return this.Exam_Date;
    }

    public void setExam_Date(String exam_Date) {
        this.Exam_Date = exam_Date;
    }

    public String getStart_Time() {
        return this.Start_Time;
    }

    public void setStart_Time(String start_Time) {
        this.Start_Time = start_Time;
    }

    public String getEnd_Time() {
        return this.End_Time;
    }

    public void setEnd_Time(String end_Time) {
        this.End_Time = end_Time;
    }

    public static void main(String[] args) {
        System.out.println("Exam mOdel");
    }
}
