/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.hiwepy.cloud.oss.flow.dao.entities.FileEntity;

import java.util.List;

public interface IFileService extends IBaseService<FileEntity> {

    /**
     * 获取配置信息
     * @param channel
     * @return
     */
    FileStoreConfig getConfig(OssChannel channel);

    /**
     * 上传文件
     * @param uploadBo
     * @return
     * @param <O>
     * @throws Exception
     */
    <O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception;

    /**
     * 上传文件
     * @param uploadBo
     * @return
     * @param <O>
     * @throws Exception
     */
    <O extends FilesUploadBO> FilesUploadResult upload(O uploadBo) throws Exception;

    /**
     * 重新上传文件
     * @param uploadBo
     * @return
     * @param <O>
     * @throws Exception
     */
    <O extends FileReuploadBO> FileReuploadResult reupload(O uploadBo) throws Exception;

    /**
     * 删除文件
     * @param deleteBo
     * @return
     * @param <O>
     * @throws Exception
     */
    <O extends FileDeleteBO> boolean delete(O deleteBo) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param path
     * @return
     */
    FileEntity getEntityByPath(String path) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param uuid
     * @return
     */
    FileEntity getEntityByFileId(String uuid) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param paths
     * @return
     */
    List<FileEntity> listEntityByPath(List<String> paths) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param uuids
     * @return
     */
    List<FileEntity> listEntityByFileId(List<String> uuids) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param path
     * @param metadata
     * @return
     */
    FileInfoVO getByPath(String path, boolean metadata) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param uuid
     * @param metadata
     * @return
     */
    FileInfoVO getByFileId(String uuid, boolean metadata) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param paths
     * @param metadata
     * @return
     */
    List<FileInfoVO> listByPath(List<String> paths, boolean metadata) throws Exception;

    /**
     * 根据给出的文件相对路径获取文件信息
     * @param uuids
     * @param metadata
     * @return
     */
    List<FileInfoVO> listByFileId(List<String> uuids, boolean metadata) throws Exception;

    /**
     * 根据给出的文件相对路径下载文件
     * @param path    要下载的文件path
     * @return
     */
    FileDownloadResult downloadByPath(String path) throws Exception;

    /**
     * 根据给出的文件Uuid下载文件
     * @param uuid    要下载的文件Uuid
     * @return
     */
    FileDownloadResult downloadByFileId(String uuid) throws Exception;

}
