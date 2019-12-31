package com.bay.controllers;

import com.bay.exceptions.SystemException;
import com.bay.models.api.UserInfoResponseModel;
import com.bay.models.api.UserRegisterRequestModel;
import com.bay.models.api.UserRegisterResponseModel;
import com.bay.services.UserRegisterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@NoArgsConstructor
public class UserController {

    @Autowired
    UserRegisterService userRegisterService;

    @ApiOperation(value = "user registration", notes = "user registration")
    @PostMapping("/api/v1/users")
    @ResponseBody
    public UserRegisterResponseModel userRegister(@Valid @RequestBody UserRegisterRequestModel req, HttpServletResponse response) throws SystemException {
        response.setStatus(HttpStatus.CREATED.value());
        return userRegisterService.register(req);
    }

    @ApiOperation(value = "get user information", notes = "get user information by username")
    @GetMapping("/api/v1/users/{username}")
    @ResponseBody
    public UserInfoResponseModel userRegister(@ApiParam(value = "username", required = true, defaultValue = "200") @PathVariable(name = "username") String username) throws SystemException {
        return userRegisterService.getUserInfo(username);
    }
}
