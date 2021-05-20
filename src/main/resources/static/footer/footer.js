$(document).ready(function(){


    var resurl=$("#footer").attr("url");
    console.log(resurl);
    var css = $("<link rel='stylesheet' href='"+resurl+"css.css"+"'>");
    var footer = $("<footer>");
    $("#footer").after(footer);
    footer.hide();
$.ajax({
    url:resurl+"index.html",
    type:"get",
    success:function (data) {
        // console.log(data);
      footer.append(css);
       footer.append(data);
        footer.ready(function () {
           footer.fadeIn(1000);
        })


    }
});



});