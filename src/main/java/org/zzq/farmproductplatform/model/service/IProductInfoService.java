package org.zzq.farmproductplatform.model.service;

import com.github.pagehelper.PageInfo;
import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.pojo.Bar;
import org.zzq.farmproductplatform.common.pojo.Pie;
import org.zzq.farmproductplatform.exception.BSException;
import org.zzq.farmproductplatform.model.entity.ProductInfo;

import java.util.List;

public interface IProductInfoService {

    List<ProductInfo> findProductListByCateId(int cateId, int currentPage, int pageSize);

    ProductInfo findById(Integer productId) throws BSException;

    PageInfo<ProductInfo> findproductListByCondition(String keywords, int cateId, int page, int pageSize, int storeId);

    ProductInfo queryproductAvailable(int productId);

    BSResult saveproduct(ProductInfo productInfo, String productDescStr);

    BSResult updateproduct(ProductInfo productInfo, String productDesc);

    BSResult changeShelfStatus(int productId,int shelf);

    ProductInfo adminFindById(int productId) throws BSException;

    BSResult deleteproduct(int productId);

    int addLookMount(ProductInfo productInfo);

    List<Pie> getproductViewsPiesByStoreId(Integer storeId);

    Bar getproductSalesBarJson(Integer storeId);
}
