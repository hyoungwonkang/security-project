package com.example.security_project.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.security_project.domain.MemberRole;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// import org.springframework.security.core.userdetails.User;

// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;

// import org.springframework.security.core.authority.SimpleGrantedAuthority;

// @ToString
// @Getter @Setter
// public class MemberDto extends User {
    
//     private String email;
//     private String password;
//     private String nickName;
//     private List<String> roleNames = new ArrayList<>();

//     // constructor method
//     public MemberDto(String email, String password, String nickName, List<String> roleNames) {
//         super(email, password,
//             roleNames.stream().map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toList()));
//         this.email = email;
//         this.password = password;
//         this.nickName = nickName;
//         this.roleNames = roleNames;
//     }

//     // 클레임
//     public Map<String, Object> getClaims() {
//         Map<String, Object> map = new HashMap<>();
//         map.put("email", email);
//         map.put("password", password);
//         map.put("nickName", nickName);
//         map.put("roleNames", roleNames);
//         return map;
//     }
// }

@ToString
@Getter @Setter
public class MemberDto extends User {
    private String email;
    private String password;
    private String nickName;
    private List<String> roleNames = new ArrayList<>();

    public MemberDto(String email, String password, String nickName, List<String> roleNames) {
        super(email, password,
            roleNames.stream().map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toList()));
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("nickName", nickName);
        map.put("roleNames", roleNames);
        return map;
    }
}