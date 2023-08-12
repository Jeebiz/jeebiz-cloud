/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.feign;

import io.hiwepy.cloud.sample.setup.feign.client.ThirdClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThirdFeginTemplate {

	@Autowired
	private ThirdClientApi thirdClientApi;


}
