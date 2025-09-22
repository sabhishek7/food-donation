package com.fooddonation.app.dto;


import com.fooddonation.app.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Role role;
}
