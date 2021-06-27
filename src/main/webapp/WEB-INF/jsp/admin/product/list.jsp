<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
    <script src="js/base-loading.js"></script>

    <style type="text/css">
        td {
            max-width: 100px;
            overflow: hidden;
        }

        #searchproduct {
            padding-top: 50px;
        }
    </style>

    <script type="text/javascript">

        function editproduct(productId) {
            location.href = "<%=basePath%>admin/product/echo?productId=" + productId;
        }

        function changeproductShelf(productId, isShelf) {
            if (isShelf == 1) {
                if (confirm("下架农产品?")) {
                    location.href = "<%=basePath%>admin/product/shelf?productId=" + productId + "&isShelf=0&"+"keywords="+'${keywords}';
                }
            }
            if (isShelf == 0) {
                if (confirm("上架农产品?")) {
                    location.href = "<%=basePath%>admin/product/shelf?productId=" + productId + "&isShelf=1&"+"keywords="+'${keywords}';
                }
            }
        }

        function deleteproduct(productId) {
            if (confirm("删除这本农产品?")) {
                location.href = "<%=basePath%>admin/product/deletion/"+productId+"?keywords="+'${keywords}';
            }
        }

    </script>
</head>

<body>
<div class="container" style="border: 1px solid #CCCCCC;">
    <div id="searchproduct" style="height:100px;border-bottom: 1px solid #CCCCCC;">
        <form action="admin/product/list" class="form-inline" role="form" method="get">
            <div class="form-group">
                <input type="text" class="form-control" id="name" name="keywords" value="${keywords}" placeholder="农产品名">
            </div>

            <button type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-search"></span> 农产品检索
            </button>
        </form>
    </div>

    <table class="table table-hover">
        <caption>农产品列表</caption>
        <thead>
        <tr>
            <th>序号</th>
            <th>农产品名</th>
            <th>图片</th>
            <th>现价</th>
            <th>定价</th>
            <th>状态</th>
            <th>库存</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productPageInfo.list}" var="product" varStatus="vs">
            <tr>
                <td>${productPageInfo.startRow + vs.count-1}</td>
                <td>${product.name}</td>
                <td>
                    <img src="${product.imageUrl}" style="width: 50%">
                </td>
                <td class="red">￥${product.price}</td>
                <td class="red">￥${product.marketPrice}</td>
                <td class="red">${product.isShelf == 1 ?"上架中":"已下架"}</td>
                    <%--<td><fmt:formatDate value="${product.publishDate}" pattern="yyyy-MM-dd"/></td>--%>
                <td>${product.storeMount}</td>
                <td>
                    <button type="button" class="btn btn-xs btn-info" onclick="editproduct(${product.productId})">
                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                    </button>
                    <button type="button" class="btn btn-xs btn-warning"
                            onclick="changeproductShelf(${product.productId},${product.isShelf})">
                            ${product.isShelf == 1 ?"<span class='glyphicon glyphicon-arrow-down'></span> 下架":"<span class='glyphicon glyphicon-arrow-up'></span> 上架"}
                    </button>
                    <button type="button" class="btn btn-xs btn-danger" onclick="deleteproduct(${product.productId})">
                        <span class="glyphicon glyphicon-pencil"></span> 删除
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul class="pagination pagination-lg">

        <c:if test="${productPageInfo.isFirstPage}">
            <li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
        </c:if>

        <c:if test="${!productPageInfo.isFirstPage}">
            <li>
                <a href="admin/product/list?keywords=${keywords}&page=${productPageInfo.prePage}">&laquo;</a>
            </li>
        </c:if>
        <c:forEach
                begin="${productPageInfo.pageNum < 6 ? 1 :productPageInfo.pageNum-5}"
                end="${productPageInfo.pages<6?productPageInfo.pages:(productPageInfo.pageNum < 6 ? 6 :productPageInfo.pageNum) }"
                var="current">
            <li
                    class="${(current == productPageInfo.pageNum) ? 'active':''}">
                <a href="admin/product/list?keywords=${keywords}&page=${current}">
                        ${current}
                </a>
            </li>
        </c:forEach>

        <c:if test="${productPageInfo.isLastPage}">
            <li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
        </c:if>

        <c:if test="${!productPageInfo.isLastPage}">
            <li><a href="admin/product/list?keywords=${keywords}&page=${productPageInfo.nextPage}">&raquo;</a>
            </li>
        </c:if>

        <li>
            <a href="admin/product/list?keywords=${keywords}&page=${productPageInfo.pages}">末页</a>
        </li>

        <li><a href="javascript:void(0);">共${productPageInfo.pages}页</a></li>
        <li><a href="javascript:void(0);">共${productPageInfo.total}本农产品</a></li>
    </ul>
</div>

</body>
</html>
