package com.sky.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import com.sky.exception.OrderBusinessException;
import com.sky.utils.MapUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private static final String SHOP_ADDRESS = "北京市海淀区";
    private static final String GAODE_KEY = "test_key_123456";

    @InjectMocks
    private OrderServiceImpl orderService;

    //商家成功响应
    private JSONObject createSuccessShopAddress() throws Exception {
        JSONObject mockShopAddress = new JSONObject();
        mockShopAddress.put("status", "1");
        mockShopAddress.put("infocode", "10000");
        JSONArray geocodes = new JSONArray();
        JSONObject location = new JSONObject();
        location.put("location", "116.397428,39.90923");
        geocodes.put(location);
        mockShopAddress.put("geocodes", geocodes);
        return mockShopAddress;
    }

    //用户成功响应
    private JSONObject createSuccessUserAddress() throws Exception {
        JSONObject mockUserAddress = new JSONObject();
        mockUserAddress.put("status", "1");
        mockUserAddress.put("infocode", "10000");
        JSONArray geocodes = new JSONArray();
        JSONObject location = new JSONObject();
        location.put("location", "116.407428,39.91923");
        geocodes.put(location);
        mockUserAddress.put("geocodes", geocodes);
        return mockUserAddress;
    }

    // 路径成功响应
    private JSONObject createSuccessPath(String distance) throws Exception {
        JSONObject mockPath = new JSONObject();
        mockPath.put("status", "1");
        mockPath.put("infocode", "10000");

        JSONObject route = new JSONObject();
        JSONArray paths = new JSONArray();
        JSONObject pathInfo = new JSONObject();
        pathInfo.put("distance", distance);
        paths.put(pathInfo);
        route.put("paths", paths);

        mockPath.put("route", route);
        return mockPath;
    }

    //反射设置餐厅地点和地图key
    @Before
    public void setUp() throws Exception {
        // 使用反射设置私有字段的值
        Field shopAddressField = OrderServiceImpl.class.getDeclaredField("ShopAddress");
        shopAddressField.setAccessible(true);
        shopAddressField.set(orderService, SHOP_ADDRESS);

        Field gaodeKeyField = OrderServiceImpl.class.getDeclaredField("GaodeKey");
        gaodeKeyField.setAccessible(true);
        gaodeKeyField.set(orderService, GAODE_KEY);
    }

    // 测试用例1: 商家地址请求失败  status=0且infocode!=10000
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange1() throws Exception {
        try (MockedStatic<MapUtil> mockedStatic = Mockito.mockStatic(MapUtil.class)) {
            // 失败响应
            JSONObject shopAddressStatus = new JSONObject();
            shopAddressStatus.put("status", "0");
            shopAddressStatus.put("infocode", "10001");
            //模拟静态方法返回
            mockedStatic.when(() -> MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(shopAddressStatus);

            orderService.checkOutOfRange("用户地址");
        }
    }
    // 测试用例2: 商家地址成功,geocodes为空
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange2() throws Exception {
        try (MockedStatic<MapUtil> mockedStatic = Mockito.mockStatic(MapUtil.class)) {
            // 失败响应
            JSONObject shopAddressStatus = new JSONObject();
            shopAddressStatus.put("status", "0");
            shopAddressStatus.put("infocode", "10000");
            //模拟静态方法返回
            mockedStatic.when(() -> MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(shopAddressStatus);

            orderService.checkOutOfRange("用户地址");
        }
    }

    // 测试用例3: 用户地址请求失败
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange3() throws Exception{
        try (MockedStatic<MapUtil> mockedStatic = Mockito.mockStatic(MapUtil.class)) {
            //商家地址成功
            JSONObject mockShopAddress = new JSONObject();
            mockShopAddress.put("status", "1");
            mockShopAddress.put("infocode", "10000");
            JSONArray geocodes = new JSONArray();
            JSONObject location = new JSONObject();
            location.put("location", "116.397428,39.90923");
            geocodes.put(location);
            mockShopAddress.put("geocodes", geocodes);
            //用户地址失败
            JSONObject mockUserAddress = new JSONObject();
            mockUserAddress.put("status", "0");
            mockUserAddress.put("infocode", "20001");

            mockedStatic.when(() ->
                            MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(mockShopAddress);
            mockedStatic.when(() ->
                            MapUtil.getLatitudeLongitude("用户地址", GAODE_KEY))
                    .thenReturn(mockUserAddress);

            orderService.checkOutOfRange("用户地址");
        }
    }

    // 测试用例4: 用户地址成功但geocodes为空
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange4() throws Exception{
        try (MockedStatic<MapUtil> mockedMapUtil = Mockito.mockStatic(MapUtil.class)) {
            // 商家地址成功
            JSONObject mockShopAddress = createSuccessShopAddress();

            // 用户地址成功但没有geocodes
            JSONObject mockUserAddress = new JSONObject();
            mockUserAddress.put("status", "1");
            mockUserAddress.put("infocode", "10000");

            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(mockShopAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude("用户地址", GAODE_KEY))
                    .thenReturn(mockUserAddress);

            orderService.checkOutOfRange("用户地址");
        }
    }

    // 测试用例5: 路径请求失败
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange5() throws Exception{
        try (MockedStatic<MapUtil> mockedMapUtil = Mockito.mockStatic(MapUtil.class)) {
            // 商家地址成功
            JSONObject mockShopAddress = createSuccessShopAddress();

            // 用户地址成功
            JSONObject mockUserAddress = createSuccessUserAddress();

            // 路径请求失败
            JSONObject mockPath = new JSONObject();
            mockPath.put("status", "0");
            mockPath.put("infocode", "20001");

            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(mockShopAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude("用户地址", GAODE_KEY))
                    .thenReturn(mockUserAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getRouteInformation("116.397428,39.90923", "116.407428,39.91923", GAODE_KEY))
                    .thenReturn(mockPath);

            orderService.checkOutOfRange("用户地址");
        }
    }

    // 测试用例6: 路径请求成功但没有route
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange6() throws Exception{
        try (MockedStatic<MapUtil> mockedMapUtil = Mockito.mockStatic(MapUtil.class)) {
            // 商家地址成功
            JSONObject mockShopAddress = createSuccessShopAddress();

            // 用户地址成功
            JSONObject mockUserAddress = createSuccessUserAddress();

            // 路径成功但没有route
            JSONObject mockPath = new JSONObject();
            mockPath.put("status", "1");
            mockPath.put("infocode", "10000");

            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(mockShopAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude("用户地址", GAODE_KEY))
                    .thenReturn(mockUserAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getRouteInformation("116.397428,39.90923", "116.407428,39.91923", GAODE_KEY))
                    .thenReturn(mockPath);

            orderService.checkOutOfRange("用户地址");
        }
    }

    // 测试用例7: 距离超过5000米
    @Test(expected = OrderBusinessException.class)
    public void testCheckOutOfRange7() throws Exception{
        try (MockedStatic<MapUtil> mockedMapUtil = Mockito.mockStatic(MapUtil.class)) {
            // 商家地址成功
            JSONObject mockShopAddress = createSuccessShopAddress();

            // 用户地址成功
            JSONObject mockUserAddress = createSuccessUserAddress();

            // 路径成功，距离为6000米
            JSONObject mockPath = createSuccessPath("6000");

            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(mockShopAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude("用户地址", GAODE_KEY))
                    .thenReturn(mockUserAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getRouteInformation("116.397428,39.90923", "116.407428,39.91923", GAODE_KEY))
                    .thenReturn(mockPath);

            orderService.checkOutOfRange("用户地址");
        }
    }
    // 测试用例8: 距离小于等于5000米
    @Test
    public void testCheckOutOfRange8() throws Exception{
        try (MockedStatic<MapUtil> mockedMapUtil = Mockito.mockStatic(MapUtil.class)) {
            // 商家地址成功
            JSONObject mockShopAddress = createSuccessShopAddress();

            // 用户地址成功
            JSONObject mockUserAddress = createSuccessUserAddress();

            // 路径成功，距离为3000米
            JSONObject mockPath = createSuccessPath("3000");

            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude(SHOP_ADDRESS, GAODE_KEY))
                    .thenReturn(mockShopAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getLatitudeLongitude("用户地址", GAODE_KEY))
                    .thenReturn(mockUserAddress);
            mockedMapUtil.when(() ->
                            MapUtil.getRouteInformation("116.397428,39.90923", "116.407428,39.91923", GAODE_KEY))
                    .thenReturn(mockPath);

            orderService.checkOutOfRange("用户地址");
        }
    }
}