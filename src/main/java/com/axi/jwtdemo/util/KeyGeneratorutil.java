package com.axi.jwtdemo.util;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyGeneratorutil {

    public static void main(String[] args) {
        try {
            // 创建KeyPairGenerator实例并指定算法为RSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

            // 初始化密钥长度（建议至少2048位）
            keyGen.initialize(2048);

            // 生成密钥对
            KeyPair pair = keyGen.generateKeyPair();
            PublicKey pubKey = pair.getPublic();
            PrivateKey privKey = pair.getPrivate();

            // 将公钥编码为字节数组并保存
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pubKey.getEncoded());
            try (FileOutputStream fos = new FileOutputStream("public.key")) {
                fos.write(Base64.getEncoder().encode(publicKeySpec.getEncoded()));
            }

            // 将私钥编码为字节数组并保存
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privKey.getEncoded());
            try (FileOutputStream fos = new FileOutputStream("private.key")) {
                fos.write(Base64.getEncoder().encode(privateKeySpec.getEncoded()));
            }

            System.out.println("公钥和私钥已成功生成并保存！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}