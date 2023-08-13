package io.hiwepy.cloud.sample.service;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.IBaseService;

/**
 * 公共查询Service
 */
public interface ICommonService extends IBaseService<PairModel> {

    /**
     * 数据库当前时间 ：
     *
     * @return
     */
    String getNow();

}
