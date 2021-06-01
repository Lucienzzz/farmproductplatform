package org.zzq.farmproductplatform.model.service;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.model.entity.custom.Cart;
import org.zzq.farmproductplatform.model.entity.User;
import org.zzq.farmproductplatform.model.entity.custom.OrderCustom;
import org.zzq.farmproductplatform.pay.PayContext;

import java.util.List;

public interface IOrderService {

    List<OrderCustom> findOrdersByUserId(int userId);

    List<OrderCustom> findOrdersByStoreId(int storeId);

    BSResult createOrder(Cart cart, User user, String express, int payMethod);

    BSResult findOrderById(String orderId);

    BSResult deleteOrder(String orderId);

    void updateOrderAfterPay(PayContext payContext);

    BSResult postOrder(String orderId);

    OrderCustom findOrderCustomById(String orderId);

    BSResult updateOrder(OrderCustom orderCustom);

    BSResult confirmReceiving(String orderId);
}
