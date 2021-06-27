<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <link rel="stylesheet" href="css/product_info.css"/>


    <title>农产品详情</title>
    <script type="application/javascript">
        $(function () {

            $(".product_message li").click(function () {
                $(".product_message li").removeClass("active");
                $(this).addClass("active");
                $(".nav_content").hide();
                var name = $(this).attr("id");
                $("#" + name + "_content").show();
            });

            $("#num_add").click(function () {
                var num = parseInt($("#buy_num").val());
                if (num < 10) {
                    $("#buy_num").val(num + 1);
                }
            });

            $("#num_sub").click(function () {
                var num = parseInt($("#buy_num").val());
                if (num > 1) {
                    $("#buy_num").val(num - 1);
                }
            });
        });

        function buyNow(productId) {
            location.href =  "<%=basePath%>" + "order/info?productId=" + productId + "&buyNum=" + $("#buy_num").val();
        }

        function addCart(productId) {
            location.href = "<%=basePath%>" + "cart/addition?productId=" + productId + "&buyNum=" + $("#buy_num").val();
        }
    </script>
</head>

<body>

<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row" style="border-bottom:1px dashed #CCCCCC;margin-bottom:15px ;">
        <a href="#">
            <img src="img/1.jpg"/>
        </a>
    </div>
    <div class="row" id="breadcrumb" style="margin-bottom:40px" ;>
        <a href="index" target="_blank">
            <b>农产品</b>
        </a>
        <span>&gt;</span>
        <a href="product/list?cateId=${productInfo.productCategoryId}" target="_blank">${productInfo.categoryName}</a>
        <span>&gt;</span>
        <b>${productInfo.name}</b>
    </div>
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" style="height: 400px;">
            <div>
                <a href="product/info/${productInfo.productId}">
                    <img src="${productInfo.imageUrl}" width="290px" height="290px"/>
                </a>
            </div>
        </div>
        <div class="col-lg-9 col-md-6 col-sm-12 col-xs-12">
            <div class="name_info">
                <h1 style="font-weight: bold;">${productInfo.name}</h1>
                <h2>
                    <span>${productInfo.outline}</span>
                </h2>
            </div>
            <div class="product_info_base">
            </div>
            <div class="product_price">
                <p class="what_price">特价</p>
                <p class="price_info">
                    <span class="rob" style="font-size: 30px;">￥${productInfo.price}</span>
                    <span style="color: red;">(${productInfo.discount}折扣)</span>
                    <span>定价:</span>
                    <span class="oprice">￥${productInfo.marketPrice}</span>
                </p>
            </div>

            <div class="buy_box">
                <div class="num buy_div">
                    <input type="text" class="text" id="buy_num" disabled="disabled" value="1" width="30px"
                           height="30px"/>
                    <a href="javascript:void(0);" class="num_add" id="num_add"></a>
                    <a href="javascript:void(0);" class="num_sub" id="num_sub"></a>
                </div>
                <div class="buy_div">
                    <div class="cart">
                        <a href="javascript:void(0);" onclick="addCart(${productInfo.productId})" class="add_cart">
                            <i class="cart_icon"></i> 加入购物车
                        </a>
                    </div>
                </div>
                <div class="buy_div buy_now_div">
                    <a href="javascript:void(0);" onclick="buyNow(${productInfo.productId})" class="buy_now">立即购买</a>
                </div>
                <div class="clear"></div>
                <div class="buy_tip">每账户最多可购买<b>10</b>件</div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
            <div class="product_left">
                <h3 style="">浏览此商品的顾客也同时浏览</h3>
                <ul class="product_left_ul">

                    <c:forEach items="${recommendProductList}" var="recommendproduct">
                        <c:if test="${recommendproduct.productId != productInfo.productId}">
                            <li class="product_li">
                                <p class="pic">
                                    <a href="product/info/${recommendproduct.productId}" class="img" target="_blank">
                                        <img src="${recommendproduct.imageUrl}"/>
                                    </a>
                                <p class="price">
                                    <span class="rob">￥${recommendproduct.price}</span>
                                    <span class="oprice">￥${recommendproduct.marketPrice}</span>
                                </p>
                                <p class="name">
                                    <a href="product/info/${recommendproduct.productId}">${recommendproduct.name}</a>
                                </p>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">

            <ul class="nav nav-tabs nav-justified product_message">
                <li class="active" id="product_detail">
                    <a href="javascript:void(0)">农产品详情</a>
                </li>
                <li id="product_comment">
                    <a href="javascript:void(0)">农产品评论</a>
                </li>
            </ul>
            <div class="content">
                <div id="product_detail_content" class="nav_content">
                    <div id="detail" class="section">


                        <div id="productDetail" class="section">
                            <div class="title">
                                <span class="title_span">农产品详情</span>
                            </div>
                            <p>
                                ${productInfo.detail}
                            </p>
                        </div>


                        <div id="feature" class="section">
                            <div class="title">
                                <span class="title_span">产品特色</span>
                            </div>
                            <p>
                                ${empty productDesc.productDesc?"xxxxxx":productDesc.productDesc}
                            </p>
                        </div>
                        <div class="abstract" class="section">
                        </div>
                        <div class="content" class="section">
                        </div>
                    </div>
                </div>
                <div id="product_comment_content" style="display: none;" class="nav_content">
                    商品评论
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>