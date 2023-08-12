package io.hiwepy.cloud.base.dict.setup.provider;

import hitool.core.collections.CollectionUtils;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.provider.KeyValueProvider;
import io.hiwepy.cloud.base.dict.service.IDictPairService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapKeyValueProvider implements KeyValueProvider<Map<String, String>> {

    private IDictPairService keyValueService;

    public MapKeyValueProvider(IDictPairService keyValueService) {
        this.keyValueService = keyValueService;
    }

    @Override
    public Map<String, String> get(String key) {
        List<PairModel> pairList = getKeyValueService().getPairValues(key);
        // 返回的结果对象
        Map<String, String> rtMap = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(pairList)) {
            for (PairModel pairModel : pairList) {
                rtMap.put(pairModel.getKey(), pairModel.getValue());
            }
        }
        return rtMap;
    }

    public IDictPairService getKeyValueService() {
        return keyValueService;
    }

}
