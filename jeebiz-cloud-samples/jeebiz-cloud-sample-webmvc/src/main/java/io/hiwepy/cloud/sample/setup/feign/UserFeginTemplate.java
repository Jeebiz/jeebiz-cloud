/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.feign;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.cloud.api.dto.UserInfoDTO;
import io.hiwepy.cloud.api.dto.UserInfoSimpleDTO;
import io.hiwepy.cloud.sample.exception.BizExceptionCode;
import io.hiwepy.cloud.sample.setup.feign.client.UserClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserFeginTemplate {

	@Autowired
	private UserClientApi userFeignClient;

	public UserInfoDTO getUserInfoByUserId(Long userId) {
		ApiRestResponse<UserInfoDTO> apiResponse = userFeignClient.getUserInfoByUserId(userId);
		if (!apiResponse.isSuccess()) {
			BizExceptionCode.throwByErrorcode(apiResponse.getCode());
		}
		return apiResponse.getData();
	}

	public List<UserInfoDTO> getUserInfoListByUserIds(Collection<Long> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return Lists.newArrayList();
		}
		ApiRestResponse<List<UserInfoDTO>> apiResponse = userFeignClient.getUserInfoListByUserIds(new ArrayList<>(userIds));
		if (!apiResponse.isSuccess()) {
			BizExceptionCode.throwByErrorcode(apiResponse.getCode());
		}
		return apiResponse.getData();
	}

	public UserInfoSimpleDTO getUserInfoSimpleByUserId(Long userId) {
		ApiRestResponse<UserInfoSimpleDTO> apiResponse = userFeignClient.getUserInfoSimpleByUserId(userId);
		if (!apiResponse.isSuccess()) {
			BizExceptionCode.throwByErrorcode(apiResponse.getCode());
		}
		return apiResponse.getData();
	}

	public List<UserInfoSimpleDTO> getUserInfoSimpleListByUserIds(Collection<Long> userIds) {
		if (userIds.size() <= 0) {
			return new ArrayList<>();
		}
		ApiRestResponse<List<UserInfoSimpleDTO>> apiResponse = userFeignClient.getUserInfoSimpleListByUserIds(new ArrayList<>(userIds));
		if (!apiResponse.isSuccess()) {
			BizExceptionCode.throwByErrorcode(apiResponse.getCode());
		}
		return apiResponse.getData();
	}

	public Map<Long, UserInfoDTO> getUserInfoMapByUserIds(Collection<Long> userIds) {
		List<UserInfoDTO> userInfoList = getUserInfoListByUserIds(userIds);
		Map<Long, UserInfoDTO> userInfoMap = userInfoList.stream()
				.collect(Collectors.toMap(UserInfoDTO::getUserId, Function.identity(), (key1, key2) -> key2));
		return userInfoMap;
	}

	public Map<Long, UserInfoSimpleDTO> getUserInfoSimpleMapByUserIds(Collection<Long> userIds) {
		List<UserInfoSimpleDTO> userInfoList = getUserInfoSimpleListByUserIds(userIds);
		Map<Long, UserInfoSimpleDTO> userInfoMap = userInfoList.stream()
				.collect(Collectors.toMap(UserInfoSimpleDTO::getUserId, Function.identity(), (key1, key2) -> key2));
		return userInfoMap;
	}

	public Set<Long> getMatchUserIds(String keywords) {
		ApiRestResponse<Set<Long>> apiResponse = userFeignClient.getMatchUserIds(keywords);
		if (!apiResponse.isSuccess()) {
			BizExceptionCode.throwByErrorcode(apiResponse.getCode());
		}
		return apiResponse.getData();
	}

}
