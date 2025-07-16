package com.sky.service.impl;

import com.sky.vo.*;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.BaseException;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.MapUtil;
import com.sky.utils.WeChatPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.DocumentationCache;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName : OrderServiceImpl
 * @Description : 订单service
 * @Author :  CyberCaelum
 * @Date: 2025-07-11 14:30
 */

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DocumentationCache resourceGroupCache;

    @Value("${sky.gaode.key}")
    private String GaodeKey;

    @Value("${sky.shop.address}")
    private String ShopAddress;

    /**
     * @description: 下订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:10:06
     * @param: ordersSubmitDTO
     * @return: com.sky.vo.OrderSubmitVO
     **/
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) throws Exception {
        //判断地址是否为空
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //判断购物车是否为空
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list ==null || list.isEmpty()){
            throw new BaseException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        String address = addressBook.getProvinceName()+addressBook.getCityName()
                +addressBook.getDistrictName()+addressBook.getDetail();
        checkOutOfRange(address);
        Orders orders = new Orders();
        //设置订单数据
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(BaseContext.getCurrentId());
        orders.setAddress(addressBook.getDetail());
        //插入订单数据库
        orderMapper.insert(orders);
        //将购物车数据复制进订单详情中
        BigDecimal amount = BigDecimal.ZERO;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart shoppingCarts : list) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCarts,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
            amount.add(shoppingCarts.getAmount());
        }
        //插入订单详情数据
        orderDetailMapper.insert(orderDetails);
        //清空购物车
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
        //返回VO
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orders.getId());
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setOrderTime(orders.getOrderTime());
        //orderSubmitVO.setOrderAmount(amount);
        return orderSubmitVO;
    }

    /**
     * @description: 判断订单是否超出范围
     * @author: CyberAstra
     * @date: 2025/7/15 at 10:44:51
     * @param: address
     **/
    public void checkOutOfRange(String address) throws Exception {
        //获得商家经纬度
        JSONObject shopAddress = MapUtil.getLatitudeLongitude(ShopAddress,GaodeKey);
        log.info("商家信息：{}", shopAddress);
        //判断请求状态
        if (shopAddress.getString("status").equals("0") && !shopAddress.getString("infocode").equals("10000")) {
            throw new OrderBusinessException("商家地址解析错误");
        }
        String shop = "";
        //获得第一个数据
        if (shopAddress.has("geocodes") && shopAddress.getJSONArray("geocodes").length() > 0) {
            JSONObject firstPoi = shopAddress.getJSONArray("geocodes").getJSONObject(0);
            shop = firstPoi.getString("location");
        }
        else {
            throw new OrderBusinessException("商家地址解析错误");
        }

        //获得用户经纬度
        JSONObject userAddress = MapUtil.getLatitudeLongitude(address,GaodeKey);
        log.info("用户信息：{}",userAddress);
        //判断请求状态
        if (userAddress.getString("status").equals("0") && !userAddress.getString("infocode").equals("10000")) {
            throw new OrderBusinessException("用户地址解析错误");
        }
        String user = "";
        //获得第一个数据
        if (userAddress.has("geocodes") && userAddress.getJSONArray("geocodes").length() > 0) {
            JSONObject firstPoi = userAddress.getJSONArray("geocodes").getJSONObject(0);
            user = firstPoi.getString("location");
        }
        else {
            throw new OrderBusinessException("用户地址解析错误");
        }

        //获得路径信息
        JSONObject path = MapUtil.getRouteInformation(shop,user,GaodeKey);
        log.info("路径信息：{}",path);
        //判断请求状态
        if (path.getString("status").equals("0") && !path.getString("infocode").equals("10000")) {
            throw new OrderBusinessException("路径解析错误");
        }
        //获得路径长度

        String length = "";
        if (path.has("route") && path.getJSONObject("route").length() > 0) {
            JSONObject firstPoi = path.getJSONObject("route");
            length = firstPoi.getJSONArray("paths").getJSONObject(0).getString("distance");
        }
        if(Double.parseDouble(length) > 5000){
            //配送距离超过5000米
            throw new OrderBusinessException("超出配送范围");
        }
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "苍穹外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );
//
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }不造对不对
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", "ORDERPAID");
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));

        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomStringUtils.randomNumeric(32);
        OrderPaymentVO orderPaymentVO = new OrderPaymentVO();
        orderPaymentVO.setTimeStamp(timeStamp);
        orderPaymentVO.setNonceStr(nonceStr);
        //为替代微信支付成功后的数据库订单状态更新，多定义一个方法进行修改
        Integer OrderPaidStatus = Orders.PAID; //支付状态，已支付
        Integer OrderStatus = Orders.TO_BE_CONFIRMED;  //订单状态，待接单

        //发现没有将支付时间 check_out属性赋值，所以在这里更新
        LocalDateTime check_out_time = LocalDateTime.now();

        //获取订单号码
        String orderNumber = ordersPaymentDTO.getOrderNumber();

        log.info("调用updateStatus，用于替换微信支付更新数据库状态的问题");
        orderMapper.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, orderNumber);

        return orderPaymentVO;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * @description: 查看历史订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 14:50:52
     * @param: page
     * @param: pageSize
     * @param: status
     * @return: com.sky.result.PageResult
     **/
    @Override
    public PageResult history(Integer page, Integer pageSize, Integer status) {
        PageHelper.startPage(page, pageSize);
        Page<Orders> orders = orderMapper.history(BaseContext.getCurrentId(),status);
        List<OrderVO> list = new ArrayList<>();
        if (orders != null && !orders.isEmpty()) {
            //遍历订单
            for (Orders order : orders) {
                //通过订单的id查询到订单详情
                List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(order.getId());
                OrderVO orderVO = new OrderVO();
                //将订单详情中的所有数据复制到orderVO中
                BeanUtils.copyProperties(order,orderVO);
                orderVO.setOrderDetailList(orderDetails);
                list.add(orderVO);
            }
        }
        return new PageResult(orders.getTotal(),list);
    }

    /**
     * @description: 用户取消订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:12:41
     * @param: id
     **/
    @Override
    public void cancel(Long id) {
        Orders order = orderMapper.getById(id);
        // 校验订单是否存在
        if (order == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
        if (order.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        //微信退款
//        Orders orders = new Orders();
//        orders.setId(order.getId());
//
//        // 订单处于待接单状态下取消，需要进行退款
//        if (order.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
//            //调用微信支付退款接口
//            weChatPayUtil.refund(
//                    order.getNumber(), //商户订单号
//                    order.getNumber(), //商户退款单号
//                    new BigDecimal(0.01),//退款金额，单位 元
//                    new BigDecimal(0.01));//原订单金额
//
//            //支付状态修改为 退款
//            orders.setPayStatus(Orders.REFUND);
//        }
        //更新订单状态
        order.setStatus(Orders.CANCELLED);
        order.setPayStatus(Orders.REFUND);
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason("用户取消订单");
        orderMapper.update(order);
    }

    /**
     * @description: 查看订单详情
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:21:06
     * @param: id
     * @return: com.sky.vo.OrderVO
     **/
    @Override
    public OrderVO details(Long id) {
        OrderVO orderVO = new OrderVO();
        List<OrderDetail> list = orderDetailMapper.getByOrderId(id);
        orderVO.setOrderDetailList(list);
        return orderVO;
    }

    /**
     * @description: 查看订单详情和订单信息
     * @author: CyberAstra
     * @date: 2025/7/15 at 15:51:19
     * @param: id
     * @return: com.sky.vo.OrderDetailVO
     **/
    public OrderDetailVO detail(Long id) {
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        Orders orders = orderMapper.getById(id);
        BeanUtils.copyProperties(orders,orderDetailVO);
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);
        orderDetailVO.setOrderDetailList(orderDetailList);
        return orderDetailVO;
    }

    /**
     * @description: 再来一单
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:59:04
     * @param: id
     **/
    @Override
    public void repetition(Long id) {
        //将订单详情中的信息存入购物车
        List<OrderDetail> list = orderDetailMapper.getByOrderId(id);
        Long currentId = BaseContext.getCurrentId();
        List<ShoppingCart> carts = new ArrayList<>();
        for (OrderDetail orderDetail : list) {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(orderDetail,shoppingCart);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setUserId(currentId);
            carts.add(shoppingCart);
        }
        shoppingCartMapper.addList(carts);
    }

    /**
     * @description: 订单搜索
     * @author: CyberAstra
     * @date: 2025/7/13 at 09:46:14
     * @param: ordersPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        //查找订单信息
        Page<Orders> page = orderMapper.select(ordersPageQueryDTO);
        List<OrderDetailVO> list = new ArrayList<>();
        //查找订单详情
        for (Orders order : page) {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(order.getId());
            //将商品信息字符串化
//            String dishes = "";
//            for (OrderDetail orderDetail : orderDetails) {
//                dishes = orderDetail.getName() + "*" + orderDetail.getNumber() + ";";
//            }
//            orderVO.setOrderDishes(dishes);
            BeanUtils.copyProperties(order,orderDetailVO);
            List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(order.getId());
            //将返回的订单列表中的订单详情设置为查询出来的列表
            orderDetailVO.setOrderDetailList(orderDetails);
            list.add(orderDetailVO);
        }
        return new PageResult(page.getTotal(),list);
    }

    /**
     * @description: 各个状态订单数量统计
     * @author: CyberAstra
     * @date: 2025/7/14 at 06:46:12
     * @return: com.sky.vo.OrderStatisticsVO
     **/
    @Override
    public OrderStatisticsVO statistics() {
        Integer a = orderMapper.statistics(Orders.CONFIRMED);
        Integer b = orderMapper.statistics(Orders.DELIVERY_IN_PROGRESS);
        Integer c = orderMapper.statistics(Orders.TO_BE_CONFIRMED);
        return new OrderStatisticsVO(c,a,b);
    }

    /**
     * @description: 接单
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:10:57
     * @param: ordersConfirmDTO
     **/
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders order = orderMapper.getById(ordersConfirmDTO.getId());
        if (order == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        order.setStatus(Orders.CONFIRMED);
        orderMapper.update(order);
    }

    /**
     * @description: 拒单
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:13:00
     * @param: ordersRejectionDTO
     **/
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        Orders order = orderMapper.getById(ordersRejectionDTO.getId());
        //判断订单是否存在，以及是否接单
        if (order == null && !order.getStatus().equals(Orders.CANCELLED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
//        //判断用户是否付款,如果已经付款需要退款
//        if (order.getPayStatus().equals(Orders.PAID)) {
//            String refund = weChatPayUtil.refund(
//                    order.getNumber(),
//                    order.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
//            log.info("申请退款：{}", refund);
//        }
        //未付款修改订单的状态
        order.setPayStatus(Orders.REFUND);
        order.setStatus(Orders.CANCELLED);
        order.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        order.setCancelTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    /**
     * @description: 商家取消订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 08:57:27
     * @param: ordersCancelDTO
     **/
    @Override
    public void adminCancel(OrdersCancelDTO ordersCancelDTO) {
        Orders order = orderMapper.getById(ordersCancelDTO.getId());
        if(order == null){
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        order.setStatus(Orders.CANCELLED);
        order.setCancelReason(ordersCancelDTO.getCancelReason());
        order.setCancelTime(LocalDateTime.now());
//        //判断用户是否付款,如果已经付款需要退款
//        if (order.getPayStatus().equals(Orders.PAID)) {
//            String refund = weChatPayUtil.refund(
//                    order.getNumber(),
//                    order.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
//            log.info("申请退款：{}", refund);
//        }
        orderMapper.update(order);
    }

    /**
     * @description: 派送订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 09:04:47
     * @param: id
     **/
    @Override
    public void delivery(Long id) {
        Orders order = orderMapper.getById(id);
        if (order == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        if (!order.getStatus().equals(Orders.CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        order.setStatus(Orders.DELIVERY_IN_PROGRESS);
        orderMapper.update(order);
    }

    /**
     * @description: 完成订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 09:09:21
     * @param: id
     **/
    @Override
    public void complete(Long id) {
        Orders order = orderMapper.getById(id);
        if (order == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        if (!order.getStatus().equals(Orders.DELIVERY_IN_PROGRESS)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        order.setStatus(Orders.COMPLETED);
        order.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(order);
    }
}
