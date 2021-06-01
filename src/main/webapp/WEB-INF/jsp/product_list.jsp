<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
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
    <link rel="stylesheet" href="css/product_list.css"/>
    <title>product</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="container">
    <div class="search_tab">
        <ul>
            <li>全部商品</li>
        </ul>
    </div>
    <div class="crumbs">
        <div>
            <a href="#">全部</a>
            <span>&gt;</span>
            <span class="search_word">${keywords}</span>
        </div>
        <span class="total_search_product_count">
					共<em class="red">${productPageInfo.total}</em>农产品
				</span>
    </div>
    <div class="search_result">
        <div class="shoplist">
            <ul class="shoplist_ul">

                <c:forEach items="${productPageInfo.list}" var="productInfo">
                    <li>
                        <a href="product/info/${productInfo.productId}" target="_blank" title="${productInfo.outline}">
                            <img src="${productInfo.imageUrl}" alt="${productInfo.outline}" width="200px" height="200px"/>
                        </a>
                        <p class="name">
                            <a href="product/info/${productInfo.productId}" title="${productInfo.outline}" target="_blank">
                                    ${productInfo.outline}
                            </a>
                        </p>
                        <p class="price">
                            <span class="search_now_price">￥ ${productInfo.price}</span>
                            <span style="color: #C0C0C0;">定价：</span>
                            <span class="oprice">￥${productInfo.marketPrice}</span>
                            <span class="search_discount">&nbsp;(${productInfo.discount}折) </span>
                        </p>
                        <p class="search_product_author">
                        </p>
                        <p class="detail">
                                ${productInfo.detail}
                        </p>
                        <div class="shop_button">
                            <p class="bottom_p">
                                <a class="search_btn_cart" href="cart/addition?productId=${productInfo.productId}&buyNum=1">加入购物车</a>
                                <a class="search_btn_collect" href="javascript:void(0);">收藏</a>
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <ul class="pagination pagination-lg">

            <%--
                上一页
            --%>
            <c:if test="${productPageInfo.isFirstPage}">
                <li class="disabled"><a href="javascript:void(0);">前一页</a></li>
            </c:if>

            <c:if test="${!productPageInfo.isFirstPage}">
                <li>
                    <a href="product/list?keywords=${keywords}&cateId=${cateId}&page=${productPageInfo.prePage}">前一页</a>
                </li>
            </c:if>

            <%--<c:forEach
                    begin="${productPageInfo.pageNum}"
                    end="${productPageInfo.pageNum+5 < productPageInfo.pages ? productPageInfo.pageNum+5 : productPageInfo.pages }"
                    var="current">
                <li
                        class="${(current == productPageInfo.pageNum) ? 'active':''}">
                    <a href="product/list?keyword=${keywords}&cateId=${cateId}&page=${current}">
                        ${current}
                    </a>
                </li>
            </c:forEach>--%>
            <c:forEach
                    begin="${productPageInfo.pageNum < 6 ? 1 :productPageInfo.pageNum-5}"
                    end="${productPageInfo.pages<6?productPageInfo.pages:(productPageInfo.pageNum < 6 ? 6 :productPageInfo.pageNum) }"
                    var="current">
                <li
                        class="${(current == productPageInfo.pageNum) ? 'active':''}">
                    <a href="product/list?keywords=${keywords}&cateId=${cateId}&page=${current}">
                            ${current}
                    </a>
                </li>
            </c:forEach>
            <%--
                下一页
            --%>
            <c:if test="${productPageInfo.isLastPage}">
                <li class="disabled"><a href="javascript:void(0);">下一页</a></li>
            </c:if>

            <c:if test="${!productPageInfo.isLastPage}">
                <li><a href="product/list?keywords=${keywords}&cateId=${cateId}&page=${productPageInfo.nextPage}">下一页</a>
                </li>
            </c:if>

            <li class="disabled"><a href="javascript:void(0);">共${productPageInfo.pages}页</a></li>
        </ul>
    </div>
</div>
</body>
</html>
