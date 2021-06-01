package org.zzq.farmproductplatform.model.service.impl;

import org.zzq.farmproductplatform.model.dao.ProductInfoMapper;
import org.zzq.farmproductplatform.model.dao.OrderDetailMapper;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.entity.OrderDetail;
import org.zzq.farmproductplatform.model.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> findBooksByOrderId(String orderId) {

        Example example = new Example(OrderDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(example);

        List<ProductInfo> productInfos = orderDetails.stream()
                .map(orderDetail -> productInfoMapper.selectByPrimaryKey(orderDetail.getBookId()))
                .collect(Collectors.toList());

        return productInfos;
    }

}
