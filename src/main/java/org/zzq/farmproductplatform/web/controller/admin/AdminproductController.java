package org.zzq.farmproductplatform.web.controller.admin;

import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zzq.farmproductplatform.common.utils.IDUtils;
import org.zzq.farmproductplatform.exception.BSException;
import org.zzq.farmproductplatform.model.dao.ProductDescMapper;
import org.zzq.farmproductplatform.model.entity.ProductDesc;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.entity.Store;
import org.zzq.farmproductplatform.model.service.IProductInfoService;
import org.zzq.farmproductplatform.model.service.IStoreService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/product")
@RequiresPermissions("product-manage")
public class AdminproductController {

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private ProductDescMapper productDescMapper;

    @Autowired
    private IStoreService storeService;

    @Value("${image.url.prefix}")
    private String urlPrefix;

    @RequestMapping("toAddition")
    @RequiresPermissions("product-add")
    public String toAddition() {
        return "admin/product/add";
    }

    @RequestMapping("/addition")
    @RequiresPermissions("product-add")
    public String addproduct(ProductInfo productInfo, String productDesc, MultipartFile pictureFile, HttpServletRequest request) throws Exception {

        uploadPicture(productInfo, pictureFile, request);
        productInfoService.saveproduct(productInfo, productDesc);

        return "redirect:/admin/product/list";
    }

    @RequestMapping(value = "/list")
    @RequiresPermissions("product-query")
    public String productList(@RequestParam(defaultValue = "", required = false) String keywords,
                           @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                           HttpSession session,
                           Model model) {
        keywords = keywords.trim();
        Store store = (Store) session.getAttribute("loginStore");

        if (store != null) {
            PageInfo<ProductInfo> products = productInfoService.findproductListByCondition(keywords, 0, page, 10, store.getStoreId());
            model.addAttribute("productPageInfo", products);
            model.addAttribute("keywords", keywords);
        } else {
            model.addAttribute("exception", "您请求的资源不存在");
            return "exception";
        }

        return "admin/product/list";
    }

    /**
     * 更新页面回显
     *
     * @param productId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/echo")
    @RequiresPermissions("product-edit")
    public String echo(int productId, Model model) throws BSException {

        ProductInfo productInfo = productInfoService.adminFindById(productId);

        ProductDesc productDesc = productDescMapper.selectByPrimaryKey(productInfo.getProductId());

        model.addAttribute("productInfo", productInfo);

        model.addAttribute("productDesc", productDesc);

        return "admin/product/edit";
    }

    @RequestMapping("/update")
    @RequiresPermissions("product-edit")
    public String updateproduct(ProductInfo productInfo, String productDesc, String keywords, MultipartFile pictureFile, HttpServletRequest request, RedirectAttributes ra) throws Exception {
        uploadPicture(productInfo, pictureFile, request);
        ProductInfo originproduct = productInfoService.findById(productInfo.getProductId());
        productInfoService.updateproduct(productInfo, productDesc);

        //更新图片后，删除原来的图片
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + originproduct.getImageUrl());
        uploadPic.delete();
        //重定向到农产品籍列表
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/product/list";
    }

    @RequestMapping("/deletion/{productId}")
    @RequiresPermissions("product-delete")
    public String deletion(@PathVariable("productId") int productId, String keywords, RedirectAttributes ra, HttpServletRequest request) throws BSException {
        ProductInfo productInfo = productInfoService.findById(productId);
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + productInfo.getImageUrl());
        uploadPic.delete();
        productInfoService.deleteproduct(productId);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/product/list";
    }

    @RequestMapping("/shelf")
    @RequiresPermissions("product-shelf")
    public String productOffShelf(int productId, int isShelf, String keywords, RedirectAttributes ra) {

        productInfoService.changeShelfStatus(productId, isShelf);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/product/list";
    }

    private void uploadPicture(ProductInfo productInfo, MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        if (pictureFile != null) {
            if (!StringUtils.isEmpty(pictureFile.getOriginalFilename())) {
                String realPath = request.getServletContext().getRealPath("/" + urlPrefix);
                //原始文件名称
                String pictureFileName = pictureFile.getOriginalFilename();
                //新文件名称
                String newFileName = IDUtils.genShortUUID() + pictureFileName.substring(pictureFileName.lastIndexOf("."));

                //上传图片
                File uploadPic = new File(realPath + File.separator + newFileName);

                //向磁盘写文件
                pictureFile.transferTo(uploadPic);
                productInfo.setImageUrl(urlPrefix + File.separator + newFileName);
            }
        }
    }

}
