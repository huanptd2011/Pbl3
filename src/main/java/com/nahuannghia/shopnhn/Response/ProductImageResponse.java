package com.nahuannghia.shopnhn.Response;

public class ProductImageResponse {
    private String imageUrl;
        
    
        // Default constructor
        public ProductImageResponse() {
        }
    
        // Constructor with imageUrl
        public ProductImageResponse(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    
        // Getter and setter for imageUrl
        public String getImageUrl() {
            return imageUrl;
        }
    
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
