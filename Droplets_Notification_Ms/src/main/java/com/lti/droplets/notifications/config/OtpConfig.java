package com.lti.droplets.notifications.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class OtpConfig {

    @Value("${droplets.notifications.otpSize}")
    private String otpSize;

    @Value("${droplets.notifications.expiryTime}")
    private String expiryTime;


    @Bean(name = "getMaxOtpValue")
    public int getMaxOtpValue(){
        int otpSizeInt = Integer.parseInt(otpSize);
        char arr[] = new char[otpSizeInt];
        for(int i=0;i<otpSizeInt;i++){
            arr[i]='9';
        }
        return Integer.parseInt(String.valueOf(arr));
    }
}
