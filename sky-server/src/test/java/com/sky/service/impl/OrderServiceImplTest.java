package com.sky.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.sky.exception.OrderBusinessException;
import com.sky.utils.MapUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private static final String ShopAddress = "ShopAddress";
    private static final String GaodeKey = "GaodeKey";

    // 创建成功的商家地址响应
    private JSONObject createSuccessShopAddress() throws JSONException {
        JSONObject mockShopAddress = new JSONObject();
        mockShopAddress.put("status", "1");
        mockShopAddress.put("infocode", "10000");
        JSONArray geocodes = new JSONArray();
        JSONObject location = new JSONObject();
        location.put("location", "116.397428,39.90923");
        geocodes.add(location);
        mockShopAddress.put("geocodes", geocodes);
        return mockShopAddress;
    }

    // 创建成功的用户地址响应
    private JSONObject createSuccessUserAddress() throws JSONException {
        JSONObject mockUserAddress = new JSONObject();
        mockUserAddress.put("status", "1");
        mockUserAddress.put("infocode", "10000");
        JSONArray geocodes = new JSONArray();
        JSONObject location = new JSONObject();
        location.put("location", "116.407428,39.91923");
        geocodes.add(location);
        mockUserAddress.put("geocodes", geocodes);
        return mockUserAddress;
    }

    // 创建成功的路径响应
    private JSONObject createSuccessPath(String distance) throws JSONException {
        JSONObject mockPath = new JSONObject();
        mockPath.put("status", "1");
        mockPath.put("infocode", "10000");

        JSONObject route = new JSONObject();
        JSONArray paths = new JSONArray();
        JSONObject pathInfo = new JSONObject();
        pathInfo.put("distance", distance);
        paths.add(pathInfo);
        route.put("paths", paths);

        mockPath.put("route", route);
        return mockPath;
    }

    // 测试用例1: 商家地址解析失败  status=0且infocode!=10000
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange_ShopAddressStatusError() {
        try (MockedStatic<MapUtil> mockedStatic = Mockito.mockStatic(MapUtil.class)) {
            //设置返回值,status=0且infocode!=10000
            JSONObject shopAddressStatus = new JSONObject();
            shopAddressStatus.put("status","0");

            mockedStatic.when(()->MapUtil.getLatitudeLongitude(ShopAddress,GaodeKey)).thenReturn()
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}