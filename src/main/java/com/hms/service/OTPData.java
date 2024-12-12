package com.hms.service;

import lombok.Data;

// Helper class to store  OTP and expiry time
@Data
public class OTPData {
    private final String otp;
    private final long expiryTime;


    public OTPData(String otp, long expiryTime) {
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

}
