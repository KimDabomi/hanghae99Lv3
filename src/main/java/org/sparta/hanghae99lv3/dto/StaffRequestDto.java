package org.sparta.hanghae99lv3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRequestDto {
    private String email;
    private String password;
    private String team;
    private String auth;
}
