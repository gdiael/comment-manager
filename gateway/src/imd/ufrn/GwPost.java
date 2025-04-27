package imd.ufrn;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GwPost {

    private List<GwComment> comments_;
    private String id_;
    private String authorId_;
    private String content_;
    private Date tick_;
    
    public GwPost(String id, String authorId, String content, Date tick) {
        id_ = id;
        authorId_ = authorId;
        content_ = content;
        tick_ = tick;
        comments_ = new LinkedList<>();
    }

    public String getId() {return id_;}
    public String getAuthorId() {return authorId_;}
    public String getContent() {return content_;}
    public Date getTick() {return tick_;}

    public GwComment getComment(String commentId) {
        return comments_.get(0);
    }
    public boolean addComment(GwComment cmt) {
        comments_.add(cmt);
        return true;
    }
}
