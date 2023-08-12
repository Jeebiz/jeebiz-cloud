package io.hiwepy.cloud.base.guard.dao;


import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.guard.dao.entities.AntisamyModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAntisamyDao extends BaseMapper<AntisamyModel> {

}
