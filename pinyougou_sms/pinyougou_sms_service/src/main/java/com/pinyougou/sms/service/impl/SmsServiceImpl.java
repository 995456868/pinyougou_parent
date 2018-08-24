package com.pinyougou.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinyougou.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map sendSms(String phone_number, String sign_name, String template_code, String template_param) {
        return null;
    }
    public static void main(String[] args){
        Double a = 0.1;
        Double b = 0.1;
        System.out.println(a==b);
        /*for (int i = 0;i<50;i++) {
            int a = (int) ((Math.random() * 9 + 1) * 100000);
            System.err.println(a);
        }*/
    }
}
