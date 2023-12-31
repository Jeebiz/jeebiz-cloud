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
public class FilesUploadBO extends FileStoreBO {

    /**
     * 文件对象数组
     */
    private MultipartFile[] files;

}
