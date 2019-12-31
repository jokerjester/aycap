package com.bay.models.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResponseModel {
    private int code;
    private UserInfoModel info;

    public UserInfoResponseModel(int code, UserInfoModel info) {
        this.code = code;
        this.info = info;
    }
}
