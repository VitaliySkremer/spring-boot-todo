package ru.skremer.todoapplication.controller.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreate {
    private String name;
    private String password;
    private  String email;
}
