package edu.hqu.lease.web.app.service.impl;

import edu.hqu.lease.web.app.service.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmsServiceImplTest {
    @Autowired
    private SmsService smsService;
    @Test
    void sendCode() {
        smsService.sendCode("17706013264","1234");
    }
}