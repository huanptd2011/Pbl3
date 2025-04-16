package com.nahuannghia.shopnhn.service.user_service;



import org.springframework.web.multipart.MultipartFile;

import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordRequest;
import com.nahuannghia.shopnhn.dto.change_password.ChangePasswordResponse;
import com.nahuannghia.shopnhn.dto.create.RegisterRequest;
import com.nahuannghia.shopnhn.dto.create.RegisterResponse;
import com.nahuannghia.shopnhn.dto.delete.DeleteUserResponse;
import com.nahuannghia.shopnhn.dto.login.LoginRequest;
import com.nahuannghia.shopnhn.dto.login.LoginResponse;
import com.nahuannghia.shopnhn.dto.logout.LogoutResponse;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserRequest;
import com.nahuannghia.shopnhn.dto.update_account_info.UpdateUserResponse;
import com.nahuannghia.shopnhn.dto.upload_avatar.UploadAvatarResponse;
import com.nahuannghia.shopnhn.dto.user_info.UserInfoResponse;
import com.nahuannghia.shopnhn.exeption.UserNotFoundException;


public interface UserService {

    UserInfoResponse getUserInfo(Long UserId) throws UserNotFoundException;
    ChangePasswordResponse changePassword(Long UserId, ChangePasswordRequest request);
    RegisterResponse createUser(RegisterRequest dto);
    DeleteUserResponse deleteUser(Long UserId) throws UserNotFoundException;
    LoginResponse login(LoginRequest dto) throws UserNotFoundException;
    LogoutResponse logout(String token) ;
    UpdateUserResponse updateUserInfo(Long UserId, UpdateUserRequest UserInfo) throws UserNotFoundException;
    UploadAvatarResponse uploadAvatar(Long UserId, MultipartFile file) throws UserNotFoundException;
}
