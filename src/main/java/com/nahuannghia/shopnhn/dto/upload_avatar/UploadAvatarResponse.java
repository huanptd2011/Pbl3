package com.nahuannghia.shopnhn.dto.upload_avatar;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadAvatarResponse {
    private int status;
   
    private String message;
    private String newAvatarUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

}
