/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.oss.flow.dao.FileObjectMapper;
import io.hiwepy.cloud.oss.flow.dao.entities.FileObjectEntity;
import io.hiwepy.cloud.oss.flow.service.IFileObjectService;
import org.springframework.stereotype.Service;

@Service
public class FileObjectServiceImpl extends BaseServiceImpl<FileObjectMapper, FileObjectEntity> implements IFileObjectService {

}
