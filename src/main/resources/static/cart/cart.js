$(document).ready(function () {
    var cart ={ info:"这是购物车组件"};
    var resurl = $("#cart").attr("url")+"index.html";

    var mcart = $("<div>");
    cart.doc =mcart;

   $("#cart").after(mcart);
   mcart.hide();
   $.ajax({
       url:resurl,
       type:"get",
       success:function (data) {
           mcart.append($(data));
           mcart.fadeIn(1000);
           cart.run();
       }
   });
    cart.run=function () {
        console.log(this.info);
        $(".good-add_cart .add_cart").click(function () {
            $(".cart").animate({opacity:1},function () {
                $(".cart").animate({opacity:"0.3"})
            });
            var num = parseInt($(".cart label").text());
            var addnum=parseInt($(".good-add_cart input[type=number]").val());
            num+=addnum;
            $(".cart label").text(num);

        });

        $(this.doc).click(function () {
            location.href=$("#cart").attr("cart");
        })
    };



});