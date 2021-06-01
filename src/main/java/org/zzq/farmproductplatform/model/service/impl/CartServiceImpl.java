package org.zzq.farmproductplatform.model.service.impl;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.utils.BSResultUtil;
import org.zzq.farmproductplatform.model.entity.custom.Cart;
import org.zzq.farmproductplatform.model.entity.custom.CartItem;
import org.zzq.farmproductplatform.model.entity.ProductInfo;
import org.zzq.farmproductplatform.model.service.ICartService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class CartServiceImpl implements ICartService {

    @Override
    public BSResult addToCart(ProductInfo productInfo, Cart cart, int buyNum) {

        //购物车为空，新建一个
        if (cart == null) {
            cart = new Cart();
        }
        Map<Integer, CartItem> cartItems = cart.getCartItems();
        double total = 0;
        if (cartItems.containsKey(productInfo.getProductId())) {
            CartItem cartItem = cartItems.get(productInfo.getProductId());
            cartItem.setBuyNum(cartItem.getBuyNum() + buyNum);
            cartItem.setSubTotal(cartItem.getBuyNum() * productInfo.getPrice().doubleValue());
            cartItem.setProductInfo(productInfo);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setBuyNum(buyNum);
            cartItem.setProductInfo(productInfo);
            cartItem.setSubTotal(productInfo.getPrice().doubleValue() * buyNum);
            cartItems.put(productInfo.getProductId(), cartItem);
        }
        for (CartItem cartItem : cartItems.values()) {
            total += cartItem.getSubTotal();
        }
        cart.setTotal(total);

        return BSResultUtil.success(cart);

    }

    @Override
    public BSResult clearCart(HttpServletRequest request, String sessionName) {
        request.getSession().removeAttribute(sessionName);
        return BSResultUtil.success();
    }

    @Override
    public BSResult deleteCartItem(int productId, HttpServletRequest request) {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Map<Integer, CartItem> cartItems = cart.getCartItems();
        if (cartItems.containsKey(productId)) {
            CartItem cartItem = cartItems.get(productId);
            cart.setTotal(cart.getTotal() - cartItem.getSubTotal());
            cartItems.remove(productId);
        }
        request.getSession().setAttribute("cart", cart);
        return BSResultUtil.success();
    }

    @Override
    public BSResult updateBuyNum(int productId, int newNum, HttpServletRequest request) {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Map<Integer, CartItem> cartItems = cart.getCartItems();
        if (cartItems.containsKey(productId)) {
            //取出订单项所对应的农产品籍，根据新的购买数量重新计算小计
            CartItem cartItem = cartItems.get(productId);
            //不知道是加还是减去商品的数量，所以先减去原来的购物项小计，最后再加新的小计
            cart.setTotal(cart.getTotal() - cartItem.getSubTotal());
            ProductInfo productInfo = cartItem.getProductInfo();
            cartItem.setSubTotal(
                    productInfo.getPrice().doubleValue() * newNum);

            cartItem.setBuyNum(newNum);
            cart.setTotal(cart.getTotal() + cartItem.getSubTotal());
        }

        request.getSession().setAttribute("cart", cart);
        return BSResultUtil.success();
    }

    @Override
    public BSResult checkedOrNot(Cart cart, int productId) {
        Map<Integer, CartItem> cartItems = cart.getCartItems();

        if (cartItems.containsKey(productId)) {
            CartItem cartItem = cartItems.get(productId);
            if (cartItem.isChecked()) {
                //如果之前是true，那就设为false
                cartItem.setChecked(false);
                cart.setTotal(cart.getTotal() - cartItem.getSubTotal());
                cartItem.setSubTotal(0.00);
            } else {
                //如果之前是false，那就设为true
                cartItem.setChecked(true);
                cartItem.setSubTotal(cartItem.getBuyNum() * cartItem.getProductInfo().getPrice().doubleValue());
                cart.setTotal(cart.getTotal() + cartItem.getSubTotal());

            }
            return BSResultUtil.success();
        } else
            return BSResultUtil.build(400, "购物车没有这本农产品籍!");
    }

}
