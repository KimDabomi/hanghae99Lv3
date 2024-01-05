package org.sparta.hanghae99lv3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String team;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StaffAuthEnum auth;

    public Staff(String email, String password, String team, StaffAuthEnum auth) {
        this.email = email;
        this.password = password;
        this.team = team;
        this.auth = auth;
    }
}
