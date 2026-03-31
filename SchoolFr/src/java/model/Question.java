package model;

import java.util.Date;

public class Question {

    private int questionId;
    private String title;
    private String content;
    private Integer userId;   // ✅ chỉ giữ dòng này
    private Date createdAt;
    private String authorName;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {   // ✅ chỉ giữ một getter
        return userId;
    }

    public void setUserId(Integer userId) {   // ✅ chỉ giữ một setter
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    private boolean isAnonymous;

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
}
