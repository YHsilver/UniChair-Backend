package fudan.se.lab2.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LBW
 * JWT 的属性
 */

@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtConfigProperties {
    // 有效时间
    private int validity;

    // 秘密？
    private String secret;

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
