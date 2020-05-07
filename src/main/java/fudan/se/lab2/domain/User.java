package fudan.se.lab2.domain;

import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LBW
 * 这个类是用户类，生成用户，放入仓库
 */

@Entity
public class User {

    private static final long serialVersionUID = -6140085056226164016L;

    // 12个属性
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    // 'username' is better than 'name'
    private String username;
    // password
    private String password;
    // fullName
    private String fullName;
    // unit
    private String unit;
    // area
    private String area;
    // email
    private String email;

    // empty constructor
    public User() {}
    // constructor
    public User(String username, String password, String fullName, String unit, String area, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.unit = unit;
        this.area = area;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getUnit() {
        return unit;
    }
    public String getEmail() {
        return email;
    }
    public String getArea() {
        return area;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getFullName() {
        return fullName;
    }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public JSONObject toStandardJson() {
        try {
            String str = "{" +
                    "\"id\":\"" + id.toString() + '\"' +
                    ", \"username\":\"" + username + '\"' +
                    ", \"fullName\":\"" + fullName + '\"' +
                    ", \"email\":\"" + email + '\"' +
                    ", \"area\":\"" + area + '\"' +
                    ", \"unit\":\"" + unit + '\"' +
                    '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", unit='" + unit + '\'' +
                ", area='" + area + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
