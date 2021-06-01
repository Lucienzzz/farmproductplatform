package org.zzq.farmproductplatform.web.controller;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.entity.Orders;
import org.zzq.farmproductplatform.model.service.IOrderDetailService;
import org.zzq.farmproductplatform.model.service.IOrderService;
import org.zzq.farmproductplatform.pay.Alipay;
import org.zzq.farmproductplatform.pay.PayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PayController {

    @Autowired
    private Alipay alipay;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @RequestMapping("/{orderId}")
    public String paymentPage(@PathVariable("orderId") String orderId, HttpServletResponse response, Model model){


        BSResult bsResult = orderService.findOrderById(orderId);
        Orders order = (Orders)bsResult.getData();

        List<ProductInfo> books = orderDetailService.findBooksByOrderId(order.getOrderId());

        PayContext payContext = new PayContext();
        payContext.setResponse(response);
        payContext.setOrders(order);
        payContext.setProductInfos(books);

        try {
            alipay.pay(payContext);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception", "支付出错了!");
            return "exception";
        }
        return "pay_success";
    }

    @RequestMapping("/return")
    public String returnUrl(String out_trade_no,String total_amount,String body,
                            Model model){

        model.addAttribute("body", body);
        model.addAttribute("total_amount", total_amount);


        BSResult bsResult = orderService.findOrderById(out_trade_no);
        Orders order = (Orders)bsResult.getData();

        List<ProductInfo> books = orderDetailService.findBooksByOrderId(order.getOrderId());

        PayContext payContext = new PayContext();
        payContext.setOrders(order);
        payContext.setProductInfos(books);

        orderService.updateOrderAfterPay(payContext);


        return "pay_success";
    }

    @RequestMapping("/notify")
    public void notifyUrl(){

        System.out.println("notify--------------------------");



    }

}
