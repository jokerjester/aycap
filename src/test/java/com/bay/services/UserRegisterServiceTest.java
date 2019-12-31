package com.bay.services;

import com.bay.exceptions.RegisterationException;
import com.bay.exceptions.SystemException;
import com.bay.exceptions.UserNotFoundException;
import com.bay.models.api.UserRegisterRequestModel;
import com.bay.models.api.UserRegisterResponseModel;
import com.bay.models.database.User;
import com.bay.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegisterService userRegisterService = new UserRegisterService();

    public void setGetUserInfoSuccessFixture() {
        User user = new User("username", "password", "address", "phone", new BigDecimal(15000), "Silver");
        when(userRepository.findByUsername(any())).thenReturn(user);
    }

    public void setGetUserInfoFailureFixture() {
        when(userRepository.findByUsername(any())).thenReturn(null);
    }

    public void setRegisterSuccessFixture() {
        User user = new User("username", "password", "address", "phone", new BigDecimal(15000), "");
        when(userRepository.countByUsername(any())).thenReturn(0L);
        when(userRepository.save(user)).thenReturn(user);
    }


    public void setRegisterFailureFixture() {
        when(userRepository.countByUsername(any())).thenReturn(1L);
    }


    @DisplayName("GetUserInfo should success when prompt a valid parameter")
    @Test
    public void testGetUserInfoSuccess() throws SystemException {
        setGetUserInfoSuccessFixture();
        assertEquals("username", userRegisterService.getUserInfo("username").getInfo().getUsername());
        verify(userRepository, times(1)).findByUsername("username");
    }

    @DisplayName("GetUserInfo should failure when prompt an invalid parameter")
    @Test
    public void testGetUserInfoFailure() {
        setGetUserInfoFailureFixture();
        assertThatThrownBy(() -> userRegisterService.getUserInfo("username").getClass().isInstance(UserNotFoundException.class));
        verify(userRepository, times(1)).findByUsername("username");
    }

    @DisplayName("Register should success when prompt a valid parameter (Silver)")
    @Test
    public void testRegisterSilverSuccess() throws SystemException {
        setRegisterSuccessFixture();
        UserRegisterResponseModel urm = userRegisterService.register(new UserRegisterRequestModel("username",
                "password",
                "address",
                "phone",
                new BigDecimal(15000))
        );
        assertEquals(HttpStatus.CREATED.value(), urm.getCode());
        assertEquals("Silver", urm.getMemberType());
        verify(userRepository, times(1)).countByUsername("username");
    }

    @DisplayName("Register should success when prompt a valid parameter (Gold)")
    @Test
    public void testRegisterGoldSuccess() throws SystemException {
        setRegisterSuccessFixture();
        UserRegisterResponseModel urm = userRegisterService.register(new UserRegisterRequestModel("username",
                "password",
                "address",
                "phone",
                new BigDecimal(30000))
        );
        assertEquals(HttpStatus.CREATED.value(), urm.getCode());
        assertEquals("Gold", urm.getMemberType());
        verify(userRepository, times(1)).countByUsername("username");
    }

    @DisplayName("Register should success when prompt a valid parameter (Platinum)")
    @Test
    public void testRegisterPlatinumSuccess() throws SystemException {
        setRegisterSuccessFixture();
        UserRegisterResponseModel urm = userRegisterService.register(new UserRegisterRequestModel("username",
                "password",
                "address",
                "phone",
                new BigDecimal(50001))
        );
        assertEquals(HttpStatus.CREATED.value(), urm.getCode());
        assertEquals("Platinum", urm.getMemberType());
        verify(userRepository, times(1)).countByUsername("username");
    }

    @DisplayName("Register should failure when prompt a duplicated user")
    @Test
    public void testRegisterFailure() {
        setRegisterFailureFixture();
        assertThatThrownBy(() -> userRegisterService.register(new UserRegisterRequestModel("username",
                "password",
                "address",
                "phone",
                new BigDecimal(15000))).getClass().isInstance(RegisterationException.class));
        verify(userRepository, times(1)).countByUsername("username");
    }
}
