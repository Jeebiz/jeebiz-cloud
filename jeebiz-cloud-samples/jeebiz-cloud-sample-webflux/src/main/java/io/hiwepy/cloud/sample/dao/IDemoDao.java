package io.hiwepy.cloud.sample.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDemoDao {

	/**
	 * 获取指定ID关联的信息，以便进行删除前的逻辑检查
	 * @param list
	 * @return
	 */
	List<Map<String,String>> getDependencies(List<String> list);
	
}
