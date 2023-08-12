/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.setup.provider;

import com.alibaba.fastjson.JSONObject;
import io.hiwepy.cloud.authz.facex.setup.FaceProvider;

public interface FaceRecognitionProvider {

    /**
     * Provider
     * @return
     */
    FaceProvider getType();

    /**
     * 人脸检测与属性分析：
     * 1、人脸检测：检测图片中的人脸并标记出位置信息;
     * 2、人脸关键点：展示人脸的核心关键点信息，及150个关键点信息。
     * 3、人脸属性值：展示人脸属性信息，如年龄、性别等。
     * 4、人脸质量信息：返回人脸各部分的遮挡、光照、模糊、完整度、置信度等信息。
     * @param imageBytes 人脸图片文件
     * @return
     */
    JSONObject detect(byte[] imageBytes, String filename) throws Exception;

    /**
     * 活体检测:
     * 1、人脸基础信息：包括人脸框位置，人脸空间旋转角度，人脸置信度等信息。
     * 2、 人脸质量检测：判断人脸的遮挡、光照、模糊度、完整度等质量信息。可用于判断上传的人脸是否符合标准。
     * 3、 基于图片的活体检测：基于单张图片，判断图片中的人脸是否为二次翻拍（举例：如用户A用手机拍摄了一张包含人脸的图片一，用户B翻拍了图片一得到了图片二，并用图片二伪造成用户A去进行识别操作，这种情况普遍发生在金融开户、实名认证等环节）。此能力可用于H5场景下的一些人脸采集场景中，增加人脸注册的安全性和真实性。
     * @param image 人脸图片文件
     * @return
     */
    JSONObject verify(byte[] imageBytes) throws Exception;

    /**
     * 人脸对比：
     * 1、两张人脸图片相似度对比：比对两张图片中人脸的相似度，并返回相似度分值；
     * 2、 多种图片类型：支持生活照、证件照、身份证芯片照、带网纹照四种类型的人脸对比；
     * 3、活体检测控制：基于图片中的破绽分析，判断其中的人脸是否为二次翻拍（举例：如用户A用手机拍摄了一张包含人脸的图片一，用户B翻拍了图片一得到了图片二，并用图片二伪造成用户A去进行识别操作，这种情况普遍发生在金融开户、实名认证等环节。）；
     * 4、质量检测控制：分析图片的中人脸的模糊度、角度、光照强度等特征，判断图片质量；
     * @param userId 用户ID
     * @param image 人脸图片文件
     * @return
     */
    JSONObject match(String userId, String filename, byte[] imageBytes) throws Exception;

    /**
     * 人脸搜索：
     * 1、人脸搜索：也称为1：N识别，在指定人脸集合中，找到最相似的人脸；
     * 2、人脸搜索 M：N识别：也称为M：N识别，待识别图片中含有多个人脸时，在指定人脸集合中，找到这多个人脸分别最相似的人脸
     * @param image 人脸图片文件
     * @return
     */
    JSONObject search(String filename, byte[] imageBytes) throws Exception;

    /**
     * 人脸融合：对两张人脸进行融合处理，生成的人脸同时具备两张人脸的外貌特征（并不是换脸）
     *
     * 1、指定人脸：当图片中有多张人脸时，可以指定某一张人脸与模板图进行融合
     * 2、 图像融合：将检测到的两张人脸图片进行融合，输出一张融合后的人脸
     * 3、 黄反识别：利用图像识别能力，判断图片中是否存在色情、暴恐血腥场景、政治敏感人物
     * @param template 人脸融合模板图片文件
     * @param target 人脸融合人脸图片文件
     * @return
     */
    JSONObject merge(byte[] template, byte[] target) throws Exception;


}
