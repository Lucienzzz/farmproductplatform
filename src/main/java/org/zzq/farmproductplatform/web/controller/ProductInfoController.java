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
@RequestMapping("/book")
public class ProductInfoController {



    @Autowired
    private IProductInfoService bookInfoService;

    @Autowired
    private ProductDescMapper productDescMapper;

    /**
     * 查询某一本书籍详情
     *
     * @param bookId
     * @param model
     * @return
     */
    @RequestMapping("/info/{bookId}")
    public String bookInfo(@PathVariable("bookId") Integer bookId, Model model) throws BSException {
        //查询书籍
        ProductInfo productInfo = bookInfoService.findById(bookId);
        //查询书籍推荐列表
        List<ProductInfo> recommendBookList = bookInfoService.findBookListByCateId(productInfo.getProductCategoryId(), 1, 5);
        //查询书籍详情
        ProductDesc productDesc = productDescMapper.selectByPrimaryKey(bookId);
        //增加访问量
        bookInfoService.addLookMount(productInfo);
        Collections.shuffle(recommendBookList);
        model.addAttribute("bookInfo", productInfo);
        model.addAttribute("bookDesc", productDesc);
        model.addAttribute("recommendBookList", recommendBookList);
        return "book_info";
    }


    /**
     * 通过关键字和书籍分类搜索书籍列表
     *
     * @param keywords
     * @return
     */
    @RequestMapping("/list")
    public String bookSearchList(@RequestParam(defaultValue = "", required = false) String keywords,
                                 @RequestParam(defaultValue = "0", required = false) int cateId,//分类Id，默认为0，即不按照分类Id查
                                 @RequestParam(defaultValue = "1", required = false) int page,
                                 @RequestParam(defaultValue = "6", required = false) int pageSize,
                                 Model model) {
        keywords = keywords.trim();
        PageInfo<ProductInfo> bookPageInfo = bookInfoService.findBookListByCondition(keywords, cateId, page, pageSize,0);//storeId为0，不按照商店Id查询

        model.addAttribute("bookPageInfo", bookPageInfo);

        model.addAttribute("keywords", keywords);

        model.addAttribute("cateId", cateId);

        return "book_list";
    }

}
