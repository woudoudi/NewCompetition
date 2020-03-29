package com.example.http_lib.bean;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.enums.RequestTypeEnum;
import com.example.http_lib.utils.ObjectUtil;
import com.example.http_lib.utils.RSACoderUtil;
import com.example.http_lib.utils.StringUtil;
import com.example.http_lib.utils.StringUtils;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.config.Constants;
import com.yidao.module_lib.utils.LogUtils;

import java.lang.reflect.Field;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBean {

    private BaseBean mBaseBean;

    private RequestrAnnotation annotation;

    public RequestBean(BaseBean baseBean, boolean isEncrypt) {
        this.mBaseBean = baseBean;
        this.isEncrypt = isEncrypt;
        annotation = baseBean.getClass().getAnnotation(RequestrAnnotation.class);
        parseParams();
    }

    private String url;

    private String type;

    private RequestMethodEnum mEnum;

    private boolean isEncrypt;

    private Map<String, Object> params = new HashMap<>();

    private Map<String, Object> encryptMap = new HashMap<>();

    public String getUrl() {
        return annotation.baseUrl() + replaceUrl(annotation.path(),mBaseBean.strBefore,mBaseBean.strAfter);
    }

    public RequestTypeEnum getType() {
        return annotation.type();
    }

    public RequestMethodEnum getEnum() {
        return annotation.method();
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public Map<String, Object> getParams() {
        if (isEncrypt()) {
            params.put(Constants.ENCRYPT_PARAM, getRSA());
        }

        LogUtils.d("HttpClient:请求的加密前参数:" + JSON.toJSONString(encryptMap));
        LogUtils.d("HttpClient:请求的url:" + getUrl());
        LogUtils.d("HttpClient:请求类型:" + getEnum().name());
        LogUtils.d("HttpClient:请求的加密后参数:" + JSON.toJSONString(params));
        return params;
    }

    public Map<String, Object> getEncryptMap() {
        return encryptMap;
    }


    private void parseParams() {
        List<Field> fields = ObjectUtil.getFields(mBaseBean.getClass());
        for (int i = 0; i < fields.size(); i++) {
            Field fieldItem = fields.get(i);
            String fieldsName = fieldItem.getName();
            if (fieldsName.equals("serialVersionUID")) {
                continue;
            }
            if (fieldsName.equals("baseUrl")) {
                continue;
            }
            if (fieldsName.equals("strBefore") || fieldsName.equals("strAfter")) {
                continue;
            }
            fieldItem.setAccessible(true);
            Object value = null;
            try {
                value = fieldItem.get(mBaseBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                if (isEncrypt()) {
                    encryptMap.put(fieldsName, value);
                } else {
                    params.put(fieldsName, value);
                }
            }
        }
    }

    private String getAES() {
        String paramUrl = StringUtils.mapToUrl(encryptMap);
//        String key = UserCacheHelper.getAESToken();
        String key = "";
        String signUrl = paramUrl.concat("&key=" + key);
        String sign = StringUtils.toMD5(signUrl);

        encryptMap.put(Constants.ENCRYPT_SIGN, sign);

        //请求参数转换为json 然后做AES加密
        String jsonUrl = JSON.toJSONString(encryptMap);
        String aes = StringUtil.toAESForSplit(jsonUrl, key, "UTF-8");
        return aes;
    }

    private String getRSA() {
        if (encryptMap.isEmpty()) {
            return "";
        }
        //公钥加密
        try {
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj4eKl68pCvhrpg7SIAq4YMjE+kAnqnGEi6ZeXMk/UVBFs6H4mCOQC16vlgwFVcDqim5pIzr8WGuApEVwbrJRz68VA5rfGoI208iteF+RNkdQn/j0im3LBDwV8z5m8Fp2CzrGJ+uzCt0GVVKfHnHd+tHECz9IvLZcgIzce/fikqwIDAQAB";
            RSAPublicKey pubK = RSACoderUtil.getPublicKey(RSACoderUtil.publicKey);
            String encrypt = RSACoderUtil.publicEncrypt(JSON.toJSONString(encryptMap), pubK);
            return encrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String replaceUrl(String url, String[] strsBefore, String[] strsAfter) {
        if (strsAfter == null || strsBefore == null || strsBefore.length != strsAfter.length) {
            return url;
        }
        int len = strsBefore.length;
        for (int i = 0; i < len; i++) {
            if (url.contains(strsBefore[i])) {
                url = url.replace(strsBefore[i],strsAfter[i]);
            }
        }
        return url;
    }
}
