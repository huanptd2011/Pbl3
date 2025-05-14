package com.nahuannghia.shopnhn.dto;


import java.io.Serializable;

public class VNPayResDTO implements Serializable {
    private String status;
    private String message;
    private String URL;

    public VNPayResDTO() {
    }

    public VNPayResDTO(String status, String message, String url) {
        this.status = status;
        this.message = message;
        this.URL = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return URL;
    }

    public void setUrl(String url) {
        this.URL = url;
    }
}
