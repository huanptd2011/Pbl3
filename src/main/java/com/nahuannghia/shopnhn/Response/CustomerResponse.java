package com.nahuannghia.shopnhn.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String customerId;
    private String gender;
    private LocalDateTime userDob;
    private String avatar;
    private String status;
}
