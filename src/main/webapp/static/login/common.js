$(document).ready(function () {
    $(".navTitle").children("div").hide()

    $(".navTitle").hover(function () {
            $(this).addClass("tran");
            $(this).children("div").show();
        },
        function () {
            $(this).removeClass("tran");
            $(this).children("div").hide();
        }
    );
    $(".me").children("div").hide()

    $(".me").hover(function () {
            $(this).children("div").show();
        },
        function () {
            $(this).children("div").hide();
        }
    );

    $("tr:even").css('background', '#f6f6f6')
    $("tr:odd").css('background', '#fff')
    $("tr:eq(0)").css('background', '#4b4c51');

    $(".page a").click(function () {
        $(this).addClass("blue").siblings().removeClass("blue")
    });
    //点击新增弹出内容
    $("#add").click(function () {
        $(".mask").show();
        $(".maskBg").show();
        $(".newAdd").show().siblings().hide();
    })

    //点击新增里的确定弹出添加成功
    $(".newAdd .sure").click(function () {
            $(".newAdd").hide();
            $(".mask").show();
            $(".maskBg").show();
            $(".addSuccess").show();
        })
        //点击新增里的确定弹出添加失败
        //    $(".newAdd .sure").click(function () {
        //        $(".newAdd").hide();
        //        $(".mask").show();
        //        $(".maskBg").show();
        //        $(".addFail").show();
        //    })

    //点击退出弹出退出成功
    $("#quitLogin").click(function () {
        $(".mask").show();
        $(".maskBg").show();
        $(".quitLogin").show().siblings().hide();
    })

    //点击关闭
    $(".close").click(function () {
        $(".mask").hide();
    })
    
    //点击登录成功
     $(".go").click(function () {
        $(".mask").show();
        $(".maskBg").show();
        $(".loginSuccess").show().siblings().hide();
    });

    //点击用户名邮箱切换
    $(".nameBtn").click(function(){
        $(this).addClass("orange").siblings().removeClass("orange");
        $(".name").show().siblings().hide();
    })
    $(".emailBtn").click(function(){
        $(this).addClass("orange").siblings().removeClass("orange");
        $(".name2").show().siblings().hide();
    });


    $(".count1").click(function(){
        $(".countM").hide();
        $(".outer").addClass("outer");
        $(".infor").show();
        $(".apiTable").show();
        $(".page").show();      
    })
    $(".count2").click(function(){
        $(this).addClass("countBlue").prev().removeClass("countBlue");
        $(".outer").removeClass("outer");
        $(".infor").hide();
        $(".apiTable").hide();
        $(".page").hide();
        $(".countM").show();
    })



})