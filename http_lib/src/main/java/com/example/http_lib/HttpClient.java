package com.example.http_lib;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.http_lib.bean.RequestBean;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.enums.RequestTypeEnum;
import com.example.http_lib.utils.RSACoderUtil;
import com.example.http_lib.utils.StringUtil;

import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.http.callback.IHttpCallBack;
import com.yidao.module_lib.utils.LogUtils;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求类
 * Created by xiaochan on 2017/6/26.
 */

public class HttpClient {

    Handler mMainHandler = new Handler(Looper.getMainLooper());

    private IHttpCallBack mModel;
    private Class mRequestClass;
    private Object mCarry;

    public static boolean debug = false;

    private HttpClient(IHttpCallBack model) {
        if (model == null) {
            throw new NullPointerException("model can not be null");
        }
        this.mModel = model;
    }

    public static void request(BaseBean baseBean, IHttpCallBack model) {
        request(baseBean, true, model);
    }


    public static void request(BaseBean baseBean, boolean isEncrypt, IHttpCallBack model) {
        new HttpClient(model).realRequest(baseBean, isEncrypt);
    }

    private void realRequest(BaseBean baseBean, boolean isEncrypt) {
        this.mCarry = baseBean.carry;
        this.mRequestClass = baseBean.getClass();
        final RequestBean requestBean = new RequestBean(baseBean, isEncrypt);
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();

        String url = requestBean.getUrl();
        Map<String, Object> params = requestBean.getParams();
        RequestTypeEnum typeValue = requestBean.getType();
        builder.addHeader("token", UserCacheHelper.getToken());
//        builder.addHeader("debug",Boolean.toString(debug));
        if (requestBean.getEnum() == RequestMethodEnum.POST) {
            if (!TextUtils.isEmpty(typeValue.getTypeStr())) {
                builder.addHeader("Content-type", typeValue.getTypeStr());
            }
            Request request;
            //post form方式提交的数据
            if (typeValue== RequestTypeEnum.FORM) {
                FormBody.Builder formBuilder = new FormBody.Builder();
                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    formBuilder.add(key, value);
                }
                FormBody formBody = formBuilder.build();
                request = builder.post(formBody).url(url).build();
            } else {//json提交方式
                String json = JSON.toJSONString(params);
                RequestBody requestBody = RequestBody.create(MediaType.parse(typeValue.getTypeStr()), json);
                request = builder.post(requestBody).url(url).build();
            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setRetCode(404);
                    responseBean.setRetMsg("请求错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });

        } else if(requestBean.getEnum() == RequestMethodEnum.GET){
            url = splictGetUrl(url, requestBean.getEncryptMap());
            LogUtils.d("HttpClient: GET 请求的url:" + url);
            Request request = builder.get().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setRetCode(404);
                    responseBean.setRetMsg("请求错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });
        } else if(requestBean.getEnum() == RequestMethodEnum.PUT){
            String json = JSON.toJSONString(params);
            RequestBody requestBody = RequestBody.create(MediaType.parse(typeValue.getTypeStr()), json);
            Request request = builder.put(requestBody).url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setRetCode(404);
                    responseBean.setRetMsg("请求错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });
        } else if(requestBean.getEnum() == RequestMethodEnum.DELETE){
            String json = JSON.toJSONString(params);
            RequestBody requestBody = RequestBody.create(MediaType.parse(typeValue.getTypeStr()), json);
            Request request = builder.delete(requestBody).url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setData("");
                    responseBean.setRetCode(404);
                    responseBean.setRetMsg("请求错误");
                    onFail(responseBean);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback(requestBean.isEncrypt(), response.body().string());
                }
            });
        }
    }

    private void callback(boolean isEncrypt, String result) {
        try {
            ResponseBean responseBean = JSON.parseObject(result,ResponseBean.class);
            // 判断是否加密  如果加密  进行解密操作
            LogUtils.d("平台返回的数据(解密前):" + responseBean.toString());

            if (isEncrypt && !debug) {
                String enc = responseBean.getEnc();
                if (!TextUtils.isEmpty(responseBean.getEnc())) {
                    try {
                        RSAPublicKey publicKey = RSACoderUtil.getPublicKey(RSACoderUtil.publicKey);
                        String decrypt = RSACoderUtil.publicDecrypt(enc, publicKey);
                        responseBean = JSON.parseObject(decrypt, ResponseBean.class);
                        LogUtils.d("平台返回的数据(解密后):" + responseBean.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        onFail(responseBean);
                        LogUtils.d("平台返回的数据(解密失败):" + enc);
                        return;
                    }
                }
            }

            if (responseBean.isSucceed()) {
                onSuccess(responseBean);
            } else {
                onFail(responseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String splictGetUrl(String requestUrl, Map<String, Object> params) {
        if(params.isEmpty()){
            return requestUrl;
        }
        Set<String> keySet = params.keySet();
        StringBuffer buffer = new StringBuffer();
        for (String key : keySet) {
            String value = params.get(key).toString();
            buffer.append(key).append("=").append(value).append("&");
        }
        String getParams = buffer.toString();

        requestUrl = requestUrl.endsWith("?") ? requestUrl : requestUrl + "?";
        getParams = getParams.endsWith("&") ? getParams.substring(0, getParams.length() - 1) : getParams;
        requestUrl += getParams;
        return requestUrl;
    }

    private void onSuccess(final ResponseBean responseBean) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                responseBean.setSucceed(true);
                responseBean.setRequestClass(mRequestClass);
                responseBean.setCarry(mCarry);
                mModel.success(responseBean);
            }
        });
    }

    private void onFail(final ResponseBean responseBean) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                responseBean.setSucceed(false);
                responseBean.setRequestClass(mRequestClass);
                responseBean.setCarry(mCarry);
                mModel.failed(responseBean);
            }
        });
    }
}
