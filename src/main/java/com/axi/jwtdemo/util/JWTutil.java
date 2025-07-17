package com.axi.jwtdemo.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
public class JWTutil {

    /**
     * 对输入的数据执行Base64Url编码
     */
    private static String base64UrlEncode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }


    /**
     * 使用 RSA 公钥验证签名
     *
     * @param content         header.payload 拼接内容
     * @param signatureBytes  Base64Url 编码的签名字符串
     * @param publicKeyBytes  PEM 格式的公钥字符串（含 BEGIN/END）
     * @return                是否验证通过
     */
    public static boolean verifySignature(String content, String signatureBytes, String publicKeyBytes) throws Exception {
        // Step 1: 解码 Base64Url 编码的签名
        byte[] decodedSignature = Base64.getUrlDecoder().decode(signatureBytes);

        // Step 2: 清理 PEM 格式
        String publicKeyPEM = publicKeyBytes;
        // Step 3: 解码 Base64 数据得到公钥字节数组
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKeyPEM);
        // Step 4: 构建 PublicKey 对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedPublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // Step 5: 初始化 Signature 并验证签名
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        return signature.verify(decodedSignature);
    }
    // 用rsasha256进行签名
    private static String signRsaSha256(String data, String privateKey) throws Exception {
        // 清理 PEM 格式
        String privateKeyPEM = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // 解码 Base64 数据得到私钥字节数组
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKeyPEM);

        // 构建 PrivateKey 对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKeyObj = keyFactory.generatePrivate(keySpec);

        // 初始化 Signature 并生成签名
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(privateKeyObj);
        signer.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signer.sign();

        return base64UrlEncode(signatureBytes);
    }
    public static String generateJwtTokenByRSA256(String headerJson, String payloadJson, String privateKey) throws Exception {
        String encodedHeader = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));

        String unsignedToken = encodedHeader + "." + encodedPayload;

        String encodedSignature = signRsaSha256(unsignedToken, privateKey);

        return unsignedToken + "." + encodedSignature;
    }

}
