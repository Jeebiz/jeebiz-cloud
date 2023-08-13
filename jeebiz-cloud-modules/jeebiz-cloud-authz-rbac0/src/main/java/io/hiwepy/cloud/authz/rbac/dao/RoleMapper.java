/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.web.dto.RoleAllotUserPaginationDTO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserAllocatedVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理Mapper
 *
 * @author wandl
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 分页查询角色已分配用户信息
     *
     * @param page
     * @param model
     * @return
     */
    List<UserAllocatedVO> getPagedAllocatedList(Page<UserAllocatedVO> page, RoleAllotUserPaginationDTO model);

    /**
     * 分页查询角色未分配用户信息
     *
     * @param page
     * @param model
     * @return
     */
    List<UserAllocatedVO> getPagedUnAllocatedList(Page<UserAllocatedVO> page, RoleAllotUserPaginationDTO model);

}
