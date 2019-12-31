package com.bay.models.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserRegisterResponseModel {
    private int code;
    private String username;
    private String memberType;

    public UserRegisterResponseModel(int code, String username, String memberType) {
        this.code = code;
        this.username = username;
        this.memberType = memberType;
    }
}
