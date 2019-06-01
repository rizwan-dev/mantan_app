package consys.onlineexam.helper;

import java.io.Serializable;

public class NewsModel implements Serializable {
    String details;
    String header;
    int id;
    byte[] newsimage;

    public byte[] getNewsimage() {
        return this.newsimage;
    }

    public void setNewsimage(byte[] newsimage) {
        this.newsimage = newsimage;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
