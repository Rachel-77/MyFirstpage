package com.waka.basic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description: okHttp 工具类
 * @Author: 星空
 * @CreateDate: 2020/12/23 9:55
 */
public class OkHttpUtils {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(500, 20, TimeUnit.MINUTES))
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build();

    /**
     * okHttp get方法的url拼接参数,并返回request对象
     * @param url
     * @param paramMap
     * @return
     */
    private static Request getRequest(String url, Map<String, String> paramMap) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if(StringUtil.NotNullOrEmpty(paramMap)){
            for (String key : paramMap.keySet()) {
                urlBuilder
                        .append(key)
                        .append("=")
                        .append(URLEncoder.encode(paramMap.get(key),"utf-8"))
                        .append("&");
            }
        }

        return new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get()
                .build();
    }


    /**
     * okHttp get同步请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url,  Map<String, String> paramMap) throws IOException {
        Response response = CLIENT.newCall( getRequest(url, paramMap)).execute();

        return response.body().string();
    }

    /**
     * okHttp get异步请求
     * @param url
     * @param paramMap
     * @param callback
     * @return
     * @throws IOException
     */
    public static void httpGet(String url,  Map<String, String> paramMap, Callback callback) throws UnsupportedEncodingException {
        CLIENT.newCall(getRequest(url, paramMap))
                .enqueue(callback);
    }

    /**
     *okHttp post返回request对象
     * @param url
     * @param body
     * @return
     */
    private static Request getRequest(String url, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    /**
     * okHttp post同步请求(json方式提交)
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String httpJsonPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Response response = CLIENT.newCall(getRequest(url, body))
                .execute();
        return response.body().string();
    }

    /**
     * okHttp post异步请求(json方式提交)
     * @param url
     * @param json
     * @param callback
     * @return
     * @throws IOException
     */
    public static void httpJsonPost(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        CLIENT.newCall(getRequest(url, body))
                .enqueue(callback);
    }


    /**
     * okHttp post同步请求(form方式提交)
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */

    public static String httpFormPost(String url, Map<String, String> paramMap) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if(StringUtil.NotNullOrEmpty(paramMap)){
            for (String key : paramMap.keySet()) {
                builder.add(key,paramMap.get(key));
            }
        }
        Response response = CLIENT.newCall(getRequest(url, builder.build()))
                .execute();
        return response.body().string();
    }

    /**
     * okHttp post异步请求(form方式提交)
     * @param url
     * @param paramMap
     * @param callback
     * @return
     * @throws IOException
     */

    public static void httpFormPost(String url, Map<String, String> paramMap, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramMap.keySet()) {
            builder.add(key, paramMap.get(key));
        }
        CLIENT.newCall(getRequest(url, builder.build()))
                .enqueue(callback);
    }

    /**
     * okHttp post同步请求(json方式提交)--附带Authorization认证
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String httpJsonAuthorizationPost(String url, String json,String Authorization) throws IOException {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Response response = CLIENT.newCall(
                        new Request.Builder()
                                .url(url)
                                .header("Authorization",Authorization)
                                .post(body)
                                .build()
                )
                .execute();
        return response.body().string();
    }

}

