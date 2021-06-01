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
@RequestMapping("/admin/book")
@RequiresPermissions("book-manage")
public class AdminBookController {

    @Autowired
    private IProductInfoService bookInfoService;

    @Autowired
    private ProductDescMapper productDescMapper;

    @Autowired
    private IStoreService storeService;

    @Value("${image.url.prefix}")
    private String urlPrefix;

    @RequestMapping("toAddition")
    @RequiresPermissions("book-add")
    public String toAddition() {
        return "admin/book/add";
    }

    @RequestMapping("/addition")
    @RequiresPermissions("book-add")
    public String addBook(ProductInfo productInfo, String bookDesc, MultipartFile pictureFile, HttpServletRequest request) throws Exception {

        uploadPicture(productInfo, pictureFile, request);
        bookInfoService.saveBook(productInfo, bookDesc);

        return "redirect:/admin/book/list";
    }

    @RequestMapping(value = "/list")
    @RequiresPermissions("book-query")
    public String bookList(@RequestParam(defaultValue = "", required = false) String keywords,
                           @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                           HttpSession session,
                           Model model) {
        keywords = keywords.trim();
        Store store = (Store) session.getAttribute("loginStore");

        if (store != null) {
            PageInfo<ProductInfo> books = bookInfoService.findBookListByCondition(keywords, 0, page, 10, store.getStoreId());
            model.addAttribute("bookPageInfo", books);
            model.addAttribute("keywords", keywords);
        } else {
            model.addAttribute("exception", "您请求的资源不存在");
            return "exception";
        }

        return "admin/book/list";
    }

    /**
     * 更新页面回显
     *
     * @param bookId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/echo")
    @RequiresPermissions("book-edit")
    public String echo(int bookId, Model model) throws BSException {

        ProductInfo productInfo = bookInfoService.adminFindById(bookId);

        ProductDesc productDesc = productDescMapper.selectByPrimaryKey(productInfo.getProductId());

        model.addAttribute("bookInfo", productInfo);

        model.addAttribute("bookDesc", productDesc);

        return "admin/book/edit";
    }

    @RequestMapping("/update")
    @RequiresPermissions("book-edit")
    public String updateBook(ProductInfo productInfo, String bookDesc, String keywords, MultipartFile pictureFile, HttpServletRequest request, RedirectAttributes ra) throws Exception {
        uploadPicture(productInfo, pictureFile, request);
        ProductInfo originBook = bookInfoService.findById(productInfo.getProductId());
        bookInfoService.updateBook(productInfo, bookDesc);

        //更新图片后，删除原来的图片
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + originBook.getImageUrl());
        uploadPic.delete();
        //重定向到书籍列表
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    @RequestMapping("/deletion/{bookId}")
    @RequiresPermissions("book-delete")
    public String deletion(@PathVariable("bookId") int bookId, String keywords, RedirectAttributes ra, HttpServletRequest request) throws BSException {
        ProductInfo productInfo = bookInfoService.findById(bookId);
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + productInfo.getImageUrl());
        uploadPic.delete();
        bookInfoService.deleteBook(bookId);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    @RequestMapping("/shelf")
    @RequiresPermissions("book-shelf")
    public String bookOffShelf(int bookId, int isShelf, String keywords, RedirectAttributes ra) {

        bookInfoService.changeShelfStatus(bookId, isShelf);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
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
