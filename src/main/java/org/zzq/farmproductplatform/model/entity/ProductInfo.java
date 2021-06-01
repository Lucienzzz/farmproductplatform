package org.zzq.farmproductplatform.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "product_info")
public class ProductInfo {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "product_category_id")
    private Integer productCategoryId;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "name")
    private String name;

    /**
     * 简介
     */
    @Column(name = "outline")
    private String outline;

    /**
     * 商品详情
     */
    @Column(name = "detail")
    private String detail;


    @Column(name = "price")
    private BigDecimal price;

    /**
     * 市场价\定价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 会员价格
     */
    @Column(name = "member_price")
    private BigDecimal memberPrice;

    /**
     * 成交量
     */
    @Column(name = "deal_mount")
    private Integer dealMount;

    /**
     * 浏览量
     */
    @Column(name = "look_mount")
    private Integer lookMount;

    @Column(name = "discount")
    private BigDecimal discount;

    /**
     * 版面图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 库存数量
     */
    @Column(name = "store_mount")
    private Integer storeMount;

    /**
     * 入库时间
     */
    @Column(name = "store_time")
    private Date storeTime;

    /**
     * 是否上架
     */
    @Column(name = "is_shelf")
    private Integer isShelf;

    @Transient
    private String categoryName;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public Integer getDealMount() {
        return dealMount;
    }

    public void setDealMount(Integer dealMount) {
        this.dealMount = dealMount;
    }

    public Integer getLookMount() {
        return lookMount;
    }

    public void setLookMount(Integer lookMount) {
        this.lookMount = lookMount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStoreMount() {
        return storeMount;
    }

    public void setStoreMount(Integer storeMount) {
        this.storeMount = storeMount;
    }

    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    public Integer getIsShelf() {
        return isShelf;
    }

    public void setIsShelf(Integer isShelf) {
        this.isShelf = isShelf;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}