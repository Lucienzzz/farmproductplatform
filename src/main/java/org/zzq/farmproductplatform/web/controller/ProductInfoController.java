package org.zzq.farmproductplatform.web.controller;


import com.github.pagehelper.PageInfo;
import org.zzq.farmproductplatform.exception.BSException;
import org.zzq.farmproductplatform.model.dao.ProductDescMapper;
import org.zzq.farmproductplatform.model.entity.ProductDesc;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductInfoController {



    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private ProductDescMapper productDescMapper;

    @RequestMapping("/info/{productId}")
    public String productInfo(@PathVariable("productId") Integer productId, Model model) throws BSException {
        //查询农产品
        ProductInfo productInfo = productInfoService.findById(productId);
        //查询农产品推荐列表
        List<ProductInfo> recommendProductList = productInfoService.findProductListByCateId(productInfo.getProductCategoryId(), 1, 5);
        //查询农产品详情
        ProductDesc productDesc = productDescMapper.selectByPrimaryKey(productId);
        //增加访问量
        productInfoService.addLookMount(productInfo);
        Collections.shuffle(recommendProductList);
        model.addAttribute("productInfo", productInfo);
        model.addAttribute("productDesc", productDesc);
        model.addAttribute("recommendProductList", recommendProductList);
        return "product_info";
    }


    /**
     * 通过关键字和农产品分类搜索农产品列表
     *
     * @param keywords
     * @return
     */
    @RequestMapping("/list")
    public String productSearchList(@RequestParam(defaultValue = "", required = false) String keywords,
                                 @RequestParam(defaultValue = "0", required = false) int cateId,//分类Id，默认为0，即不按照分类Id查
                                 @RequestParam(defaultValue = "1", required = false) int page,
                                 @RequestParam(defaultValue = "6", required = false) int pageSize,
                                 Model model) {
        keywords = keywords.trim();
        PageInfo<ProductInfo> productPageInfo = productInfoService.findproductListByCondition(keywords, cateId, page, pageSize,0);//storeId为0，不按照商店Id查询

        model.addAttribute("productPageInfo", productPageInfo);

        model.addAttribute("keywords", keywords);

        model.addAttribute("cateId", cateId);

        return "product_list";
    }

}
