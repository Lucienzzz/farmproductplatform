package org.zzq.farmproductplatform.web.controller.admin;

import org.zzq.farmproductplatform.common.pojo.Bar;
import org.zzq.farmproductplatform.common.pojo.Pie;
import org.zzq.farmproductplatform.model.entity.Store;
import org.zzq.farmproductplatform.model.service.IProductInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/report")
@RequiresPermissions("store-manage")
public class ReportController {

    @Autowired
    private IProductInfoService productInfoService;

    /**
     * 商店农产品籍访问量排行饼图
     * @return
     */
    @RequestMapping("/views/pie")
    public List<Pie> getproductViewsPieJson(HttpSession session){
        Store loginStore = (Store) session.getAttribute("loginStore");
        if(loginStore == null){
            return new ArrayList<>();
        }
        return productInfoService.getproductViewsPiesByStoreId(loginStore.getStoreId());
    }

    @RequestMapping("/sales/bar")
    public Bar getproductSalesBarJson(HttpSession session){
        Store loginStore = (Store) session.getAttribute("loginStore");
        if(loginStore == null){
            return null;
        }
        return productInfoService.getproductSalesBarJson(loginStore.getStoreId());
    }


}
