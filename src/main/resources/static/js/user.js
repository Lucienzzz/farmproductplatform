var userInfo = null;
window.onload = function () {
    var currentUrl = document.location.toString();
    if (!currentUrl.match('user')) {
        return;
    }
    var user_uuid = $.cookie('userUUID');
    var userUsername = $.cookie('userUsername');
    var userEmailAddress = $.cookie('userEmailAddress');
    $('#userHeadImage').attr('src', "./upload/item/user/head/" + user_uuid + ".jpg");
    $('#username').text("用户名：" + userUsername);
    $('#userEmailAddress').text("邮箱：" + userEmailAddress);
}
//获取用户信息
$(function () {
    var getUserInfoUrl = "/user/getUserInfo";
    $.ajax({
        url: getUserInfoUrl,
        type: 'POST',
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            console.log(data);
            if (data.status === 200) {
                userInfo = data.data;
                console.log(data.userInfo.userUsername)
                console.log(data.userInfo.userUuid)
                console.log(data.userInfo.userEmailAddress)
                $('#userName').text('欢迎你，' + data.userInfo.userUsername);
                $.cookie('userUUID', data.userInfo.userUuid);
                $.cookie('userUsername', data.userInfo.userUsername);
                $.cookie('userEmailAddress', data.userInfo.userEmailAddress);
                $('#userName').attr("href", "/user");
                $('#logout').css("display", "block");
                $('#register').css("display", "none");
                //店铺状态显示
                if (userInfo.shopStatus == 1) {
                    $('#shop').css('display', "none");
                    $('#managerShopInfo').css('display', "");
                    $('#managerProduct').css('display', "");
                    $('#managerOrder').css('display', "");
                }
            } else {
            }
        }
    });
});

//注销
function logout() {
    if (!confirm("你确定要注销吗？")) {
        return;
    }
    var logoutUrl = "/logout";
    $.ajax({
        url: logoutUrl,
        type: 'GET',
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            if (data.success) {
                alert("注销成功！");
                $.removeCookie('userUUID');
                $.removeCookie('userUsername');
                $.removeCookie('userEmailAddress');
                window.location = "./login";
            } else {
                alert("注销失败！");
            }
        }
    });
}

//获取用户收货地址
$(function () {
    var queryAddressUrl = "./user/queryAddress";
    $.ajax({
        url: queryAddressUrl,
        type: 'POST',
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            if (data.success) {
                var s = "";
                for (var i = 0; i < data.addresses.length; i++) {
                    s += "\n" +
                        "                        <tr>\n" +
                        "                            <td>" + data.addresses[i].userName + "</td>\n" +
                        "                            <td>" + data.addresses[i].userPhone + "</td>\n" +
                        "                            <td>" + data.addresses[i].userAddress + "</td>\n" +
                        "                            <td>" +
                        "<button class=\"btn bg-success\"><a href=\"./user/editMyAddress.html\">编辑</a></button>" +
                        " &nbsp;<button class=\"btn bg-success\"><a href=\"#\">删除</a></button></td></tr>";
                }
                $('.address').append(s);
            } else {
            }
        }
    });
});

//获取用户订单
$(function () {
    var queryOrderUrl = "./user/queryOrder";
    $.ajax({
        url: queryOrderUrl,
        type: 'POST',
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            if (data.success) {
                var s = "";
                for (var i = 0; i < data.orders.length; i++) {
                    if (data.orders[i].logistics == null) {
                        data.orders[i].logistics = '暂无物流信息';
                    }
                    s += "\n" +
                        "                    <tr>\n" +
                        "<td id='orderId'>" + data.orders[i].orderId + "</td>\n" +
                        "<td>" + data.orders[i].productName + "</td>\n" +
                        "<td>" + data.orders[i].price + "</td>\n" +
                        "<td>" + data.orders[i].logistics + "</td>\n";
                    if (data.orders[i].status == 0) {
                        s += "<td>未付款</td>\n";
                        s += "<td><button class=\"btn  btn-default \"><a href='./pay?body=" + data.orders[i].productName + "&out_trade_no=" + data.orders[i].orderId + "&subject=" + data.orders[i].productName + "&total_amount=" + data.orders[i].price + "'>付款</a></button></td>\n" +
                            "</tr>";
                    } else if (data.orders[i].status === 1) {
                        s += "<td>已付款</td>\n";
                        s += "<td><button class=\"btn  btn-default \"><a href='./refund?orderId="+data.orders[i].orderId+"&orderTradeNo="+data.orders[i].orderTradeNo+"'>退款</a></button></td>\n" +
                            "</tr>";
                    } else if (data.orders[i].status === 2) {
                        s += "<td>订单已关闭</td>\n";
                        s += "<td><button class=\"btn  btn-default \">无操作</button></td>\n" +
                            "</tr>";
                    } else if (data.orders[i].status === 3) {
                        s += "<td>已发货</td>\n";
                        s += "<td><button onclick='confirmOrder(\"" + data.orders[i].orderId + "\")' class=\"btn  btn-default \">确认收货</button></td>\n" +
                            "</tr>";
                    } else {
                        s += "<td>已收货</td>\n";
                        s += "<td><button class=\"btn  btn-default \">评价</button></td>\n" +
                            "</tr>";
                    }
                }
                $('#orders').append(s);
            } else {
            }
        }
    });
});

function confirmOrder(orderId) {
    var confirmOrderUrl = "./user/confirmOrder?orderId=" + orderId;
    $.ajax({
        url: confirmOrderUrl,
        type: 'POST',
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            if (data.success) {
                alert("确认收货成功！");
                window.location.reload();
            } else {
                alert(data.message);
            }
        }
    });
}
