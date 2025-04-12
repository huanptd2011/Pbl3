package com.nahuannghia.shopnhn.service.interfaces;

import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;

public interface IUserService {
    LoginResponse authenticate(LoginRequest request) ;
}
