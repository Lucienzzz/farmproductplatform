package org.zzq.farmproductplatform.model.service;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.model.entity.custom.Cart;
import org.zzq.farmproductplatform.model.entity.ProductInfo;

import javax.servlet.http.HttpServletRequest;

public interface ICartService {

    BSResult addToCart(ProductInfo productInfo, Cart cart, int buyNum);

    BSResult clearCart(HttpServletRequest request,String sessionName);

    BSResult deleteCartItem(int productId, HttpServletRequest request);

    BSResult updateBuyNum(int productId, int newNum, HttpServletRequest request);

    BSResult checkedOrNot(Cart cart,int productId);

}
