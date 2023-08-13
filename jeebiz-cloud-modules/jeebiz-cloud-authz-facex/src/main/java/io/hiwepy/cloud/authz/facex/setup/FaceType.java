package io.hiwepy.cloud.authz.facex.setup;

/**
 * 人脸的类型
 */
public enum FaceType {

    /**
     * 表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
     */
    LIVE,
    /**
     * 表示身份证芯片照：二代身份证内置芯片中的人像照片
     */
    IDCARD,
    /**
     * 表示带水印证件照：一般为带水印的小图，如公安网小图
     */
    WATERMARK,
    /**
     * 表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片
     */
    CERT;

}
