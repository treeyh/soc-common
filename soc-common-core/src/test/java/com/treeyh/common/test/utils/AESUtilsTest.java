package com.treeyh.common.test.utils;

import com.treeyh.common.test.BaseTest;
import com.treeyh.common.utils.AESUtils;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 测试 AESUtils 对AES加密算法的封装
 * @create 2020-01-19 13:55
 */
public class AESUtilsTest extends BaseTest {


    @Test
    public void testAES(){
        String content = "abcdefg789+-*+="; // 待加密的字符串
        System.out.println("明文数据为：" + content);
        try {
            // 获得经 BASE64 处理之后的 AES 密钥
            String strKeyAES = AESUtils.getStrKeyAES();
            System.out.println("经BASE64处理之后的密钥：" + strKeyAES);

            // 将 BASE64 处理之后的 AES 密钥转为 SecretKey
            SecretKey secretKey = AESUtils.strKey2SecretKey(strKeyAES);

            // 加密数据
            byte[] encryptAESbytes = AESUtils.encryptAES(content.getBytes("utf-8"), secretKey);
            System.out.println("加密后的数据经 BASE64 处理之后为：" + Base64.getEncoder().encodeToString(encryptAESbytes));
            // 解密数据
            String decryptAESStr = new String(AESUtils.decryptAES(encryptAESbytes, secretKey), "utf-8");
            System.out.println("解密后的数据为：" + decryptAESStr);

            if (content.equals(decryptAESStr)){
                System.out.println("测试通过！");
            }else {
                System.out.println("测试未通过！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
