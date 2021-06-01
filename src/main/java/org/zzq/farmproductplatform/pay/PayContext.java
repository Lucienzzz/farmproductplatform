package org.zzq.farmproductplatform.pay;

import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.entity.Orders;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PayContext {

    private Orders orders;

    private List<ProductInfo> productInfos;

    private HttpServletResponse response;

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
