package org.zzq.farmproductplatform.model.service;

import org.zzq.farmproductplatform.model.entity.ProductInfo;

import java.util.List;

public interface IOrderDetailService {

    List<ProductInfo> findproductsByOrderId(String orderId);
}
