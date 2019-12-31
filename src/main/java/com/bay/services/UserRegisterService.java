package com.bay.services;

import com.bay.exceptions.RegisterationException;
import com.bay.exceptions.SystemException;
import com.bay.exceptions.UserNotFoundException;
import com.bay.models.api.UserInfoModel;
import com.bay.models.api.UserInfoResponseModel;
import com.bay.models.api.UserRegisterRequestModel;
import com.bay.models.api.UserRegisterResponseModel;
import com.bay.models.database.User;
import com.bay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserRegisterService {

    @Autowired
    private UserRepository repository;

    public UserRegisterResponseModel register(UserRegisterRequestModel request) throws SystemException {
        try {
            Long count = repository.countByUsername(request.getUsername());
            if (count > 0L) {
                throw new RegisterationException(HttpStatus.BAD_REQUEST, "username is duplicated");
            }
            User user = new User(
                    request.getUsername(),
                    request.getPassword(),
                    request.getAddress(),
                    request.getPhone(),
                    request.getSalary(),
                    decideMemberType(request.getSalary())
            );
            repository.save(user);
            return new UserRegisterResponseModel(HttpStatus.CREATED.value(), request.getUsername(), user.getMemberType());
        } catch (Exception ex) {
            throw ex;
        } finally {

        }
    }

    public UserInfoResponseModel getUserInfo(String username) throws SystemException {
        try {
            User user = repository.findByUsername(username);
            if (user == null) {
                throw new UserNotFoundException(HttpStatus.NOT_FOUND, "username is not exist");
            }
            UserInfoModel userInfo = new UserInfoModel(user.getUsername(), user.getMemberType());
            return new UserInfoResponseModel(HttpStatus.OK.value(), userInfo);
        } catch (Exception ex) {
            throw ex;
        } finally {

        }
    }

    private String decideMemberType(BigDecimal salary) {
        if (salary.compareTo(new BigDecimal(50000)) > 0)
            return "Platinum";
        else if (salary.compareTo(new BigDecimal(30000)) >= 0 && salary.compareTo(new BigDecimal(50000)) <= 0)
            return "Gold";
        else
            return "Silver";
    }
}
