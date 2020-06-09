package fudan.se.lab2.domain.conference;


import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.persistence.*;

/**
 * one comment or judgment of a paper
 */
@Entity
public class PaperPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postsId;

    @ManyToOne
    private User user;

    private String comments;

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PaperPosts(User user, String comments) {
        this.user=user;
        this.comments=comments;
    }


    public JSONObject tojSON() {
        String str="{\"name\":\""+this.user.getUsername()+"\",\"message\":\""+this.comments+"\"}";
        try {
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}



