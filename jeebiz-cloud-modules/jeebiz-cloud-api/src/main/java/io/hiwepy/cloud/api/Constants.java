package io.hiwepy.cloud.api;

public abstract class Constants extends io.hiwepy.boot.api.Constants {

    public final static String OSS_IMAGE_FORMAT = "?x-oss-process=image/format,%s";

    public static class Normal {

        public static final Integer IS_DELETE_0 = 0; //未删除
        public static final Integer IS_DELETE_1 = 1; //已删除

        public static final Integer IS_DEFAULT_NO = 0; //非默认
        public static final Integer IS_DEFAULT_YES = 1; //是默认

        public static final Integer GENDER_MAIL = 1;
        public static final Integer GENDER_FEMAIL = 2;

        public static final Integer IS_STATUS_NO = 0; //停用
        public static final Integer IS_STATUS_YES = 1; //启用

    }

}
