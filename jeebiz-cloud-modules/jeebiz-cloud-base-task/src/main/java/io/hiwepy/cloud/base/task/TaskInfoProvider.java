package io.hiwepy.cloud.base.task;

import com.alibaba.fastjson2.JSONObject;
import io.hiwepy.cloud.base.task.entity.TaskInfoEntity;

public interface TaskInfoProvider {

    /**
     * 自定义路径前缀（默认返回FilePath）
     * @param entity 任务信息
     * @return
     */
    default String getUrlPrefix(TaskInfoEntity entity){
        return entity.getFilePath();
    };

    /**
     * 自定义任务名称（默认返回Name）
     * @param entity 任务信息
     * @return
     */
    default String getTaskName(TaskInfoEntity entity, JSONObject params){
        return entity.getName();
    };

    /**
     * 自定义任务描述（默认返回Desc）
     * @param entity 任务信息
     * @return
     */
    default String getTaskDesc(TaskInfoEntity entity, JSONObject params){
        return entity.getDesc();
    };

}
