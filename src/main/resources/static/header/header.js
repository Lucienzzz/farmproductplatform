
$(document).ready(function(){


    var resurl=$("#header").attr("url");
    console.log(resurl);
    //var css = $("<link rel='stylesheet' href='"+resurl+"css.css"+"'>");
    var header = $("<header>");
    $("#header").after(header);
    header.hide();
    $.ajax({
        url:resurl+"index.html",
        type:"get",
        success:function (data) {
            // console.log(data);
            header.append(data);
            header.ready(function () {
                header.fadeIn(1000);
            })

            if($.cookie("userUsername")){
                $('#userName').text('欢迎你，' + $.cookie('userUsername'));
                $('#userName').attr("href", "/user");
                $('#logout').css("display", "block");
                $('#register').css("display", "none");
            }


        }
    });



});
