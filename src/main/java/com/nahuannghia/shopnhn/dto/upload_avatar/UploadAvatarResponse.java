package com.nahuannghia.shopnhn.dto.upload_avatar;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


public class UploadAvatarResponse {
    private int status;
   
    private String message;
    private String newAvatarUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    //
    public int getStatus() {
        return status;
    }
    public UploadAvatarResponse(int status, String message, String newAvatarUrl, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.newAvatarUrl = newAvatarUrl;
        this.timestamp = timestamp;
    }
    public UploadAvatarResponse() {
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getNewAvatarUrl() {
        return newAvatarUrl;
    }
    public void setNewAvatarUrl(String newAvatarUrl) {
        this.newAvatarUrl = newAvatarUrl;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    //

}
