package com.nahuannghia.shopnhn.Response;

/**
 * Response for order data (counts by status or time)
 */
public class OrderDataResponse {
    private String label;
    private long count;
    
    public OrderDataResponse(String label, long count) {
        this.label = label;
        this.count = count;
    }
    
    // Getters and setters
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public long getCount() {
        return count;
    }
    
    public void setCount(long count) {
        this.count = count;
    }
}
