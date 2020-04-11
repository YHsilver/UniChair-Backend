package fudan.se.lab2.domain;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LBW
 * 这个类是用户类，生成用户，放入仓库
 */

@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    // 9个属性
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    // 会议列表
    private ArrayList<Long> conferenceIds = new ArrayList<>();

    // empty constructor
    public User() {
    }

    // constructor
    public User(String username, String password, String fullName, String unit, String area, String email,
                Set<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.unit = unit;
        this.area = area;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public ArrayList<Long> getConferenceIds() {
        return conferenceIds;
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

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setConferencesId(ArrayList<Long> conferenceIds) {
        this.conferenceIds = conferenceIds;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String toJsonObject() {
        return "{" +
                "\"id\":" + id +
                ", \"username\":\"" + username + '\"' +
                ", \"fullName\":\"" + fullName + '\"' +
                ", \"email\":\"" + email + '\"' +
                '}';
    }

    // print all info, not safe!
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
                ", authorities=" + authorities +
                ", conferenceIds=" + conferenceIds +
                '}';
    }

    // print necessary info, safe!
    public String toUserString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", unit='" + unit + '\'' +
                ", area='" + area + '\'' +
                ", email='" + email + '\'' +
//                ", authorities=" + authorities +
//                ", conferenceIds=" + conferenceIds +
                '}';
    }
}
