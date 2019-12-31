package com.bay.models.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoModel {
    private String username;
    private String memberType;

    public UserInfoModel(String username, String memberType) {
        this.username = username;
        this.memberType = memberType;
    }
}
