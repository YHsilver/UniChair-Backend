package fudan.se.lab2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author LBW
 * 这个类是用户
 */

@Entity
public class User implements UserDetails {

    // 这是干啥的？？？
    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username; // 'username' is better than 'name'
    private String password;
    private String fullname;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    private ArrayList<Long> conferencesId = new ArrayList<>();

    // empty constructor
    public User() {
    }

    // constructor
    public User(String username, String password, String fullname, Set<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.authorities = authorities;
    }

    public ArrayList<Long> getConferencesId() {
        return conferencesId;
    }

    public void setConferencesId(ArrayList<Long> conferencesId) {
        this.conferencesId = conferencesId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    // 这个要修改！！！！！！！！！
    public String toJsonObject() {
        return "{" +
                "\"id\":" + id +
                ", \"username\":\"" + username + '\"' +
                ", \"fullname\":\"" + fullname + '\"' +
                '}';
    }

}
