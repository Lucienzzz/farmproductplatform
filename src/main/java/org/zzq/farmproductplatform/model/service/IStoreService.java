package org.zzq.farmproductplatform.model.service;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.model.entity.Store;

import java.util.List;

public interface IStoreService {
    Store findStoreByUserId(Integer userId);

    Store findById(int storeId);

    List<Store> findStores();

    BSResult updateStore(Store store);

    BSResult deleteStore(int storeId);

    BSResult addStore(Store store);
}
