package io.hiwepy.cloud.authz.passwd;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeVerifiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VerifiServiceTest {

    @Autowired
    protected IPwdRetakeVerifiService VerifiService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testInsert() {

        PwdRetakeVerifiModel model = new PwdRetakeVerifiModel();
        model.setName("idcard");
        model.setLabel("证件号");
        model.setDesc("请输入有些的身份证号码.");
        model.setRequired("1");
        System.out.println(model);
        VerifiService.save(model);
        System.out.println("===========");
    }

    //@Test
    public void testGetModel() {
        PwdRetakeVerifiModel model = new PwdRetakeVerifiModel();

        model.setId("4CCB54CAAAC44076E0538713470A8637");

        PwdRetakeVerifiModel validate2 = VerifiService.getOne(new LambdaQueryWrapper<PwdRetakeVerifiModel>()
                .eq(PwdRetakeVerifiModel::getId, "4CCB54CAAAC44076E0538713470A8637"));
        System.out.println(validate2);
    }

    //@Test
    public void testGetList() {
        PwdRetakeVerifiModel model = new PwdRetakeVerifiModel();
        List<PwdRetakeVerifiModel> validateList = VerifiService.getEntityList(model);
        for (PwdRetakeVerifiModel validate : validateList) {
            System.out.println(validate);
        }
    }

    //@Test
    public void testDelete() {
        VerifiService.remove(new LambdaQueryWrapper<PwdRetakeVerifiModel>()
                .eq(PwdRetakeVerifiModel::getId, "4CCB54CAAAC44076E0538713470A8637"));
    }

}
