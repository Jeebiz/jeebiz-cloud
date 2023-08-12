package io.hiwepy.cloud.base.dict.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.dict.dao.entities.DictRegionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1 Mapper 接口
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Mapper
public interface DictRegionMapper extends BaseMapper<DictRegionEntity> {

}
