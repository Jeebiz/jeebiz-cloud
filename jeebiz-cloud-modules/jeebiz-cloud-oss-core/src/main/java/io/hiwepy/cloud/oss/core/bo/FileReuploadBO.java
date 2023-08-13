package io.hiwepy.cloud.oss.core.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileReuploadBO extends FileStoreBO {

    /**
     * 原文件UUID
     */
    private String uuid;

    /**
     * 文件对象
     */
    private MultipartFile file;

}
