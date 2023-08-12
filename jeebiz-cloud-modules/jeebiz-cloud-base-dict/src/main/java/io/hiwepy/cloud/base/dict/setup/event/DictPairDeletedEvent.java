/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.setup.event;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.PairModel;
import org.springframework.biz.context.event.EnhancedEvent;

import java.util.List;

/**
 */
@SuppressWarnings("serial")
public class DictPairDeletedEvent extends EnhancedEvent<List<PairModel>> {

    private final String key;

    public DictPairDeletedEvent(Object source, String key) {
        super(source, Lists.newArrayList());
        this.key = key;
    }

    public DictPairDeletedEvent(Object source, String key, PairModel keyValue) {
        super(source, Lists.newArrayList(keyValue));
        this.key = key;
    }

    public DictPairDeletedEvent(Object source, String key, List<PairModel> keyValues) {
        super(source, keyValues);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
