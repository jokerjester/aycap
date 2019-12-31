package com.bay.controllers;

import com.bay.exceptions.RegisterationException;
import com.bay.exceptions.SystemException;
import com.bay.exceptions.UserNotFoundException;
import com.bay.models.api.UserInfoModel;
import com.bay.models.api.UserInfoResponseModel;
import com.bay.models.api.UserRegisterRequestModel;
import com.bay.models.api.UserRegisterResponseModel;
import com.bay.models.database.User;
import com.bay.services.UserRegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRegisterService userRegisterService;


    public void setGetUserInfoRouteSuccessFixture() throws SystemException {
        User user = new User("username", "password", "address", "phone", new BigDecimal(15000), "Silver");
        UserInfoModel userInfoModel = new UserInfoModel(user.getUsername(), user.getMemberType());
        UserInfoResponseModel userInfoResponseModel = new UserInfoResponseModel(200,userInfoModel );
        when(userRegisterService.getUserInfo(any())).thenReturn(userInfoResponseModel);
    }

    public void setGetUserInfoRouteFailureFixture() throws SystemException {
        when(userRegisterService.getUserInfo(any())).thenThrow(new UserNotFoundException(HttpStatus.NOT_FOUND, "username is not exist"));
    }

    public void setRegisterRouteFailureFixture() throws SystemException {
        when(userRegisterService.register(any())).thenThrow(new RegisterationException(HttpStatus.BAD_REQUEST, "username is duplicated"));
    }

    public void setRegisterSilverRouteSuccessFixture() throws SystemException {
        when(userRegisterService.register(any())).thenReturn(new UserRegisterResponseModel(HttpStatus.CREATED.value(), "someone","Silver"));
    }

    public void setRegisterGoldRouteSuccessFixture() throws SystemException {
        when(userRegisterService.register(any())).thenReturn(new UserRegisterResponseModel(HttpStatus.CREATED.value(), "someone","Gold"));
    }

    public void setRegisterPlatinumRouteSuccessFixture() throws SystemException {
        when(userRegisterService.register(any())).thenReturn(new UserRegisterResponseModel(HttpStatus.CREATED.value(), "someone","Platinum"));
    }

    @DisplayName("getUserInfo route should be success when provide an existing username")
    @Test
    public void getUserInfoRouteSuccess() throws Exception {
        setGetUserInfoRouteSuccessFixture();
        mockMvc.perform(get("/api/v1/users/{username}", "someone")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @DisplayName("getUserInfo route should be failure when provide a non-existing username")
    @Test
    public void getUserInfoRouteFailed() throws Exception {
        setGetUserInfoRouteFailureFixture();
        mockMvc.perform(get("/api/v1/users/{username}", "noone")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("userRegister route should be failure when provide an existing user information")
    @Test
    public void userRegisterRouteFailed() throws Exception {
        setRegisterRouteFailureFixture();
        UserRegisterRequestModel request = new UserRegisterRequestModel("someone", "password", "address", "phone", new BigDecimal(15000));
        mockMvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("userRegister route should be failure when provide a user information with salary < 15000 ")
    @Test
    public void userRegisterRouteFailedSalaryReject() throws Exception {
        UserRegisterRequestModel request = new UserRegisterRequestModel("someone", "password", "address", "0888888888", new BigDecimal(14000));
        mockMvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("userRegister route should be success and member type correctly decided (Silver) when provide a valid request body")
    @Test
    public void userRegisterRouteSuccess() throws Exception {
        setRegisterSilverRouteSuccessFixture();
        UserRegisterRequestModel request = new UserRegisterRequestModel("someone", "password", "address", "0888888888", new BigDecimal(15000));
        mockMvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"code\":201,\"username\":\"someone\",\"member_type\":\"Silver\"}"));
    }

    @DisplayName("userRegister route should be success and member type correctly decided (Gold) when provide a valid request body")
    @Test
    public void userRegisterGoldRouteSuccess() throws Exception {
        setRegisterGoldRouteSuccessFixture();
        UserRegisterRequestModel request = new UserRegisterRequestModel("someone", "password", "address", "0888888888", new BigDecimal(15000));
        mockMvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"code\":201,\"username\":\"someone\",\"member_type\":\"Gold\"}"));
    }

    @DisplayName("userRegister route should be success and member type correctly decided (Platinum) when provide a valid request body")
    @Test
    public void userRegisterPlatinumRouteSuccess() throws Exception {
        setRegisterPlatinumRouteSuccessFixture();
        UserRegisterRequestModel request = new UserRegisterRequestModel("someone", "password", "address", "0888888888", new BigDecimal(15000));
        mockMvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"code\":201,\"username\":\"someone\",\"member_type\":\"Platinum\"}"));
    }
}
