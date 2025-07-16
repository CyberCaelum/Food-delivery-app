package com.sky.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @ClassName : MapUtil
 * @Description : 高德地图工具类
 * @Author :  CyberCaelum
 * @Date: 2025-07-15 07:46
 */
public class MapUtil {

    /**
     * @description: 获得经纬度
     * @author: CyberAstra
     * @date: 2025/7/15 at 09:48:29
     * @return: org.json.JSONObject
     **/
    public static JSONObject getLatitudeLongitude(String address,String key) {
        //创建访问地址:https://restapi.amap.com/v3/geocode/geo?parameters
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("restapi.amap.com")
                    .setPath("/v3/geocode/geo")
                    .addParameter("address", address)//地址
                    .addParameter("key", key)
                    .build();
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpGet httpGet = new HttpGet(uri);
        //发送请求，接受响应结果
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        HttpEntity entity = response.getEntity();
        //将资源转化为JSONObject
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(body);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        //关闭资源
        try {
            response.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonObject;
    }

    /**
     * @description: 路径信息
     * @author: CyberAstra
     * @date: 2025/7/15 at 10:08:23
     * @param: origin
     * @param: destination
     * @param: key
     * @return: org.json.JSONObject
     **/
    public static JSONObject getRouteInformation(String origin,String destination,String key) {

        //创建访问地址:https://restapi.amap.com/v5/direction/driving?parameters
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("restapi.amap.com")
                    .setPath("/v5/direction/driving")
                    .addParameter("origin", origin)//起点
                    .addParameter("destination", destination)//终点
                    .addParameter("key", key)
                    .build();
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpPost httpPost = new HttpPost(uri);
        //发送请求，接受响应结果
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        HttpEntity entity = response.getEntity();
        //将资源转化为JSONObject
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(body);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        //关闭资源
        try {
            response.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonObject;
    }

}
