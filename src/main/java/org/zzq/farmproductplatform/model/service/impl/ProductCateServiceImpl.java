package org.zzq.farmproductplatform.model.service.impl;

import org.zzq.farmproductplatform.model.dao.ProductCategoryMapper;
import org.zzq.farmproductplatform.model.entity.ProductCategory;
import org.zzq.farmproductplatform.model.service.IProductCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductCateServiceImpl implements IProductCateService {


    @Autowired
    private ProductCategoryMapper productCategoryMapper;


    @Override
    public List<ProductCategory> getCategoryList() {
        return productCategoryMapper.selectAll();
    }
}
