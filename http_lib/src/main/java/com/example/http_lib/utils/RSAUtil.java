package com.example.http_lib.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Logger;

import javax.crypto.Cipher;

public class RSAUtil {

//    private static final Logger LOGGER= LoggerFactory.getLogger(RSAUtil.class);
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小（如果秘钥是1024bit,解密最大块是128,如果秘钥是2048bit,解密最大块是256）
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static PrivateKey globalPrivateKey = null;
    private static PublicKey globalPublicKey = null;

    public static PrivateKey getPrivateKey(String prikeypath) throws Exception {
        if(globalPrivateKey != null){
            return globalPrivateKey;
        }
//        InputStream in = RSAUtil.class.getClassLoader().getResourceAsStream(prikeypath);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        StringBuilder sb = new StringBuilder();
//        String readLine = null;
//
//        while((readLine = br.readLine()) != null) {
//            if(readLine.charAt(0) != 45) {
//                sb.append(readLine);
//                sb.append('\r');
//            }
//        }
//
//        in.close();

//        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(sb.toString()));
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(RSAUtils.PublicKey));

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            globalPrivateKey = privateKey;
            return globalPrivateKey;
        } catch (Exception e) {
            Log.e("error","获取私钥异常",e);
            throw e;
        }
    }

    public static PublicKey getPublicByPublic(String keypath) throws Exception {
        if(globalPublicKey != null){
            return globalPublicKey;
        }
//        InputStream in = RSAUtil.class.getClassLoader().getResourceAsStream(keypath);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        StringBuilder sb = new StringBuilder();
//        String readLine = null;
//
//        while((readLine = br.readLine()) != null) {
//            if(readLine.charAt(0) != 45) {
//                sb.append(readLine);
//                sb.append('\r');
//            }
//        }
//
//        in.close();
//        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

//        X509EncodedKeySpec ks = new X509EncodedKeySpec(Base64.decode(sb.toString()));
        X509EncodedKeySpec ks = new X509EncodedKeySpec(Base64.decode(RSAUtils.PublicKey));


        KeyFactory kf = KeyFactory.getInstance("RSA");
        globalPublicKey = kf.generatePublic(ks);
        return globalPublicKey;
    }

    public static String dencrypt(String str,String privateKeyStr) throws Exception{
        //64位解码加密后的字符串
        byte[] encryptedData = Base64.decode(str);
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String outStr = new String(decryptedData,"UTF-8");
        return outStr;
    }

    public static String encrypt(String data, String publicKeyPath) throws Exception{
        PublicKey pubKey = getPublicByPublic(publicKeyPath);

        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] dataByte = data.getBytes("UTF-8");
        int inputLen = dataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        String outStr = Base64.encode(encryptedData);
        return outStr;
    }

    public static void main(String[] args) throws Exception {
        //现在生成公私钥对,http://web.chacuo.net/netrsakeypair
        String data = "我是打酱油的";
        //使用公钥加密,第二个参数classpath下的公钥文件
        data = encrypt(data,"public1024.key");
//        System.out.println("加密后的数据:"+data);
        //使用私钥解密,第二个参数classpath下的私钥文件
        data = dencrypt(data,"private1024.key");
//        System.out.println("解密后的数据:"+data);
    }
}
