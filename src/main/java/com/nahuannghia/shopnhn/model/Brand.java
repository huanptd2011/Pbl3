package com.nahuannghia.shopnhn.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @Column(name = "brand_id", length = 30, nullable = false)
    private String brandid;

    @Column(name = "brand_name", length = 100, nullable = false)
    private String brandName;

    @Column(name = "brand_description", length = 2000, nullable = false)
    private String brandDescription;

    @Column(name = "brand_image", length = 500)
    private String brandImage;

    @Column(name = "brand_address", length = 200)
    private String brandAddress;

    @Column(name = "brand_phone", length = 20,nullable = false)
    private String brandPhone;

    @Column(name = "brand_email", length = 100)
    private String brandEmail;
    public Brand() {
    }
    public Brand(String brandid, String brandName, String brandDescription, String brandImage, String brandAddress, String brandPhone, String brandEmail) {
        this.brandid = brandid;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
        this.brandImage = brandImage;
        this.brandAddress = brandAddress;
        this.brandPhone = brandPhone;
        this.brandEmail = brandEmail;
    }
    // Getters
    public String getBrandid() {
        return brandid;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public String getBrandAddress() {
        return brandAddress;
    }

    public String getBrandPhone() {
        return brandPhone;
    }

    public String getBrandEmail() {
        return brandEmail;
    }

    // Setters
    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

    public void setBrandAddress(String brandAddress) {
        this.brandAddress = brandAddress;
    }

    public void setBrandPhone(String brandPhone) {
        this.brandPhone = brandPhone;
    }

    public void setBrandEmail(String brandEmail) {
        this.brandEmail = brandEmail;
    }
}
