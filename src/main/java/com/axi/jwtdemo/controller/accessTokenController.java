package com.axi.jwtdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.axi.jwtdemo.util.JWTutil.generateJwtTokenByRSA256;
import static com.axi.jwtdemo.util.JWTutil.verifySignature;

@RestController
public class accessTokenController {


    @GetMapping("/access_Token/{con}")
    public boolean accessToken(@PathVariable String con){
        try {
            // 示例 Header 和 Payload JSON 字符串
            String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\",\"kid\":\"123\"}";
            String payloadJson = "{\"iss\":\"https://example.com\",\"exp\":1737234200,\"iat\":1737230600,\"sub\":\"1234567890\",\"username\":\"john_doe\"}";

            // 密钥 private.key中
            String secretKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCj1dLPLXCbiwiRVSJu5PLXpw04+EINFgxUbDeZmiRJgtagRVsUONO/jBt6PiLAmzh+hc77Lu1j5F0grv2LKVudGNfApHi3FjSJo/aTpSMxEevadI02GFEVrOtP3qbePe/KK/lut1G+xbB/N9jGlwx8orDvhBJ0FLpS099PirWvRHmgovvBFnBBAMumYD8jZ+rtERVPWdGgHsNdH5PrNqjDAjiAOzNH4FuqPAdMQb/c0pXxtKjJ6oPFySqfN2bwuaLW1szwbJqp4EzAIVvQnghWcFb0CelSQRWPHegLEouVK028vXh+UaXSpZtjQIpKnWrJKR9Qih8kk3PgPOF9p7eXAgMBAAECggEALzOgCEuFqXKNld2KzZtNsb7xysuPsBh9lecVv73OnZVCA6vnuTREIWga1IXJWmd+B8nCX9Dd2Q1GnW99IdZZDDJHmrtoAgu2OD+Sf2gw8ubAZlB1DLRFzDJlGz5BIj+C8eLoerL7LLTlDEoJPPHwyYnP4znQXTxJkCp1qIOEo8lm7iSoNaBuhm8Qya1QKqt/VU8GFxnsR2onkEt7eQjpUzwRfdbzoOLmHEwbUyBgQ8hGg8jf/QqQYuvOBmP+gT18wjbwclaLFkQ+dls7QRTiWTEdWq/4GtrXwYV2vZSuc6czmosDMoIwb+5bu272pdZEQLJTTW+WW1X5PRJCeeHcDQKBgQDAyCLKtVS1DuoSI4c7FPqHvMJiqDiuh7BUEU1bZWO8T0O/LVBvo2WKodmgF6/+tHdcsOraMPVbly6fYRMOprvrVstYp0JopMEhfmlxKj/H6nu4ePgHc5Ve05eOb7VwHc7+wQuhqiA4DGRlQ7ME6EaaQY55V9dZ/tcOXsfhhcvfuwKBgQDZj6aveQOTJXwyxdSzHSY6GmK7RjHQuzZrTdBI/S982G+P1q8Btt8DEtW4nNfU792I4TzyW8Z9jvrZTvetvJedz7QaYlQ9kv9dcPIpTFwhd5pCb76Mo2bPLgxepmM6eCwH6hFAh1b3LcgPO/fe7O0GcBKcl6gb/uvEnAEMk1Aj1QKBgF6MSXP9hvPdwrLutc4mNdEIpJVaR6JhbfttGPFwQoACqAlFIiP+kThytJctxuU2gSv2oNguFse61+TW6U/QOm3rfYOXVJyZ4RaaNwCHZgyYVeFdgte0uldtkvEKnIPGOtT6vMem9nJGsZ5wm4YWnTneWQ/uIvJCaBrmJUXYJustAoGAcafO/2pryKpTiL660FuRBdeU7prMGWxEEKiVABxse38iQPV5dZ7QQG3NguVFB7PLet+YFFCHkD5qo63STEvytPm17/agZY5uo6UGjTacBsvRpZV06UnPwroesi+gNQJkljnci2ZoszaShgrhAe+qH6vavT2pD/8LsimBjs1Cl4UCgYBZAPTh3cVYRSeBiK7gfGInkCK7+RdDWDbqFe8v7rH+ufNrXckaxyb0g7n7hE5HYh68Ln4KkDYp61u5KkfXvRBWTTSdCfx2fPICc22VaEygbHfI/W5RyAhpEIOdZ4vHoxSsTF1DB8KvzDS9xrMZoywnfoymNcosqtoB6r9LwX+NeA==";
            // 生成 JWT Token
            String jwtToken = generateJwtTokenByRSA256(headerJson, payloadJson, secretKey);
            System.out.println("生成的JWT Token:");
            System.out.println(jwtToken);
            String falseToken = jwtToken.replace('a','e');
            // 读取公钥文件内容
            String publickey  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo9XSzy1wm4sIkVUibuTy16cNOPhCDRYMVGw3mZokSYLWoEVbFDjTv4wbej4iwJs4foXO+y7tY+RdIK79iylbnRjXwKR4txY0iaP2k6UjMRHr2nSNNhhRFazrT96m3j3vyiv5brdRvsWwfzfYxpcMfKKw74QSdBS6UtPfT4q1r0R5oKL7wRZwQQDLpmA/I2fq7REVT1nRoB7DXR+T6zaowwI4gDszR+BbqjwHTEG/3NKV8bSoyeqDxckqnzdm8Lmi1tbM8GyaqeBMwCFb0J4IVnBW9AnpUkEVjx3oCxKLlStNvL14flGl0qWbY0CKSp1qySkfUIofJJNz4Dzhfae3lwIDAQAB";
            // Step 4: 验证签名
            // 提取 token 各部分
            String[] parts;
            if(con.equals("1")){
                parts = jwtToken.split("\\.");
            }else{
                parts = falseToken.split("\\.");
            }
            if (parts.length != 3) {
                throw new IllegalArgumentException("非法JWT格式");
            }
            String headerAndPayload = parts[0] + "." + parts[1];
            String signaturePart = parts[2];
            // 验证签名
            boolean isValid = verifySignature(headerAndPayload, signaturePart, publickey);
            System.out.println("签名是否有效: " + isValid);
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
