package com.woniu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.woniu.entity.Patient;
import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import com.woniu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
public class PayController {

    private final String APP_ID = "2021000119641392";
    private final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCzdzIBBE/5iAQB2BX1KOAdSpmdboGMjzxBM33qFkEmsW4VcgJSVBTBy8Hw3tNagp+MxmqzmuUpOFB268mOaidwG6qLesRzTK9HEsPoSMz1VbSVeKTFiVfw68psbtnWUPPAf4IV8ru1UvZ4O6NSR4RYCPOIWhjoUE1kKIaSSXWSng8+aa7eWKzpzpuOAAPea8CNYL3EUUfJm7Wl4yVJNSWSp6tufFonVwSnKeISZwP8oeFY7625P1y8lkd6tNaaK30YIWzFaIkEiMOMr43Z/8o+0ligeqQCNPNLfoXTrAciIydGJrAgVF04emJ7EKfIsq2mRMpjEVmkCKoXAydYphlAgMBAAECggEAWrFh40zuqN9mW7bBliRZOTgDrgbydFlg57vtE0omborhjG3txIKQrwDMGxXu6D4eCGJl6Yg2D1pC1Ob9CgPwZXCHSneMBUvBYl27rZqMTeQdzri94D36q+uCB+aziXTbDyzSdfZpPi+l5+uNe2i28FU4fjOrrWihTgJ6Yr9U/L6gO75v22exVb0HALSMjacAGZMeCV64sJlIewbtdZTV5IbNmh11WjMHIxQfuPSP2PBLMuRv1MMc+oESM+Rj9EmsMsm3MwePCdp4vo36tNDqvD24CSRYU1xyqdFQKmoK4HND4D4Z9LaLjl/eWljSq2T0dRz4cBSrJOMtSIyyfsXgAQKBgQDptr4wL92SX9Tof1QFigJN5YdjWCDYGJyE9NNu/FXzsSNNOmReeOWJriRSKMd8g6rqkJbv/iaj42bBwg4MqS4eF18WRMqL4YFvxB9S23jnrbXZXPmnF77ZVbjRE7yNuYG3odCC4CXUfaUf8nDrCwJZz/Dp1sxzzJUsnjPMEKKDlQKBgQCPRvX9QB5EFS7SqsTlPFeSgYeXKtskGv1VNyH+m64AqX+7/eiYwJ4ItoZhMq8k4V806m+WJsn33IlbNPl8o0UyIwMAZUlFwZw5RydJz5v8ASxDojerW94i0H6RmfVZfnCiCEAxjPlp1ASl7bTHVFqsP0HdiAnxAb3say7FLCmNkQKBgB+xNzMjmoE52443jfY+U7eqc2SGU9XZ6IejB3TaZPFpyOa1D108Oawk4Cn/+6Un2ap7NuyOY0EAGjQWE3+mBWnxM2S6qVi0mK/GQZUjy85m4Oz7zaLZ9nhURn6blYsGZ8+18qd8q8SX0Sd24dcNpCD3sKyVAx6E1Im7XjzC2LbNAoGAaOlY7hupDclFA9UvX3uT1B8snbAvznxEy8UXC0wLWUjD02/UhSVlkpO0Si/Gt4afJCwVkSjN8sR1f2eG15Dl9hRU+lEwGffu4LINPqraWOiy9MMvaDZYrb1lMFA/q0w+RSctehrz9l3dGXK5UMufqUiYfhJC1UqlOxwwnwf7VUECgYEA5245+01OVG80I2kEshOGJgHYCcigobqVAIOABqIiE9pTd+AuQdWUpam0hKiJcpBmOdv7+HZu95oem/idHMcgh9v7a4wn/1o7Z9MlTNPRobksdISPIDEAI94V+SGY65PqMSk1t6LRbrcfyMmoAyFabHoAM2yVcHd9Vo/NQuGIXFs=";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwKig4kC93wnytx4p0umTYIIu3qSO2f6cl3Nt8HIOtccHCaHVLLjwVo4PA9Cx4CwGfbN+tGd8vu15BuHpV/XZX2XyZLjaZno4UKmarPeh01MG/M5GMDyG3zc9cjUQ5isvEXmY3C6OOEEswJTym/P6kUwpVy9azDcSJyhBt/8bQ6l07ZnDZS4wwJ//jRxp+7Hgv6aa2kxSy9t/BQKNHEgMZmbUVCXX25Xs/QIy0rMaQw63Td61dX1OuLgpQA0+dtgx4sxs95E4aSAOpZhtYsJ1eGpmMlp5TjQShSTATP28di16LLjkBMI6iIyzo7TJdC7u0183PDT9jFzG7jWEopWagwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
//    private final String NOTIFY_URL = "http://localhost:8081/#/register/manager";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private String RETURN_URL = "http://localhost:8080/returnUrl";
    private String phone;


    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private CashierCostService cashierCostService;


    @GetMapping("/pay")
    public void alipay(HttpServletResponse httpResponse, String phone) throws IOException, ParseException {
        this.phone = phone;
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
//        request.set
        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount = "8";
        //订单名称，必填
        String subject = "成都协和医院预约";
        //商品描述，可空
        String body = "成都协和医院预约";
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @GetMapping(value = "/returnUrl")
    public void returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException, ServletException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if (signVerified) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andTelephoneEqualTo(this.phone);
            List<User> userList = userMapper.selectByExample(userExample);
            User user = userList.get(0);
            user.setSpare3("2");
            userMapper.updateByPrimaryKeySelective(user);

            //发请求到前端告诉前端已经支付成功
            response.sendRedirect("http://localhost:9090/#/gotoUserHome");
        }else{
            response.sendRedirect("http://localhost:9090/#/gotoUserHome");
        }

    }

}

