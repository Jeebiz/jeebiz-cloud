/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import hitool.core.collections.CollectionUtils;
import hitool.core.lang3.StringUtils;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.oss.core.OssRedisKey;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.strategy.FileInfoProvider;
import io.hiwepy.cloud.oss.core.strategy.FileStoreStrategyRouter;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.hiwepy.cloud.oss.flow.dao.FileMapper;
import io.hiwepy.cloud.oss.flow.dao.entities.FileEntity;
import io.hiwepy.cloud.oss.flow.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileServiceImpl extends BaseServiceImpl<FileMapper, FileEntity> implements IFileService, FileInfoProvider {

    @Autowired
    private FileStoreStrategyRouter fileStrategyRouter;
    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    public FileStoreConfig getConfig(OssChannel channel) {
        FileStoreConfig config = fileStrategyRouter.routeFor(channel).getConfig();
        return config;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception {
        return fileStrategyRouter.routeFor(uploadBo.getChannel()).upload(uploadBo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <O extends FilesUploadBO> FilesUploadResult upload(O uploadBo) throws Exception {
        return fileStrategyRouter.routeFor(uploadBo.getChannel()).upload(uploadBo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <O extends FileReuploadBO> FileReuploadResult reupload(O uploadBo) throws Exception {
        return fileStrategyRouter.routeFor(uploadBo.getChannel()).reupload(uploadBo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <O extends FileDeleteBO> boolean delete(O deleteBo) throws Exception {

        List<FileEntity> fileList = null;
        if (CollectionUtils.isNotEmpty(deleteBo.getPaths())) {
            // 查询path对象的文件记录
            fileList = this.list(new LambdaQueryWrapper<FileEntity>().in(FileEntity::getFilePath, deleteBo.getPaths()));
        } else if (CollectionUtils.isNotEmpty(deleteBo.getUuids())) {
            // 查询Uid对象的文件记录
            fileList = this.list(new LambdaQueryWrapper<FileEntity>().in(FileEntity::getFileId, deleteBo.getUuids()));
        }
        if (CollectionUtils.isNotEmpty(fileList)) {
            for (FileEntity entity : fileList) {
                this.removeById(entity.getId());
                FileInfoBO fileInfoBO = getBeanMapper().map(entity, FileInfoBO.class);
                boolean isDeleted = fileStrategyRouter.routeFor(entity.getChannel()).delete(fileInfoBO);
                if (!isDeleted) {
                    this.removeById(entity);
                }
            }
        }
        return false;
    }

    @Override
    public FileEntity getEntityByPath(String path) throws Exception {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        String cachedId = redisOperation.hGetString(OssRedisKey.UPLOAD_FILE_PATHS.getKey(), DigestUtils.md5Hex(path));
        FileEntity entity = null;
        if (StringUtils.hasText(cachedId)) {
            entity = this.getById(cachedId);
        } else {
            entity = this.getOne(new LambdaQueryWrapper<FileEntity>().eq(FileEntity::getFilePath, path));
        }
        return entity;
    }

    @Override
    public FileEntity getEntityByFileId(String uuid) throws Exception {
        if (!StringUtils.hasText(uuid)) {
            return null;
        }
        String cachedId = redisOperation.hGetString(OssRedisKey.UPLOAD_FILE_IDS.getKey(), uuid);
        FileEntity entity = null;
        if (StringUtils.hasText(cachedId)) {
            entity = this.getById(cachedId);
        } else {
            entity = this.getOne(new LambdaQueryWrapper<FileEntity>().eq(FileEntity::getFileId, uuid));
        }
        return entity;
    }

    @Override
    public List<FileEntity> listEntityByPath(List<String> paths) throws Exception {
        if (CollectionUtils.isEmpty(paths)) {
            return Lists.newArrayList();
        }
        // 查询文件信息
        List<FileEntity> fileList = this.list(new LambdaQueryWrapper<FileEntity>().in(FileEntity::getFilePath, paths));
        if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
        return fileList;
    }

    @Override
    public List<FileEntity> listEntityByFileId(List<String> uuids) throws Exception {
        if (CollectionUtils.isEmpty(uuids)) {
            return Lists.newArrayList();
        }
        // 查询文件信息
        List<FileEntity> fileList = this.list(new LambdaQueryWrapper<FileEntity>().in(FileEntity::getFileId, uuids));
        if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
        return fileList;
    }

    @Override
    public FileInfoVO getByPath(String path, boolean metadata) throws Exception {
        // 1、查询文件信息
        FileEntity entity = this.getEntityByPath(path);
        if (Objects.isNull(entity)) {
            return null;
        }
        // 2、获取文件信息对应的文件描述对象
        FileInfoBO fileInfoBO = getBeanMapper().map(entity, FileInfoBO.class);
        return fileStrategyRouter.routeFor(entity.getChannel()).getFileInfo(fileInfoBO, metadata);
    }

    @Override
    public FileInfoVO getByFileId(String uuid, boolean metadata) throws Exception {
        // 1、查询文件信息
        FileEntity entity = this.getEntityByFileId(uuid);
        if (Objects.isNull(entity)) {
            return null;
        }
        // 2、获取文件信息对应的文件描述对象
        FileInfoBO fileInfoBO = getBeanMapper().map(entity, FileInfoBO.class);
        return fileStrategyRouter.routeFor(entity.getChannel()).getFileInfo(fileInfoBO, metadata);
    }

    @Override
    public List<FileInfoVO> listByPath(List<String> paths, boolean metadata) throws Exception {
        // 查询文件信息
        List<FileEntity> fileList = this.listEntityByPath(paths);
        if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
        return fileList.stream().map(fileEntity -> {
            FileInfoBO fileInfoBO = getBeanMapper().map(fileEntity, FileInfoBO.class);
            return fileStrategyRouter.routeFor(fileEntity.getChannel()).getFileInfo(fileInfoBO, metadata);
        }).collect(Collectors.toList());
    }

    @Override
    public List<FileInfoVO> listByFileId(List<String> uuids, boolean metadata) throws Exception {
        // 查询文件信息
        List<FileEntity> fileList = this.listEntityByFileId(uuids);
        if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
        return fileList.stream().map(fileEntity -> {
            FileInfoBO fileInfoBO = getBeanMapper().map(fileEntity, FileInfoBO.class);
            return fileStrategyRouter.routeFor(fileEntity.getChannel()).getFileInfo(fileInfoBO, metadata);
        }).collect(Collectors.toList());
    }

    @Override
    public FileDownloadResult downloadByPath(String path) throws Exception {
        // 1、查询文件信息
        FileEntity entity = this.getEntityByPath(path);
        if (Objects.isNull(entity)) {
            throw new BizRuntimeException(path + "指向的文件不存在");
        }
        FileInfoBO fileInfoBO = getBeanMapper().map(entity, FileInfoBO.class);
        return fileStrategyRouter.routeFor(entity.getChannel()).download(fileInfoBO);
    }

    @Override
    public FileDownloadResult downloadByFileId(String uuid) throws Exception {
        // 1、查询文件信息
        FileEntity entity = this.getEntityByFileId(uuid);
        if (Objects.isNull(entity)) {
            throw new BizRuntimeException(uuid + "指向的文件不存在");
        }
        FileInfoBO fileInfoBO = getBeanMapper().map(entity, FileInfoBO.class);
        return fileStrategyRouter.routeFor(entity.getChannel()).download(fileInfoBO);
    }


    @Override
    public FileInfoBO getFileInfo(OssChannel channel, String uuid) throws Exception {
        // 1、查询文件信息
        FileEntity entity = this.getEntityByFileId(uuid);
        if (Objects.isNull(entity)) {
            return null;
        }
        // 2、获取文件信息对应的文件描述对象
        return getBeanMapper().map(entity, FileInfoBO.class);
    }

    @Override
    public FileInfoBO deleteById(OssChannel channel, String uuid) throws Exception {
        return null;
    }
}
