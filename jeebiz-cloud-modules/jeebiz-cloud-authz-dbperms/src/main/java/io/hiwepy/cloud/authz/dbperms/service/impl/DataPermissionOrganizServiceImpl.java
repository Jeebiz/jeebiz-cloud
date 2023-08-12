package io.hiwepy.cloud.authz.dbperms.service.impl;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionOrganizMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionOrganizModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionOrganizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据权限树service层接口实现
 */
@Service
public class DataPermissionOrganizServiceImpl extends BaseServiceImpl<DataPermissionOrganizMapper, DataPermissionOrganizModel>
        implements IDataPermissionOrganizService {

    @Autowired
    private RedisOperationTemplate redisOperation;

    private void setChecked(DataPermissionOrganizModel parentModel, List<DataPermissionOrganizModel> childrenList) {
        if (CollectionUtils.isNotEmpty(childrenList)) {
            for (DataPermissionOrganizModel organizModel : childrenList) {
                if (CollectionUtils.isNotEmpty(organizModel.getChildren())) {
                    this.setChecked(organizModel, organizModel.getChildren());
                }
            }
            if (childrenList.stream().anyMatch(children -> !children.isChecked())) {
                parentModel.setChecked(true);
            } else {
                parentModel.setChecked(false);
            }
        }
    }

    @Override
    public List<DataPermissionOrganizModel> getOrganizRoleTree(String rid) {

        // 查询数据
        List<DataPermissionOrganizModel> treeList = getBaseMapper().getOrganizRoleTree(rid);
        if (CollectionUtils.isEmpty(treeList)) {
            return Lists.newArrayList();
        }
        // 选中状态
        for (DataPermissionOrganizModel organizModel : treeList) {
            this.setChecked(organizModel, organizModel.getChildren());
        }
        // 构造根节点
        List<DataPermissionOrganizModel> retList = Lists.newArrayList();

        DataPermissionOrganizModel model = new DataPermissionOrganizModel();
        model.setChecked(treeList.stream().anyMatch(children -> !children.isChecked()));
        model.setChildren(treeList);
        model.setId("1");
        model.setName("全校");
        model.setValue("1");
        model.setPid("0");

        retList.add(model);

        return retList;
    }

    @Override
    public List<DataPermissionOrganizModel> getOrganizUserTree(String uid) {

        // 查询数据
        List<DataPermissionOrganizModel> treeList = getBaseMapper().getOrganizUserTree(uid);
        if (CollectionUtils.isEmpty(treeList)) {
            return Lists.newArrayList();
        }
        // 选中状态
        for (DataPermissionOrganizModel organizModel : treeList) {
            this.setChecked(organizModel, organizModel.getChildren());
        }
        // 构造根节点
        List<DataPermissionOrganizModel> retList = Lists.newArrayList();

        DataPermissionOrganizModel model = new DataPermissionOrganizModel();
        model.setChecked(treeList.stream().anyMatch(children -> !children.isChecked()));
        model.setChildren(treeList);
        model.setId("1");
        model.setName("全校");
        model.setValue("1");
        model.setPid("0");

        retList.add(model);

        return retList;
    }

    @Override
    public List<DataPermissionOrganizModel> getOrganizRuleTree(String pid) {

        // 查询数据
        List<DataPermissionOrganizModel> treeList = getBaseMapper().getOrganizRuleTree(pid);
        if (CollectionUtils.isEmpty(treeList)) {
            return Lists.newArrayList();
        }
        // 选中状态
        for (DataPermissionOrganizModel organizModel : treeList) {
            this.setChecked(organizModel, organizModel.getChildren());
        }
        // 构造根节点
        List<DataPermissionOrganizModel> retList = Lists.newArrayList();

        DataPermissionOrganizModel model = new DataPermissionOrganizModel();
        model.setChecked(treeList.stream().anyMatch(children -> !children.isChecked()));
        model.setChildren(treeList);
        model.setId("1");
        model.setName("全校");
        model.setValue("1");
        model.setPid("0");

        retList.add(model);

        return retList;
    }

}
