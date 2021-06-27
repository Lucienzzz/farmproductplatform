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
 * 农产品籍详情服务
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
    public List<ProductInfo> findProductListByCateId(int cateId, int currentPage, int pageSize) {
        //设置分页信息，当前页，每页显示多少
        PageHelper.startPage(currentPage, pageSize);
        Example example = new Example(ProductInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productCategoryId", cateId);
        criteria.andEqualTo("isShelf", 1);
        example.setOrderByClause("deal_mount DESC,look_mount DESC");
        List<ProductInfo> products = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(products);
        return pageInfo.getList();
    }

    @Override
    @Cacheable(cacheNames="product",key = "'productInfo_'+#productId")
    public ProductInfo findById(Integer productId) throws BSException {
        Example example = new Example(ProductInfo.class);
        Example.Criteria criteriaOfIsShelf = example.createCriteria();
        criteriaOfIsShelf.andEqualTo("isShelf", 1);
        criteriaOfIsShelf.andEqualTo("productId", productId);
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        if (productInfos == null || productInfos.size() == 0) {
            throw new BSException("你搜索的农产品不存在或未上架！");
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
     * 按照一堆条件搜索农产品籍，查询关键字可以是农产品名、
     *
     * @param keywords
     * @param cateId
     * @param page
     * @param pageSize
     * @param storeId
     * @return
     */
    @Override
    public PageInfo<ProductInfo> findproductListByCondition(String keywords, int cateId, int page, int pageSize, int storeId) {
        PageHelper.startPage(page, pageSize);
        Example example = new Example(ProductInfo.class);
        if (!StringUtils.isEmpty(keywords)) {
            String s = "%" + keywords + "%";
            Example.Criteria criteriaOfKeywords = example.createCriteria();
            criteriaOfKeywords.orLike("name", s);
        }
        if (cateId != 0) {
            //加分类Id查询条件,where (name like ? or author like ? or isbn like ?) and cateId = ?
            Example.Criteria criteriaOfCateId = example.createCriteria();
            criteriaOfCateId.andEqualTo("productCategoryId", cateId);
            example.and(criteriaOfCateId);
        }

        if (storeId == 0) {
            //前台展示，是否上架
            Example.Criteria criteriaOfIsShelf = example.createCriteria();
            criteriaOfIsShelf.andEqualTo("isShelf", 1);
            example.and(criteriaOfIsShelf);
        }else{
            //后台管理
            Example.Criteria criteriaOfStore = example.createCriteria();
            criteriaOfStore.andEqualTo("storeId", storeId);
            example.and(criteriaOfStore);
            example.setOrderByClause("store_time DESC");
        }
        List<ProductInfo> products = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(products);

        return pageInfo;
    }

    @Override
    public ProductInfo queryproductAvailable(int productId) {

        Example example = new Example(ProductInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        criteria.andEqualTo("isShelf", 1);
        criteria.andGreaterThan("storeMount", 0);
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        if (productInfos != null && !productInfos.isEmpty()) {
            return productInfos.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="product",allEntries = true)
    public BSResult saveproduct(ProductInfo productInfo, String productDescStr) {

        productInfo.setStoreTime(new Date());
        productInfo.setDiscount(productInfo.getPrice().divide(productInfo.getMarketPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(10.0)));

        productInfo.setLookMount(100);
        productInfo.setDealMount(100);
        productInfo.setIsShelf(1);

        productInfoMapper.insert(productInfo);

        ProductDesc productDesc = new ProductDesc();
        productDesc.setproductDesc(productDescStr);
        productDesc.setProductId(productInfo.getProductId());
        productDesc.setCreated(new Date());
        productDesc.setUpdated(new Date());
        productDescMapper.insert(productDesc);

        return BSResultUtil.success();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="product",allEntries = true)
    public BSResult updateproduct(ProductInfo productInfo, String productDescStr) {

        productInfo.setDiscount(productInfo.getPrice().divide(productInfo.getMarketPrice(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(10.0)));

        productInfoMapper.updateByPrimaryKeySelective(productInfo);

        ProductDesc productDesc = new ProductDesc();
        productDesc.setproductDesc(productDescStr);
        productDesc.setProductId(productInfo.getProductId());
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
     * @param productId
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames="product",allEntries = true)
    public BSResult changeShelfStatus(int productId,int shelf) {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        productInfo.setIsShelf(shelf);
        productInfoMapper.updateByPrimaryKeySelective(productInfo);
        return BSResultUtil.success();
    }

    @Override
    public ProductInfo adminFindById(int productId) throws BSException {
        Example example = new Example(ProductInfo.class);
        Example.Criteria criteriaOfIsShelf = example.createCriteria();
        criteriaOfIsShelf.andEqualTo("productId", productId);
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if(productInfo == null){
            throw new BSException("您搜索的农产品籍不存在!");
        }
        return productInfo;
    }

    @Override
    @Transactional
    public BSResult deleteproduct(int productId) {
        productInfoMapper.deleteByPrimaryKey(productId);
        productDescMapper.deleteByPrimaryKey(productId);
        return BSResultUtil.success();
    }


    @Override
    //@Cacheable(cacheNames="product",key = "'productInfo_views'+#storeId")
    public List<Pie> getproductViewsPiesByStoreId(Integer storeId) {

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
    //@Cacheable(cacheNames="product",key = "'productInfo_sales'+#storeId")
    public Bar getproductSalesBarJson(Integer storeId) {
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
        bar.setproductNames(productInfos.stream().map(ProductInfo::getName).collect(Collectors.toList()));
        bar.setSales(productInfos.stream().map(ProductInfo::getDealMount).collect(Collectors.toList()));

        return bar;
    }
}
