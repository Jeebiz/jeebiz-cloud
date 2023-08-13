/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.core.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.common.collect.Sets;
import hitool.core.format.ByteUnitFormat;
import io.hiwepy.cloud.oss.core.bo.FileMetaData;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class OssUtils {

    private static String[] exts = new String[]{"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"};
    private static String base64Prefix = "data:image/{};base64,{}";

    public static boolean isImage(MultipartFile file) {
        Optional<MediaType> mOptional = MediaTypeFactory.getMediaType(file.getOriginalFilename());
        if (mOptional.isPresent() && StringUtils.equalsIgnoreCase(mOptional.get().getType(), "image")) {
            return true;
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Stream.of(exts).anyMatch(item -> StringUtils.equalsIgnoreCase(item, extension));
    }

    public static boolean isImageByExtension(String ext) {
        if (StringUtils.isEmpty(ext))
            return false;
        for (int i = 0; i < exts.length; i++) {
            if (StringUtils.equalsIgnoreCase(ext, exts[i]))
                return true;
        }
        return false;
    }

    public static boolean thumbable(MultipartFile file, Integer width, Integer height) {
        if (Objects.nonNull(width) && width > 0 && Objects.nonNull(height) && height > 0) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            return Stream.of(exts).anyMatch(item -> StringUtils.equalsIgnoreCase(item, extension));
        }
        return false;
    }

    public static boolean thumbableByExtension(String ext, Integer width, Integer height) {
        if (Objects.nonNull(width) && width > 0 && Objects.nonNull(height) && height > 0) {
            return isImageByExtension(ext);
        }
        return false;
    }


    public static double thumbScale(MultipartFile file) throws IOException {
        double imageSize = ByteUnitFormat.B.toDouble(ByteUnitFormat.M, file.getSize());
        // 图片大于2M,则进行压缩
        if (imageSize >= 2) {
            double scale = 1D / imageSize;
            if (imageSize >= 15) {
                scale = 5 / imageSize;
            } else if (imageSize >= 10) {
                scale = 4D / imageSize;
            } else if (imageSize >= 4) {
                scale = 2D / imageSize;
            } else {
                scale = 1D / imageSize;
            }
            return scale;
        }
        return 1D;
    }

    public static InputStream thumbStream(MultipartFile file) throws IOException {
        InputStream inputStream = null;
        if (isImage(file)) {
            double scale = thumbScale(file);
            if (scale < 1) {
                try (ByteArrayInputStream input = new ByteArrayInputStream(file.getBytes());
                     ByteArrayOutputStream output = new ByteArrayOutputStream();) {
                    Thumbnails.of(input).scale(thumbScale(file)).toOutputStream(output);
                    inputStream = new ByteArrayInputStream(output.toByteArray());
                }
            }
        } else {
            inputStream = file.getInputStream();
        }
        return inputStream;
    }

    public static Set<FileMetaData> metaDataSet(File file) {
        Set<FileMetaData> metaDataSet = Sets.newHashSet();
        try (InputStream inputStream = FileUtils.openInputStream(file);) {
            metaDataSet.addAll(metaDataSet(inputStream));
        } catch (Exception e) {
        }
        return metaDataSet;
    }

    public static Set<FileMetaData> metaDataSet(InputStream inputStream) throws ImageProcessingException, IOException {
        Set<FileMetaData> metaDataSet = Sets.newHashSet();
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //格式化输出[directory.getName()] - tag.getTagName() = tag.getDescription()
                System.out.format("[%s] - %s = %s%n", directory.getName(), tag.getTagName(), tag.getDescription());
                metaDataSet.add(new FileMetaData(directory.getName(), tag.getTagName()));
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
        return metaDataSet;
    }

    /**
     * 文件File类型转BASE64
     *
     * @param file
     * @return
     */
    public static String toBase64(MultipartFile file) throws IOException {
        return toBase64(file.getOriginalFilename(), file.getBytes());
    }

    /**
     * @param fileName
     * @param bytes
     * @return
     * @throws IOException
     */
    public static String toBase64(String fileName, byte[] bytes) {
        String imageBase64 = Base64.encodeBase64String(bytes);
        return MessageFormatter.format(base64Prefix, FilenameUtils.getExtension(fileName), imageBase64).getMessage();
    }

}
