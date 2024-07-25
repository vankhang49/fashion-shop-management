//package com.codegym.fashionshop.dto.respone;//package com.codegym.shopdemo.dto.respone;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//public class JwtRespone {
//    @Getter
//    @Setter
//    private Long id;
//    private String token;
//    private String type = "Bearer";
//    @Setter
//    @Getter
//    private String username;
//    @Getter
//    private Collection<? extends GrantedAuthority> roles;
//
//    public JwtRespone(String accessToken, Long id, String username, Collection<? extends GrantedAuthority> roles) {
//        this.token = accessToken;
//        this.id = id;
//        this.username = username;
//        this.roles = roles;
//    }
//
//    public String getAccessToken() {
//        return token;
//    }
//
//    public void setAccessToken(String accessToken) {
//        this.token = accessToken;
//    }
//
//    public String getTokenType() {
//        return type;
//    }
//
//    public void setTokenType(String tokenType) {
//        this.type = tokenType;
//    }
//
//}
