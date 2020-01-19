package com.treeyh.common.test.utils;

import com.treeyh.common.test.BaseTest;
import com.treeyh.common.utils.RSAUtils;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 描述
 * @create 2020-01-19 13:56
 */
public class RSAUtilsTest extends BaseTest {

    @Test
    public void testRSA(){

        String content = "abcdefg456+-=";   // 明文内容
        System.out.println("原始字符串是：" + content);
        try {
            // 获得密钥对
            KeyPair keyPair =  RSAUtils.getKeyPair();
            // 获得进行Base64 加密后的公钥和私钥 String
            String privateKeyStr = RSAUtils.getPrivateKey(keyPair);
            String publicKeyStr = RSAUtils.getPublicKey(keyPair);
            System.out.println("Base64处理后的私钥：" + privateKeyStr + "\n"
                    + "Base64处理后的公钥：" + publicKeyStr);

            // 获得原始的公钥和私钥，并以字符串形式打印出来
            PrivateKey privateKey = RSAUtils.string2Privatekey(privateKeyStr);
            PublicKey publicKey = RSAUtils.string2PublicKey(publicKeyStr);

            // 公钥加密/私钥解密
            byte[] publicEncryBytes =  RSAUtils.publicEncrytype(content.getBytes(), publicKey);
            System.out.println("公钥加密后的字符串(经BASE64处理)：" + Base64.getEncoder().encodeToString(publicEncryBytes));
            byte[] privateDecryBytes = RSAUtils.privateDecrypt(publicEncryBytes, privateKey);
            System.out.println("私钥解密后的原始字符串：" + new String(privateDecryBytes));

            String privateDecryStr = new String(privateDecryBytes, "utf-8");
            if (content.equals(privateDecryStr)){
                System.out.println("测试通过！");
            }else {
                System.out.println("测试未通过！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

