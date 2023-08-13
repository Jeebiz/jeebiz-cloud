package io.hiwepy.cloud.base.guard.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.guard.dao.IAntisamyDao;
import io.hiwepy.cloud.base.guard.dao.entities.AntisamyModel;
import io.hiwepy.cloud.base.guard.service.IAntisamyService;
import org.springframework.stereotype.Service;

@Service
public class AntisamyServiceImpl extends BaseServiceImpl<IAntisamyDao, AntisamyModel> implements IAntisamyService {


}
