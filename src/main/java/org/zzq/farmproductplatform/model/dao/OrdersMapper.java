package org.zzq.farmproductplatform.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.zzq.farmproductplatform.common.utils.MyMapper;
import org.zzq.farmproductplatform.model.entity.Orders;
@Component
public interface OrdersMapper extends MyMapper<Orders> {
}