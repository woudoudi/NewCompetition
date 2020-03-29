package com.example.http_lib.utils;

import android.os.Build;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSACoderUtil {

    public static final String RSA_ALGORITHM = "RSA";
    private static final String CIPHER = "RSA/ECB/PKCS1Padding";

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj4eKl68pCvhrpg7SIAq4YMjE+kAnqnGEi6ZeXMk/UVBFs6H4mCOQC16vlgwFVcDqim5pIzr8WGuApEVwbrJRz68VA5rfGoI208iteF+RNkdQn/j0im3LBDwV8z5m8Fp2CzrGJ+uzCt0GVVKfHnHd+tHECz9IvLZcgIzce/fikqwIDAQAB";

    private static String encPri;
    private static String encPub;

    public static byte[] string2bytes(String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return s.getBytes(StandardCharsets.UTF_8);
        }
        return s.getBytes(Charset.forName("UTF-8"));
    }

    public static String bytes2String(byte[] bs) {
        if (bs.length == 0) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new String(bs, StandardCharsets.UTF_8);
        }
        return new String(bs, Charset.forName("UTF-8"));
    }

    private static byte[] decryptBASE64(String key) {
        return Base64.decode(key,Base64.NO_WRAP);
    }

    private static String encryptBASE64(byte[] key) {
        return Base64.encodeToString(key,Base64.NO_WRAP);
    }

    public static Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = encryptBASE64(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = encryptBASE64(privateKey.getEncoded());

        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decryptBASE64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decryptBASE64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return encryptBASE64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, string2bytes(data),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            final String decrypted = bytes2String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE,
                    decryptBASE64(data), privateKey.getModulus().bitLength()));
            return decrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return encryptBASE64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE,
                    string2bytes(data), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return bytes2String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, decryptBASE64(data),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte[] resultDatas = out.toByteArray();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultDatas;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Map<String, String> keyMap = RSACoderUtil.createKeys(1024);
//        String publicKey = keyMap.get("publicKey");
//        String privateKey = keyMap.get("privateKey");

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj4eKl68pCvhrpg7SIAq4YMjE+kAnqnGEi6ZeXMk/UVBFs6H4mCOQC16vlgwFVcDqim5pIzr8WGuApEVwbrJRz68VA5rfGoI208iteF+RNkdQn/j0im3LBDwV8z5m8Fp2CzrGJ+uzCt0GVVKfHnHd+tHECz9IvLZcgIzce/fikqwIDAQAB";
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKPh4qXrykK+GumDtIgCrhgyMT6QCeqcYSLpl5cyT9RUEWzofiYI5ALXq+WDAVVwOqKbmkjOvxYa4CkRXBuslHPrxUDmt8agjbTyK14X5E2R1Cf+PSKbcsEPBXzPmbwWnYLOsYn67MK3QZVUp8ecd360cQLP0i8tlyAjNx79+KSrAgMBAAECgYA4o4brhTRgIMe4UNaB8zssDZzLXIiw1t8Ta9wCW1cPsQZct3Vxu7zh5pv4NHCvkJwTOuf1pc6Q2zUBvIgy4FBluaxiFJ0/VLu/gtylxjRlPqv5QpIrd2ru73yi0Eb3YUrXvHHzdl8ZeRuKsOvdTHTq8y1lO4JKc24RMBqGb44P8QJBAOE4I7p8lpDPn7a9TQ7cmQ4n+TJBSD/3j7jhgboBVJWX1jMxZ+Qd0/27hnltjz3nfts9aW3gbpg7tYm+Dp93tv8CQQC6R7hGqfPAgBM2osvmZcUpfPB93iwfr9XfW1v7Jm87X8P9KDOJUXRoUMfZaapRHDOROUp7Ppv3epqnzsC7OR5VAkA8kna5fyyXgd+4rCz/ZEEd145j/77IxXI4DR5wwk7XdOC9H/qLFaKgSUEfg2HVCl/J4zhpvsS/2L84bo9MQExNAkARWYy4SCwDyeoANLl5BBGRA6oCjybc7Kum2gN/KN1DUnAWrB/gsk19ScjTXxwV4eKLrCJbyEEOk8TDzGX3CiLhAkBqRCxOpIc58pNDxxyy+WWBP8WSYUSZNdCZkb0kI711wyzNhAE5pD6FdVtuRzOOIDUXkMacthoGtQwxv9uuIBeX";
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);
        System.out.println("公钥加密——私钥解密");

        Map map = new HashMap();
        map.put("enc", "hello");
        System.out.println("\r明文：\r\n" + map.toString());
        System.out.println("\r明文大小：\r\n" + map.toString().getBytes().length);

        RSAPublicKey pubK = getPublicKey(publicKey);
        String encodedData = RSACoderUtil.publicEncrypt(map.toString(), pubK);
        System.out.println("密文：\r\n" + encodedData);

        RSAPrivateKey privateKey1 = RSACoderUtil.getPrivateKey(privateKey);


        String decodedData = RSACoderUtil.privateDecrypt(encodedData, privateKey1);
        System.out.println("解密后文字: \r\n" + decodedData);


        String raw = "ic41+M3HmuVQqo39ZJoKqLflXu11lB0n34l0GzH6tnEA8gP0RRCDQB2JP2UwFKV6IP0k0stgXFQXfdM1lnfpVmbLbaskqxg2WdqNokgzgrOWcK0xuwZXN1I3rmCG8LN5jMYRekldyrwpEu5HAhdwheQDbe9hgiQeVo6mNayR2VYdx+rE/qQwQOix+bVKAqTPCGCVd+3n7uqI7wsMj1OPphOaRYVisQ1ZASHJW4SGV50dCwZ7vvEq2EAmsaHKuc+aSKwR3kWHDs13aXrk/nfMx+GbpYy+E3fK3l92p+atGOgkoHM/pHxNcCAlyDpAFMzOEMxsFcGU9q8aWSKWUyje/BLgA8jYnfA4sTinPkJko3VaZvbJ0vZ0OHL+7Qf/IYlaJR46wMefc0OsKc+7tvXWnICJGa4Z+7dLY1AA/jISewGQSzeehXl3TI+z62Tq0ZigGbkVG/TuXpZIoFOdSGe7j4Z7okW47mLZ1lMku+SNeFxkW9gaSy+WtFnBJSAzbKBucl2u7N1rojqFj7nLrDZTej7ijZDaIaZTB1D74Dhf+3nGRKY9DVGmUIFjD3RNSOpczmlCZ/pRZTyQwYdKOl/xc+Ll419PickRFxS2G4xKHtckQhFTDbTNbA9MEkBDH7fw/b55qwK5nMCXLM/Z7EXV0KMfMalu1KDspxKUiqr6HzE=";
        System.out.println("私钥加密密文:" + raw);
        String dec = publicDecrypt(raw, pubK);
        System.out.println("公钥解密密文:" + dec);

        String enc = privateEncrypt(dec, getPrivateKey(privateKey));

        System.out.println(publicDecrypt(enc, pubK));

    }

}
