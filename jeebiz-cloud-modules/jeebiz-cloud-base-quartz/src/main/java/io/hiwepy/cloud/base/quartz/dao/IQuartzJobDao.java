package io.hiwepy.cloud.base.quartz.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.quartz.dao.entities.QuartzJobModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface IQuartzJobDao extends BaseMapper<QuartzJobModel> {

    QuartzJobModel getJobByBizId(@Param("id") String bizId);

    List<QuartzJobModel> getJobList();

}