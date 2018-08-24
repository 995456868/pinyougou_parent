package com.pinyougou.sms.service;

import java.util.Map;

public interface SmsService {

    public Map sendSms(String phone_number,String sign_name,String template_code,String template_param);

}
