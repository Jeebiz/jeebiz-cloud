package io.hiwepy.cloud.sample.setup.feign.client;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.cloud.api.dto.OssPutDTO;
import io.hiwepy.cloud.autoconfigure.FeignClientsExtConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "jeebiz-cloud-third", fallbackFactory = ThirdClientFallbackFactory.class, configuration = FeignClientsExtConfiguration.class)
public interface ThirdClientApi {

	/**
	 * 阿里云OSS上传
	 */
	@PostMapping(value = "/oss/aliyun/putUrl")
	ApiRestResponse<OssPutDTO> putUrl(@RequestBody OssPutDTO putDto);

	/**
	 * 阿里云OSS上传
	 */
	@PostMapping(value = "/oss/aliyun/upload")
	ApiRestResponse<String> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam String dir);

}
