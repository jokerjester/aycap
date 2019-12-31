package com.bay.models.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequestModel {

    @NotNull(message = "must be provided in the request body")
    private String username;
    @NotNull(message = "must be provided in the request body")
    private String password;
    @NotNull(message = "must be provided in the request body")
    private String address;
    @Size(min = 10, message = " length must be at least 10 characters")
    @NotNull(message = "must be provided in the request body")
    private String phone;
    @DecimalMin("15000.00")
    private BigDecimal salary;

    public UserRegisterRequestModel(String username, String password, String address, String phone, BigDecimal salary) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.salary = salary;
    }
}
