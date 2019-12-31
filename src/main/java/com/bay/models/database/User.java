package com.bay.models.database;

import com.bay.utilities.DatetimeUtils;
import com.bay.utilities.StringUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Entity
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private String address;
    private String phone;
    private BigDecimal salary;
    private String referenceCode;
    private String memberType;

    public User(String username, String password, String address, String phone, BigDecimal salary, String memberType) {
        this.username = username;
        this.password = StringUtils.hash(password);
        this.address = address;
        this.phone = phone;
        this.salary = salary;
        this.referenceCode = DatetimeUtils.currentDatetimeToString("YYYYMMDD").concat(phone.substring(phone.length() - 4));
        this.memberType = memberType;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", salary=" + salary +
                ", referenceCode='" + referenceCode + '\'' +
                ", memberType='" + memberType + '\'' +
                '}';
    }
}
