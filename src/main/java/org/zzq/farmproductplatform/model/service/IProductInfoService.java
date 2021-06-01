package org.zzq.farmproductplatform.model.service;

import com.github.pagehelper.PageInfo;
import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.pojo.Bar;
import org.zzq.farmproductplatform.common.pojo.Pie;
import org.zzq.farmproductplatform.exception.BSException;
import org.zzq.farmproductplatform.model.entity.ProductInfo;

import java.util.List;

public interface IProductInfoService {

    List<ProductInfo> findBookListByCateId(int cateId, int currentPage, int pageSize);

    ProductInfo findById(Integer bookId) throws BSException;

    PageInfo<ProductInfo> findBookListByCondition(String keywords, int cateId, int page, int pageSize, int storeId);

    ProductInfo queryBookAvailable(int bookId);

    BSResult saveBook(ProductInfo productInfo, String bookDescStr);

    BSResult updateBook(ProductInfo productInfo, String bookDesc);

    BSResult changeShelfStatus(int bookId,int shelf);

    ProductInfo adminFindById(int bookId) throws BSException;

    BSResult deleteBook(int bookId);

    int addLookMount(ProductInfo productInfo);

    List<Pie> getBookViewsPiesByStoreId(Integer storeId);

    Bar getBookSalesBarJson(Integer storeId);
}
