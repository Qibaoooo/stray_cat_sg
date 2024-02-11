package nus.iss.sa57.csc_android.model;

import java.util.List;

public class Comment {
    private int id;
    private String time;
    private String content;
    private List<String> newlabels;
    private SCSUser scsUser;
    public Comment(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getNewlabels() {
        return newlabels;
    }

    public void setNewlabels(List<String> newlabels) {
        this.newlabels = newlabels;
    }

    public SCSUser getScsUser() {
        return scsUser;
    }

    public void setScsUser(SCSUser scsUser) {
        this.scsUser = scsUser;
    }
}
