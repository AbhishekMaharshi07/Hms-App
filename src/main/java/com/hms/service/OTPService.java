package com.hms.service;

import com.hms.util.OTPUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class OTPService {

//    @Autowired
//    private OTPUtil otpUtil;
//
//    @Autowired
//    private TwilioService twilioService;
//
//    // Temporary storage for OTPs (In a real app, use a  persistent store like Real
//    private  final Map<String, OTPService> otpservices = new HashMap<>();
//    private static final long OTP_EXPIRY_TIME = 5 * 60 * 1000; // OTP Expiry in 5 minutes
//
//
////    Twilio credentials for SMS sending
////    private final String ACCOUNT_SID = "AC2cf27dec18267960b6e23be888bb9c5a";
////    private final String AUTH_TOKEN = "30fc4a4b06ffdb89320bd893928c73eb";
////    private final String FROM_PHONE = "+18582030941";
//
//    //Generate OTP and send via SMS
//    public String generateAndSendOTP(String mobileNumber) {
//        // Generate OTP
//        String otp = otpUtil.generateOTP();
//
//        // Store OTP and expiry time
//        otpStorage.put(mobileNumber, new OTPData(otp, System.currentTimeMillis()+ OTP_EXPIRY_TIME));
//
//        // Send OTP via SMS
//        twilioService.sendSms(mobileNumber,"Your otp number is: "+ otp);
//
//        // return the OTP (in a real application, you won't return
//        return otp;
//
//
//    }
//
//    //Validate the OTP entered by the user
//    public boolean validateOTP(String mobileNumber, String otp) {
//        OTPData storedOTPData = otpStorage.get(mobileNumber);
//        if (storedOTPData == null){
//            return false; // OTP not found
//        }
//        // Check if OTP expired
//        if (System.currentTimeMillis() > storedOTPData.getExpiryTime){
//            otpStorage.remove(mobileNumber); // OTP expired
//            return false;
//        }
//        //Validate the OTP
//        if (storedOTPData.getOtp().equals(otp)){
//            otpStorage.remove(mobileNumber); // OTP validated successfully
//            return true;
//        }
//        return false; // Invalid OTP
//    }

}
