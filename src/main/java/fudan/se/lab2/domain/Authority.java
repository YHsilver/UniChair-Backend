package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * @author LBW
 * 管理用户权限，分chair、PC members、author
 */

@Entity
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = -8974777274465208640L;

    // 3个属性
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private Set<User> users;

    // empty constructor
    public Authority() {
    }

    // constructor
    public Authority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
