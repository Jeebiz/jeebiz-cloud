package io.hiwepy.cloud.oss.core.strategy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.github.hiwepy.ip2region.spring.boot.IP2regionTemplate;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.oss.core.OssProperties;
import io.hiwepy.cloud.oss.core.OssRedisKey;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractFileStoreStrategy implements FileStoreStrategy, InitializingBean, ApplicationEventPublisherAware {

    protected static final String FOLDER_SEPARATOR = "/";

    @Autowired
    private OssProperties ossProperties;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper beanMapper;
    @Autowired
    private FileInfoProvider fileInfoProvider;
    @Autowired
    private Sequence sequence;
    @Autowired
    private IP2regionTemplate ipTemplate;
    @Autowired
    private RedisOperationTemplate redisOperation;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional
    public <O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception {
        // 1、验证请求，如果有不满足有要求则应抛出异常
        boolean flag = this.preCheck(uploadBo);
        log.info("File Upload Pre Check : {}", flag);
        if (!flag) {
            return null;
        }
        // 2、钩子方法 用于扩展
        customizedMethod(uploadBo);
        // 3、通过检查则继续处理上传
        FileUploadResult uploadRt = this.handleUpload(uploadBo);
        // 4、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
        if (Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
            recordUploadFlow(uploadBo, uploadRt);
        }
        // 5、返回上传结果
        return uploadRt;
    }

    protected <O extends FileUploadBO> boolean preCheck(O uploadBo) throws Exception {
        // 2.4、黑名单
        String blacklistKey = OssRedisKey.UPLOAD_BLACKLIST.getKey();
        if (getRedisOperation().sHasKey(blacklistKey, uploadBo.getUserId())) {
            throw new BizRuntimeException(ApiCode.SC_FORBIDDEN, "file.upload.backlist.limit");
        }
        return true;
    }

    protected <O extends FilesUploadBO> void customizedMethod(O uploadBo) throws Exception {
        // TODO Auto-generated method stub
    }

    ;


    protected final <O extends FileUploadBO> FileUploadResult handleUpload(O uploadBo) throws Exception {

        FileInfoVO fileDto = this.handleFileUpload(uploadBo, uploadBo.getFile(), 0);
        log.info("File Upload Data : {}", JSONObject.toJSONString(fileDto));

        FileUploadResult uploadRt = FileUploadResult.builder()
                .channel(uploadBo.getChannel())
                .file(fileDto)
                .status(1)
                .userId(uploadBo.getUserId())
                .build();
        return uploadRt;
    }

    ;

    protected <O extends FileUploadBO> void recordUploadFlow(O uploadBo, FileUploadResult uploadRt) throws Exception {

        // ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());

        this.recordFileEntity(countryByIp, uploadBo, uploadBo.getFile(), uploadRt.getFile());

    }

    ;

    @Override
    @Transactional
    public <O extends FilesUploadBO> FilesUploadResult upload(O uploadBo) throws Exception {
        // 1、验证请求，如果有不满足有要求则应抛出异常
        boolean flag = this.preCheck(uploadBo);
        if (!flag) {
            return null;
        }
        // 2、钩子方法 用于扩展
        customizedMethod(uploadBo);
        // 3、通过检查则继续处理上传
        FilesUploadResult uploadRt = this.handleUpload(uploadBo);
        // 4、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
        if (Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
            recordUploadFlow(uploadBo, uploadRt);
        }
        // 5、返回上传结果
        return uploadRt;
    }

    protected <O extends FilesUploadBO> boolean preCheck(O uploadBo) throws Exception {
        // 2.4、黑名单
        String blacklistKey = OssRedisKey.UPLOAD_BLACKLIST.getKey();
        if (getRedisOperation().sHasKey(blacklistKey, uploadBo.getUserId())) {
            throw new BizRuntimeException(ApiCode.SC_FORBIDDEN, "file.upload.backlist.limit");
        }
        return true;
    }

    protected <O extends FileUploadBO> void customizedMethod(O uploadBo) throws Exception {
        // TODO Auto-generated method stub
    }

    ;

    protected <O extends FilesUploadBO> FilesUploadResult handleUpload(O uploadBo) throws Exception {

        List<FileInfoVO> fileList = Lists.newArrayList();
        int index = 0;
        for (MultipartFile file : uploadBo.getFiles()) {
            FileInfoVO fileDto = this.handleFileUpload(uploadBo, file, index);
            fileList.add(fileDto);
            index++;
        }

        FilesUploadResult uploadRt = FilesUploadResult.builder()
                .channel(uploadBo.getChannel())
                .files(fileList)
                .status(1)
                .userId(uploadBo.getUserId())
                .build();
        return uploadRt;
    }


    protected <O extends FilesUploadBO> void recordUploadFlow(O uploadBo, FilesUploadResult uploadRt) throws Exception {

        // ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());

        for (FileInfoVO fileInfoVO : uploadRt.getFiles()) {
            MultipartFile file = Arrays.stream(uploadBo.getFiles()).filter(mFile -> fileInfoVO.getName().equals(mFile.getOriginalFilename())).findFirst().get();
            this.recordFileEntity(countryByIp, uploadBo, file, fileInfoVO);
        }

    }

    ;

    protected abstract FileInfoVO handleFileUpload(FileStoreBO uploadBo, MultipartFile file, int index) throws Exception;

    @Override
    public boolean delete(FileInfoBO fileInfoBO) throws Exception {
        if (Objects.isNull(fileInfoBO)) {
            return Boolean.FALSE;
        }
        return this.handleFileDelete(fileInfoBO);
    }

    protected abstract boolean handleFileDelete(FileInfoBO fileInfoBO) throws Exception;

    @Override
    @Transactional
    public <O extends FileReuploadBO> FileReuploadResult reupload(O uploadBo) throws Exception {

        // 1、查询文件信息
        FileInfoBO fileInfoBO = fileInfoProvider.getFileInfo(this.getChannel(), uploadBo.getUuid());
        if (Objects.isNull(fileInfoBO)) {
            throw new BizRuntimeException(uploadBo.getUuid() + "指向的文件不存在");
        }
        // 2、验证请求，如果有不满足有要求则应抛出异常
        boolean flag = this.preCheck(uploadBo);
        if (!flag) {
            return null;
        }
        // 3、钩子方法 用于扩展
        customizedMethod(uploadBo);
        // 4、通过检查则继续处理上传
        FileReuploadResult uploadRt = this.handleReupload(uploadBo);
        // 5、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
        if (Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
            recordReuploadFlow(uploadBo, uploadRt);
        }
        // 6、返回上传结果
        return uploadRt;
    }

    protected <O extends FileReuploadBO> boolean preCheck(O uploadBo) throws Exception {
        // 2.4、黑名单
        String blacklistKey = OssRedisKey.UPLOAD_BLACKLIST.getKey();
        if (getRedisOperation().sHasKey(blacklistKey, uploadBo.getUserId())) {
            throw new BizRuntimeException(ApiCode.SC_FORBIDDEN, "file.upload.backlist.limit");
        }
        return true;
    }

    protected <O extends FileReuploadBO> void customizedMethod(O uploadBo) throws Exception {
        // TODO Auto-generated method stub
    }

    ;

    protected <O extends FileReuploadBO> FileReuploadResult handleReupload(O uploadBo) throws Exception {

        // 1、查询旧文件信息
        FileInfoBO fileInfoBO = fileInfoProvider.getFileInfo(this.getChannel(), uploadBo.getUuid());
        if (Objects.isNull(fileInfoBO)) {
            throw new BizRuntimeException(uploadBo.getUuid() + "指向的文件不存在");
        }

        // 2、上传新文件
        FileInfoVO fileInfoVo = this.handleFileUpload(uploadBo, uploadBo.getFile(), 0);

        // 3、删除旧的文件
        this.delete(fileInfoBO);

        // 4、返回操作结果
        FileReuploadResult uploadRt = FileReuploadResult.builder()
                .channel(uploadBo.getChannel())
                .file(fileInfoVo)
                .status(1)
                .userId(uploadBo.getUserId())
                .build();
        return uploadRt;
    }

    protected <O extends FileReuploadBO> void recordReuploadFlow(O uploadBo, FileReuploadResult uploadRt) throws Exception {
        // ip获取国家
        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
        this.recordFileEntity(countryByIp, uploadBo, uploadBo.getFile(), uploadRt.getFile());
    }

    ;

    protected <O extends FileStoreBO> void recordFileEntity(String countryByIp, O uploadBo, MultipartFile file, FileInfoVO fileInfoVO) throws Exception {

        FileInfoBO fileInfoBO = FileInfoBO.builder()
                .appId(uploadBo.getAppId())
                .appChannel(uploadBo.getAppChannel())
                .appVer(uploadBo.getAppVer())
                .bizId(uploadBo.getBizId())
                .channel(this.getChannel())
                .bucket(fileInfoVO.getBucket())
                .fileId(fileInfoVO.getUuid())
                .fileName(fileInfoVO.getName())
                .filePath(fileInfoVO.getPath())
                .fileExt(fileInfoVO.getExt())
                .fileSize(fileInfoVO.getSize())
                .fileMetadata(JSONObject.toJSONString(fileInfoVO.getMetadata()))
                .ipAddress(uploadBo.getIpAddress())
                .userId(uploadBo.getUserId())
                .region(countryByIp)
                .orderBy(fileInfoVO.getIndex())
                .build();
        fileInfoBO.setCreateTime(LocalDateTime.now());
        fileInfoBO.setCreator(uploadBo.getUserId());

        fileInfoProvider.recordFlow(this.getChannel(), fileInfoBO);

        this.afterRecordFileEntity(fileInfoBO, file, fileInfoVO);

        log.info(" Upload File : {}", JSONObject.toJSONString(fileInfoBO));

    }

    protected void afterRecordFileEntity(FileInfoBO fileInfo, MultipartFile file, FileInfoVO fileInfoVO) throws IOException {
        // 存储文件路径与记录ID对照缓存
        redisOperation.hSet(OssRedisKey.UPLOAD_FILE_PATHS.getKey(), DigestUtils.md5Hex(fileInfo.getFilePath()), fileInfo.getId());
        // 存储文件路径与记录ID对照缓存
        redisOperation.hSet(OssRedisKey.UPLOAD_FILE_IDS.getKey(), fileInfo.getFileId(), fileInfo.getId());
    }

    @Override
    public FileInfoVO getFileInfo(FileInfoBO fileInfo, boolean metadata) {
        if (Objects.nonNull(fileInfo)) {
            return this.handleFileMetadata(fileInfo, metadata);
        }
        return null;
    }

    protected abstract FileInfoVO handleFileMetadata(FileInfoBO fileInfo, boolean metadata);

    @Override
    public FileDownloadResult download(FileInfoBO fileInfo) throws Exception {
        if (Objects.isNull(fileInfo)) {
            throw new BizRuntimeException(fileInfo.getFilePath() + "指向的文件不存在");
        }
        FileDownloadResult downloadRt = FileDownloadResult.builder()
                .channel(fileInfo.getChannel())
                .file(this.handleFileMetadata(fileInfo, Boolean.FALSE))
                .status(1)
                .userId(fileInfo.getUserId())
                .build();
        this.handleFileDownload(fileInfo, downloadRt);
        return downloadRt;
    }

    protected abstract void handleFileDownload(FileInfoBO fileInfo, FileDownloadResult downloadRt) throws Exception;

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public OssProperties getOssProperties() {
        return ossProperties;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected Mapper getBeanMapper() {
        return beanMapper;
    }

    protected Sequence getSequence() {
        return sequence;
    }

    public IP2regionTemplate getIpTemplate() {
        return ipTemplate;
    }

    public RedisOperationTemplate getRedisOperation() {
        return redisOperation;
    }

}
