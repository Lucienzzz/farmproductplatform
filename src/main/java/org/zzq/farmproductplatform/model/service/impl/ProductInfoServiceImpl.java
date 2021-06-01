package org.zzq.farmproductplatform.model.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.pojo.Bar;
import org.zzq.farmproductplatform.common.pojo.Pie;
import org.zzq.farmproductplatform.common.utils.BSResultUtil;
import org.zzq.farmproductplatform.exception.BSException;
import org.zzq.farmproductplatform.model.dao.ProductCategoryMapper;
import org.zzq.farmproductplatform.model.dao.ProductDescMapper;
import org.zzq.farmproductplatform.model.dao.ProductInfoMapper;
import org.zzq.farmproductplatform.model.entity.ProductDesc;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 书籍详情服务
 */

@Service
public class ProductInfoServiceImpl implements IProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private ProductDescMapper productDescMapper;

    @Autowired
    private ProductCategoryMapper categoryMapper;


    @Override
    @Cacheable(cacheNames="product",key = "'productInfo_'+#cateId+'_'+#currentPage+#pageSize")
    public List<ProductInfo> findBookListByCateId(int cateId, int currentPage, int pageSize) {
        //设置分页信息，当前页，每页显示多少
        PageHelper.startPage(currentPage, pageSize);
        Example bookInfoExample = new Example(ProductInfo.class);
        Example.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andEqualTo("productCategoryId", cateId);
        criteria.andEqualTo("isShelf", 1);
        bookInfoExample.setOrderByClause("deal_mount DESC,look_mount DESC");
        List<ProductInfo> books = productInfoMapper.selectByExample(bookInfoExample);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(books);
        return pageInfo.getList();
    }

    @Override
    @Cacheable(cacheNames="book",key = "'bookInfo_'+#bookId")
    public ProductInfo findById(Integer bookId) throws BSException {
        Example bookInfoExample = new Example(ProductInfo.class);
        Example.Criteria criteriaOfIsShelf = bookInfoExample.createCriteria();
        criteriaOfIsShelf.andEqualTo("isShelf", 1);
        criteriaOfIsShelf.andEqualTo("bookId", bookId);
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(bookInfoExample);
        if (productInfos == null || productInfos.size() == 0) {
            throw new BSException("你搜索的书籍不存在或未上架！");
        }
        ProductInfo productInfo = productInfos.get(0);
        productInfo.setCategoryName(categoryMapper.selectByPrimaryKey(productInfo.getProductCategoryId()).getName());
        return productInfo;
    }

    public int addLookMount(ProductInfo productInfo){
        productInfo.setLookMount(productInfo.getLookMount() + 1);
        return productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }

    /**
     * 按照一堆条件搜索书籍，查询关键字可以是书名、关键字或ISBN
     *
     * @param keywords
     * @param cateId
     * @param page
     * @param pageSize
     * @param storeId
     * @return
     */
    @Override
    public PageInfo<ProductInfo> findBookListByCondition(String keywords, int cateId, int page, int pageSize, int storeId) {
        PageHelper.startPage(page, pageSize);
        Example bookInfoExample = new Example(ProductInfo.class);
        if (!StringUtils.isEmpty(keywords)) {
            String s = "%" + keywords + "%";
            Example.Criteria criteriaOfKeywords = bookInfoExample.createCriteria();
            criteriaOfKeywords.orLike("name", s);
        }
        if (cateId != 0) {
            //加分类Id查询条件,where (name like ? or author like ? or isbn like ?) and cateId = ?
            Example.Criteria criteriaOfCateId = bookInfoExample.createCriteria();
            criteriaOfCateId.andEqualTo("productCategoryId", cateId);
            bookInfoExample.and(criteriaOfCateId);
        }

        if (storeId == 0) {
            //前台展示，是否上架
            Example.Criteria criteriaOfIsShelf = bookInfoExample.createCriteria();
            criteriaOfIsShelf.andEqualTo("isShelf", 1);
            bookInfoExample.and(criteriaOfIsShelf);
        }else{
            //后台管理
            Example.Criteria criteriaOfStore = bookInfoExample.createCriteria();
            criteriaOfStore.andEqualTo("storeId", storeId);
            bookInfoExample.and(criteriaOfStore);
            bookInfoExample.setOrderByClause("store_time DESC");
        }
        List<ProductInfo> books = productInfoMapper.selectByExample(bookInfoExample);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(books);

        return pageInfo;
    }

    @Override
    public ProductInfo queryBookAvailable(int bookId) {

        Example bookInfoExample = new Example(ProductInfo.class);
        Example.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andEqualTo("bookId", bookId);
        criteria.andEqualTo("isShelf", 1);
        criteria.andGreaterThan("storeMount", 0);
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(bookInfoExample);
        if (productInfos != null && !productInfos.isEmpty()) {
            return productInfos.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="book",allEntries = true)
    public BSResult saveBook(ProductInfo productInfo, String bookDescStr) {

        productInfo.setStoreTime(new Date());
        productInfo.setDiscount(productInfo.getPrice().divide(productInfo.getMarketPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(10.0)));

        productInfo.setLookMount(100);
        productInfo.setDealMount(100);
        productInfo.setIsShelf(1);

        productInfoMapper.insert(productInfo);

        ProductDesc productDesc = new ProductDesc();
        productDesc.setBookDesc(bookDescStr);
        productDesc.setBookId(productInfo.getProductId());
        productDesc.setCreated(new Date());
        productDesc.setUpdated(new Date());
        productDescMapper.insert(productDesc);

        return BSResultUtil.success();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="book",allEntries = true)
    public BSResult updateBook(ProductInfo productInfo, String bookDescStr) {

        productInfo.setDiscount(productInfo.getPrice().divide(productInfo.getMarketPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(10.0)));

        productInfoMapper.updateByPrimaryKeySelective(productInfo);

        ProductDesc productDesc = new ProductDesc();
        productDesc.setBookDesc(bookDescStr);
        productDesc.setBookId(productInfo.getProductId());
        productDesc.setUpdated(new Date());
        if(productDescMapper.selectByPrimaryKey(productInfo.getProductId()) == null ){
            productDesc.setCreated(new Date());
            productDescMapper.insert(productDesc);
            return BSResultUtil.success();
        }
        productDescMapper.updateByPrimaryKeySelective(productDesc);
        return BSResultUtil.success();
    }

    /**
     * 商品下架
     *
     * @param bookId
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames="book",allEntries = true)
    public BSResult changeShelfStatus(int bookId,int shelf) {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(bookId);
        productInfo.setIsShelf(shelf);
        productInfoMapper.updateByPrimaryKeySelective(productInfo);
        return BSResultUtil.success();
    }

    @Override
    public ProductInfo adminFindById(int bookId) throws BSException {
        Example bookInfoExample = new Example(ProductInfo.class);
        Example.Criteria criteriaOfIsShelf = bookInfoExample.createCriteria();
        criteriaOfIsShelf.andEqualTo("bookId", bookId);
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(bookId);
        if(productInfo == null){
            throw new BSException("您搜索的书籍不存在!");
        }
        return productInfo;
    }

    @Override
    @Transactional
    public BSResult deleteBook(int bookId) {
        productInfoMapper.deleteByPrimaryKey(bookId);
        productDescMapper.deleteByPrimaryKey(bookId);
        return BSResultUtil.success();
    }


    @Override
    //@Cacheable(cacheNames="book",key = "'bookInfo_views'+#storeId")
    public List<Pie> getBookViewsPiesByStoreId(Integer storeId) {

        //top 8
        PageHelper.startPage(1, 8);
        Example example = new Example(ProductInfo.class);
        example.createCriteria().andEqualTo("storeId", storeId);
        example.setOrderByClause("look_mount DESC");
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        if(productInfos == null || productInfos.size() == 0){
            return new ArrayList<>();
        }
        List<Pie> pies = new ArrayList<>();


        for (ProductInfo productInfo : productInfos) {
            Pie pie = new Pie();
            pie.setName(productInfo.getName());
            pie.setY(productInfo.getLookMount());
            pies.add(pie);
        }
        pies.get(0).setSliced(true);
        pies.get(0).setSelected(true);
        return pies;
    }

    @Override
    //@Cacheable(cacheNames="book",key = "'bookInfo_sales'+#storeId")
    public Bar getBookSalesBarJson(Integer storeId) {
        //top 6
        PageHelper.startPage(1, 6);

        Example example = new Example(ProductInfo.class);
        example.createCriteria().andEqualTo("storeId", storeId);
        example.setOrderByClause("deal_mount DESC");
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        if(productInfos == null || productInfos.size() == 0){
            return null;
        }
        Bar bar = new Bar();
        bar.setBookNames(productInfos.stream().map(ProductInfo::getName).collect(Collectors.toList()));
        bar.setSales(productInfos.stream().map(ProductInfo::getDealMount).collect(Collectors.toList()));

        return bar;
    }
}
