package twilliams84.uco.edu.recipeapp;

public class Comment {
    private String commenter;
    private String content;
    private String date;

    public Comment() {

    }

    public Comment(String commenter, String content, String date) {
        this.commenter = commenter;
        this.content = content;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
