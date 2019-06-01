package consys.onlineexam.helper;

import java.io.Serializable;

public class SolutionModel implements Serializable {
    int Chapter_id;
    String hint;
    int sol_Id;
    String solution;

    public int getSol_Id() {
        return this.sol_Id;
    }

    public void setSol_Id(int sol_Id) {
        this.sol_Id = sol_Id;
    }

    public int getChapter_id() {
        return this.Chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.Chapter_id = chapter_id;
    }

    public String getSolution() {
        return this.solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
