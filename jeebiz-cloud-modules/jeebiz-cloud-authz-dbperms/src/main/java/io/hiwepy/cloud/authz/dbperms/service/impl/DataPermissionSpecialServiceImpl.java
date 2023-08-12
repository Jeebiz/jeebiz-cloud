package io.hiwepy.cloud.authz.dbperms.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionSpecialMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionSpecialService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据权限专项service层接口实现
 */
@Service
public class DataPermissionSpecialServiceImpl
        extends BaseServiceImpl<DataPermissionSpecialMapper, DataPermissionSpecialModel>
        implements IDataPermissionSpecialService {

    @Override
    public List<DataPermissionSpecialCheckedModel> getSpecialUserPairs(String userid) {
        return getBaseMapper().getSpecialUserPairs(userid);
    }

    @Override
    public List<DataPermissionSpecialCheckedModel> getSpecialRolePairs(String roleid) {
        return getBaseMapper().getSpecialRolePairs(roleid);
    }

}
