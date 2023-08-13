package io.hiwepy.cloud.sample.service.impl;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.sample.dao.CommonMapper;
import io.hiwepy.cloud.sample.service.ICommonService;
import org.springframework.stereotype.Service;

/**
 * 公共查询Service 实现
 *
 * @author wandl
 */
@Service
public class CommonServiceImpl extends BaseServiceImpl<CommonMapper, PairModel> implements ICommonService {

    @Override
    public String getNow() {
        return getBaseMapper().getNow();
    }

}
