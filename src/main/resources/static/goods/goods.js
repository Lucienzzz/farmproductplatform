$(document).ready(function(){
    var resurl=$("#goods").attr("url");
    console.log(resurl);
    var css = $("<link rel='stylesheet' href='"+resurl+"css.css"+"'>");
    var goods = $("<goods>");
    $("#goods").after(goods);
    goods.hide();
    $.ajax({
        url:resurl+"index.html",
        type:"get",
        success:function (data) {
            goods.append(css);
            goods.append(data);
            goods.ready(function () {
                goods.fadeIn(1000);
            })


        }
    });



});