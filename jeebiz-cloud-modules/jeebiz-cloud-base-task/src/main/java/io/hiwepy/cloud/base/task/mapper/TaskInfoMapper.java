package io.hiwepy.cloud.base.task.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.task.entity.TaskInfoEntity;
import io.hiwepy.cloud.base.task.dto.TaskPaginationDTO;
import io.hiwepy.cloud.base.task.vo.TaskInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任务信息表 Mapper 接口
 * </p>
 *
 * @author wandl
 * @since 2023-07-18
 */
public interface TaskInfoMapper extends BaseMapper<TaskInfoEntity> {

    /**
     * 分页查询任务列表
     *
     * @param page
     * @param paginationDTO
     * @return
     */
    Page<TaskInfoVO> getPagedTaskList(@Param("page") Page<TaskInfoVO> page, @Param("parm") TaskPaginationDTO paginationDTO);

}
