package imd.ufrn;

import java.util.Date;

public class GwComment {

    private String id_;
    private String authorId_;
    private String content_;
    private Date tick_;

    public GwComment(String id, String authorId, String content, Date tick) {
        id_ = id;
        authorId_ = authorId;
        content_ = content;
        tick_ = tick;
    }

    public String getId() {return id_;}
    public String getAuthorId() {return authorId_;}
    public String getContent() {return content_;}
    public Date getTick() {return tick_;}
    
}
