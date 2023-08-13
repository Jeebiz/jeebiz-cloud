/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.web.dto.RoleAllotUserDTO;
import io.hiwepy.cloud.authz.rbac.web.dto.RoleAllotUserPaginationDTO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserAllocatedVO;

import java.util.List;

/**
 * @author wandl
 */
public interface IRoleService extends IBaseService<RoleEntity> {

    /**
     * 执行分配用户逻辑操作
     *
     * @param model
     * @return
     */
    boolean doAllot(RoleAllotUserDTO model);

    /**
     * 取消已分配给指定角色的用户
     *
     * @param model
     * @return
     */
    boolean doUnAllot(RoleAllotUserDTO model);

    /**
     * 查询系统可用角色信息
     *
     * @return
     */
    List<RoleEntity> getRoleList();

    /**
     * 分页查询角色已分配用户信息
     *
     * @param paginationDTO
     * @return
     */
    Page<UserAllocatedVO> getPagedAllocatedList(RoleAllotUserPaginationDTO paginationDTO);

    /**
     * 分页查询角色未分配用户信息
     *
     * @param paginationDTO
     * @return
     */
    Page<UserAllocatedVO> getPagedUnAllocatedList(RoleAllotUserPaginationDTO paginationDTO);

}
