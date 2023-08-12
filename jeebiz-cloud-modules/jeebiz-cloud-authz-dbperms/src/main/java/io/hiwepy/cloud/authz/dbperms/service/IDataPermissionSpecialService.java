package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialModel;

import java.util.List;

/**
 * 数据权限专项service层接口
 */
public interface IDataPermissionSpecialService extends IBaseService<DataPermissionSpecialModel> {

    List<DataPermissionSpecialCheckedModel> getSpecialUserPairs(String userid);

    List<DataPermissionSpecialCheckedModel> getSpecialRolePairs(String roleid);

}
