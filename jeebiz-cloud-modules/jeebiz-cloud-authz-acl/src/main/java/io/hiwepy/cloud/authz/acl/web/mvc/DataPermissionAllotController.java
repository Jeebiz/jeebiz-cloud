/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.acl.web.mvc;

import io.hiwepy.boot.api.web.BaseApiController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数据权限：角色数据授权,用户数据授权")
@RestController
@RequestMapping("/authz/dbperms/allot")
public class DataPermissionAllotController extends BaseApiController {

}