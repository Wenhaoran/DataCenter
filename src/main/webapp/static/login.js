// 判断时候在Iframe框架内,在则刷新父页面
if (self != top) {
    parent.location.reload(true);
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    } else {
        window.stop();
    }
}

function reloadCheckCode(){
	$("#yzmPic").attr("src",basePath +"/CheckCode?r=" + new Date().getTime());
}

$(function () {
    // 得到焦点
    $("#password").focus(function () {
        $("#left_hand").animate({
            left: "150",
            top: " -38"
        }, {
            step: function () {
                if (parseInt($("#left_hand").css("left")) > 140) {
                    $("#left_hand").attr("class", "left_hand");
                }
            }
        }, 2000);
        $("#right_hand").animate({
            right: "-64",
            top: "-38px"
        }, {
            step: function () {
                if (parseInt($("#right_hand").css("right")) > -70) {
                    $("#right_hand").attr("class", "right_hand");
                }
            }
        }, 2000);
    });
    // 失去焦点
    $("#password").blur(function () {
        $("#left_hand").attr("class", "initial_left_hand");
        $("#left_hand").attr("style", "left:100px;top:-12px;");
        $("#right_hand").attr("class", "initial_right_hand");
        $("#right_hand").attr("style", "right:-112px;top:-12px");
    });
    //打开页面 光标在用户名input
    $("#username").focus();
    
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            
        	if($("#username").val()==""){
        		 $("#username").focus();
        		 return;
        	}
        	if($("#password").val()==""){
        		$("#password").focus();
        		return;
        	}
//        	if($("#validateCode").val()==""){
//          		 $("#validateCode").focus();
//          		return;
//           	}
        	//要做的事情
        	submitForm();
       }
    }
    
    // 登录
    $('#loginform').form({
    	url: basePath + '/pages/common/login',
        onSubmit : function() {
            progressLoad();
            var isValid = $(this).form('validate');
            if(!isValid){
                progressClose();
            }
            var username = $("#username").val();
            if(username==""){            	 
            	easyui_error("请输入用户名！");            	 
            	 isValid = false;
            	 progressClose();
            	 $("#username").focus();
            	 return isValid;
            }
            var password = $("#password").val();
            if(password==""){            	 
            	 easyui_error("请输入密码！");            	 
            	 isValid = false;
            	 progressClose();
            	 $("#password").focus();
            	 return isValid;
            }
//            var validateCode = $("#validateCode").val();
//            if(validateCode==""){
//           	 easyui_error("请输入验证码！");
//               $("#validateCode").focus();
//            	 isValid = false;
//            	 progressClose();
//            	 return isValid;
//            }
            return isValid;
        },
        success:function(result){
            result = $.parseJSON(result);
            if (result.isSuccess == 'true') {
            	progressClose();
                window.location.href = basePath + '/pages/index';
            }else{              
            	easyui_error(result.msg);
                reloadCheckCode();
                progressClose();
                return false;
            }
        }
    });
});
function submitForm(){
    $('#loginform').submit();
}
function clearForm(){
    $('#loginform').form('clear');
}
//回车登录
function enterlogin(){
    $('#loginform').submit();
}
