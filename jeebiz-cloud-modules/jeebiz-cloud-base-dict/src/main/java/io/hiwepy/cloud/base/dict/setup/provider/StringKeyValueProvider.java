package io.hiwepy.cloud.base.dict.setup.provider;

import io.hiwepy.boot.api.provider.KeyValueProvider;
import io.hiwepy.cloud.base.dict.service.IDictPairService;

public class StringKeyValueProvider implements KeyValueProvider<String> {

    private IDictPairService keyValueService;

    public StringKeyValueProvider(IDictPairService keyValueService) {
        this.keyValueService = keyValueService;
    }

    @Override
    public String get(String key) {
        return getKeyValueService().getValue(key);
    }

    public IDictPairService getKeyValueService() {
        return keyValueService;
    }

}
