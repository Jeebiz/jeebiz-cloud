package io.hiwepy.cloud.authz.passwd;

import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeCaptcha;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

public class FormatTest {

    protected String format = "yyyy-MM-dd HH:mm:ss";
    protected SimpleDateFormat sdf = new SimpleDateFormat(format);
    protected Sequence sequence = new Sequence(0);

    @Test
    public void testFormat() throws Exception {

        PwdRetakeCaptcha bind = new PwdRetakeCaptcha(String.valueOf(sequence.nextId()), RandomStringUtils.randomAlphanumeric(6), sdf.format(System.currentTimeMillis()));

        System.out.println("uuid:" + bind.getUuid());
        System.out.println("Captcha:" + bind.getCaptcha());
        System.out.println("Timestamp:" + bind.getTimestamp());

    }

}
