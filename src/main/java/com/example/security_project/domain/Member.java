package com.example.security_project.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "member")
public class Member {
    @Id
    private String email;

    private String password;

    private String nickName;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_role", joinColumns = @JoinColumn(name = "email", referencedColumnName = "email"))
    @Builder.Default
    @Enumerated(EnumType.STRING)
    List<MemberRole> roles = new ArrayList<>();

    public void addRole(MemberRole role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    };

    public void removeRole() {
        this.roles.clear();
    }
}
