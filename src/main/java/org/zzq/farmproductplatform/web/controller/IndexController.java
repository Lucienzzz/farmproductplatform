package org.zzq.farmproductplatform.web.controller;


import org.zzq.farmproductplatform.model.entity.ProductCategory;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.service.IProductCateService;
import org.zzq.farmproductplatform.model.service.IProductInfoService;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {

    @Autowired
    private IProductInfoService bookInfoService;

    @Autowired
    private IProductCateService cateService;


    @Value("${book.category}")
    private String BOOK_CATEGORY;

    private List<ProductCategory> categoryList;


    /**
     * 第一次访问首页首页
     *
     * @return
     */
    @RequestMapping({"", "/", "/index"})
    public String index(Model model) {
        if(categoryList == null){
            categoryList = cateService.getCategoryList();
        }
        //获得书籍列表
        List<ProductInfo> productInfos = bookInfoService.findBookListByCateId(categoryList.get(new Random().nextInt(6)).getCateId(), new Random().nextInt(3), 18);
        model.addAttribute("bookInfos", productInfos);

        return "index";
    }


    /**
     * 点击首页导航栏分类后来到这个handler
     *
     * @param cateId
     * @param model
     * @return
     */
    @RequestMapping("/index/category/{cateId}")
    public String bookListByCategoryId(@PathVariable("cateId") int cateId, Model model) {


        List<ProductInfo> productInfos = bookInfoService.findBookListByCateId(cateId, new Random().nextInt(3), 18);
        model.addAttribute("bookInfos", productInfos);
        model.addAttribute("cateId", cateId);
        return "index";
    }

}
