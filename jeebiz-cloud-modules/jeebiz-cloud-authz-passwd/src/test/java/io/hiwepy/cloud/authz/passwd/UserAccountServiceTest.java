package io.hiwepy.cloud.authz.passwd;

import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserAccountServiceTest {


    @Autowired
    protected IPwdRetakeAccountService accountService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetList() {

    }

}
