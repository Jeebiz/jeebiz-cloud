package io.hiwepy.cloud.authz.passwd;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeStrategyModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeStrategyService;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StrategyServiceTest {

    @Autowired
    protected IPwdRetakeStrategyService strategyService;

    @BeforeEach
    public void setup() {
    }

    //@Test
    public void testInsert() {

        PwdRetakeStrategyModel strategy = new PwdRetakeStrategyModel();
        strategy.setName(PwdStrategy.PWD_RETAKE_BY_EMAIL);
        strategy.setDesc("通过密保邮箱");
        strategy.setStatus("1");

        System.out.println(strategy);
        strategyService.save(strategy);
        System.out.println("===========");

        strategy.setName(PwdStrategy.PWD_RETAKE_BY_PHONE);
        strategy.setDesc("通过密保手机");
        strategy.setStatus("1");

        System.out.println(strategy);
        strategyService.save(strategy);
        System.out.println("===========");

        strategy.setName(PwdStrategy.PWD_RETAKE_BY_OTP);
        strategy.setDesc("通过动态口令");
        strategy.setStatus("0");

        System.out.println(strategy);
        strategyService.save(strategy);
        System.out.println("===========");

    }

    // @Test
    public void testGetModel() {
        PwdRetakeStrategyModel validate2 = strategyService.getOne(new LambdaQueryWrapper<PwdRetakeStrategyModel>()
                .eq(PwdRetakeStrategyModel::getId, "4CCAF0CCEEC5390BE0538713470A75A2"));
        System.out.println(validate2);
    }

    @Test
    public void testUpdate() {

        PwdRetakeStrategyModel strategyBefor = strategyService.getOne(new LambdaQueryWrapper<PwdRetakeStrategyModel>()
                .eq(PwdRetakeStrategyModel::getId, "4CCB1FF3FEFD3CA8E0538713470AD66B"));
        System.out.println(strategyBefor);

        strategyBefor.setDesc("通过邮箱找回密码(修改).");
        strategyBefor.setStatus("1");

        strategyService.updateById(strategyBefor);
    }

    //@Test
    public void testGetList() {
        PwdRetakeStrategyModel strategy = new PwdRetakeStrategyModel();
        List<PwdRetakeStrategyModel> validateList = strategyService.getEntityList(strategy);
        for (PwdRetakeStrategyModel validate : validateList) {
            System.out.println(validate);
        }
    }

    // @Test
    public void testDelete() {
        strategyService.remove(new LambdaQueryWrapper<PwdRetakeStrategyModel>()
                .eq(PwdRetakeStrategyModel::getId, "4CCB1FF3FEFD3CA8E0538713470AD66B"));
    }

    @AfterEach
    public void shutdown() {


    }

}
