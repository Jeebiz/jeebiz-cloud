package io.hiwepy.cloud.base.logs.strategy;


import io.hiwepy.cloud.api.dto.BizLogNewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.lang.reflect.Method;

public interface LogWriteStrategy {

    /**
     * 获取写出渠道
     *
     * @return
     */
    LogWriteChannel getChannel();

    <O extends BizLogNewDTO> boolean saveLog(Method method, Api api, ApiOperation apiOperation, Object rt, Throwable ex) throws Exception;

}
