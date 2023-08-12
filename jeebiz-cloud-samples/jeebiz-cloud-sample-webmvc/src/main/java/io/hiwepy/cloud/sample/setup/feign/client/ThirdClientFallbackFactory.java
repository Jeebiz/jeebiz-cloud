package io.hiwepy.cloud.sample.setup.feign.client;

import feign.hystrix.FallbackFactory;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.cloud.api.dto.OssPutDTO;
import io.hiwepy.cloud.autoconfigure.feign.FeignFallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ThirdClientFallbackFactory implements FallbackFactory<ThirdClientApi> {
	
	@Autowired
    protected FeignFallbackHandler fallbackHandler;

    @Override
    public ThirdClientApi create(Throwable throwable) {
        fallbackHandler.handle(throwable);
        return new ThirdClientApi() {

            @Override
            public ApiRestResponse<OssPutDTO> putUrl(OssPutDTO putDto) {
                return ApiRestResponse.fail("Feign调用异常 /oss/aliyun/putUrl 请求降级");
            }

            @Override
            public ApiRestResponse<String> uploadFile(MultipartFile file, String dir) {
                return ApiRestResponse.fail("Feign调用异常 /oss/aliyun/upload 请求降级");
            }

        };
    }
	
  
 
}
