//日期全局方法，easyui识别
$.fn.datebox.defaults.parser = function(inputStr){
	if (!isNaN(Date.parse(inputStr))){
		return new Date(Date.parse(inputStr));
	}else{
		return new Date();
	}
}
/*定义一个全局变量*/
/*将form表单内的元素序列化为对象，扩展Jquery的一个方法*/
var sy = $.extend({}, sy);
sy.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

// ---------------表单验证---------------------
$.extend($.fn.validatebox.defaults.rules, {
    //固定长度数字
	fixLength: {
        validator: function(value, param){
        	 return isNumber(value) && value.length == param[0];
        },
        message: '请输入{0}位长度的数字.'
    },
	compareDate:{ //每三个参数一组校验，如compareDate['LTE','#ISSUING_END_DATE','发行开始日期小于等于发行结束日','LT','#START_INTEREST_DATE','发行开始日期小于起息日']
        validator:function(value,param){
            try{
            	for(var i=0;i<param.length;i=i+3){//alert(value+','+ $(param[i+1]).datebox('getValue'));
            		$.fn.validatebox.defaults.rules.compareDate.message = param[i+2];
            		if($(param[i+1]).datebox('getValue')=='')
            			return false;
                	if(param[i]=='LTE' && value > $(param[i+1]).datebox('getValue')){                    	
                    	return false;
                    }else if(param[i]=='LT'&&value >= $(param[i+1]).datebox('getValue')){
                    	return false;
                    }else if(param[i]=='GTE'&&value < $(param[i+1]).datebox('getValue')){
                    	return false;
                    }else if(param[i]=='GT'&&value <= $(param[i+1]).datebox('getValue')){
                    	return false;
                    }           	
                }
                return  true;
            }catch(e){
            	return false;
            }        	
        }, 
        message:'日期校验不对！' 
    },
    compareNumber:{ //每三个参数一组校验，如compareDate['LTE','#ISSUING_END_DATE','发行开始日期小于等于发行结束日','LT','#START_INTEREST_DATE','发行开始日期小于起息日']
        validator:function(value,param){
            try{            	
            	for(var i=0;i<param.length;i=i+3){//alert(value+','+ $(param[i+1]).datebox('getValue'));
            		$.fn.validatebox.defaults.rules.compareNumber.message = param[i+2];
            		if($(param[i+1]).numberbox('getValue')=='')
                		return false;
            		if(param[i]=='LTE' && value > $(param[i+1]).numberbox('getValue')){                    	
                    	return false;
                    }else if(param[i]=='LT'&&value >= $(param[i+1]).numberbox('getValue')){
                    	return false;
                    }else if(param[i]=='GTE'&&value < $(param[i+1]).numberbox('getValue')){
                    	return false;
                    }else if(param[i]=='GT'&&value <= $(param[i+1]).numberbox('getValue')){
                    	return false;
                    }            	
                }
                return  true;
            }catch(e){
            	return false;
            }        	
        }, 
        message:'数字校验不对！' 
    },
    selectRequired: {
        validator: function (value, param) {
        	return value!='请选择';
        },
        message:'该项为必选项'
    }    
});
//扩展form获取验证未通过项的方法
$.extend($.fn.form.methods, {
	getUnValidate:function(jq) {			
		if ($.fn.validatebox) {
            var field = $(jq[0]);
            field.find(".validatebox-text:not(:disabled)").validatebox("validate");
            var selField = field.find(".validatebox-invalid");
            selField.filter(":not(:disabled):first").focus();
            var label=$(selField[0]).parent().parent().prev().html().replace("<span style=\"color:red\">* </span>","");
            var message=$.data(selField[0],"validatebox").message;//$.data(this, "validatebox");
            if(message.indexOf(label)==-1)
            	$.messager.alert("提示",label+message);
            else
            	$.messager.alert("提示",message);
        }
	}
});
// 1.去字符串前后空格
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
// 判断合法邮编
function isZip(zip) {
	if (zip.value.length > 0) {
		if (isNaN(zip.value) || zip.value.length != 6) {
			alert("邮编格式不正确");
			zip.focus();
			return false;
		}
	}
	return true;
}
// 判断是否合法email格式
function isEmail(email) {
	if (email.value.length > 0) {
		var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if (!emailReg.test(email.value)) {
			alert("邮箱格式不正确");
			email.focus();
			return false;
		}
	}
	return true;
}
// 电话是否合法
function isTelephone(telephone) {
	if (telephone.value.length > 0) {
		var telephoneReg = /^([0-9]|[\-])+$/g;
		if (!telephoneReg.test(telephone.value)) {
			alert("电话格式不正确");
			telephone.focus();
			return false;
		}

	}
	return true;
}

// 判断时间是否合法,格式xxxx-xx-xx,yyyy-mm-dd
function formatTime(str) {
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null)
		return false;
	var d = new Date(r[1], r[3] - 1, r[4]);
	return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d
			.getDate() == r[4]);
}

// 只能输入金额
function moneyValidate(value) {
	var moneyReg = /^\d{1,9}(\.\d{0,6})?$/;
	if (!moneyReg.test(value)) {
		return false;
	}
	return true;
}

// 保留2位小数
function formatFloat(x) {
	var f_x = parseFloat(x);
	if (isNaN(f_x)) {
		x = 0;
	}
	var f_x = Math.round(x * 100) / 100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2) {
		s_x += '0';
	}
	return s_x;
}

// 控制输入框只能输入合法的数字,适用于数字运算
function numberText2(obj) {
	if (navigator.userAgent.indexOf('MSIE') >= 0) {
		var pos = getCursorPos(obj);// 保存原始光标位置
	}
	// 先把非数字的都替换掉，除了数字和"."还有"-"
	obj.value = obj.value.replace(/[^\d.\d-]/g, "");
	// 当第一个字符为"."时,自动换成"-"
	obj.value = obj.value.replace(/^\./g, "-");
	// 把"-."转换成"-"
	obj.value = obj.value.replace(/^\-\./g, "-");
	// 保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
	// 保证只有出现一个.而没有多个"-"
	obj.value = obj.value.replace(/\-{2,}/g, "-");
	obj.value = obj.value.replace("-", "$#$").replace(/\-/g, "").replace("$#$",
			"-");
	if (obj.value == '') {
		obj.value = '0';
	}
	if (obj.value.indexOf('.') != -1
			&& (obj.value.length - obj.value.lastIndexOf('.') > 3)) {
		obj.value = obj.value.substring(0, obj.value.length - 1);// 只能输入两位小数
	}
	if (navigator.userAgent.indexOf('MSIE') >= 0) {
		setCursorPos(obj, pos);// 设置光标
	}
	if (obj.value == "0")
		obj.select();
}

// 判断字符串s1是否以S2结束
function endWith(s1, s2) {
	if (s1.length < s2.length)
		return false;
	if (s1 == s2)
		return true;
	if (s1.substring(s1.length - s2.length) == s2)
		return true;
	return false;
}

// 判断字符串s1是否以S2开始
function startWith(s1, s2) {
	if (s1.length < s2.length)
		return false;
	if (s1 == s2)
		return true;
	if (s1.substring(0, s2.length) == s2)
		return true;
	return false;
}

// 创建XMLHttpRequest
function createXhr() {
	if (window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else {
		throw new Error("Does not ajax programming");
	}
}


/**
 * 验证由26个英文字母组成的字符串
 * @param value
 * @returns {Boolean}
 */
function validateEnglishLetters(value){
	if(value.length<=0){
		return false;
	}
	var englishLettersReg = /^[A-Za-z]+$/;
	if (!englishLettersReg.test(value)) {
		return false;
	}
	return true;
}


function cleanForm(formId){
	if(formId){
		$('#'+formId+' .easyui-textbox').textbox('setValue',"");
		$('#'+formId+' .easyui-combobox').combobox('setValue',"");
		$('#'+formId+' .easyui-datebox').datebox('setValue',"");
	}else{
		$('.easyui-textbox').textbox('setValue',"");
		$('.easyui-combobox').combobox('setValue',"");
	}
	
}



/**
 * 验证数量  非零的正整数
 * @param value
 * @returns {Boolean}
 */
function  validateAmount(value){
	var pReg = /^\+?[1-9][0-9]*$/;
	if(value.length>0){
		if (!pReg.test(value)) {
			return false;
		}
	}
	return true;
}

/**
 * 验证金额两位小数的正实数
 * @param value
 * @returns {Boolean}
 */
function validateMoney(value){
	if(value.length<=0){
		return false;
	}
	var pReg = /^[0-9]+(.[0-9]{2})?$/;
	if(value.length>0){
		if (!pReg.test(value)) {
			return false;
		}
	}
	return true;
}


// 判断合法邮编
function validateZip(zip) {
	if(zip.length<=0){
		return false;
	}
	if (zip.length > 0) {
		if (isNaN(zip) || zip.length != 6) {
			return false;
		}
	}
	return true;
}
// 判断合法手机号码
function validateCellphone(cellphone) {
	if(cellphone.length<=0){
		return false;
	}
	if (cellphone.length > 0) {
		if (isNaN(cellphone) || cellphone.length != 11) {
			return false;
		}
	}
	return true;
}

// 判断传入的数字是否正常
function validateNumber(value,num) {
	if(value.length<=0){
		return false;
	}
	if (value.length > 0) {
		if (isNaN(value) || value.length != num) {
			return false;
		}
	}
	return true;
}
// 判断是否合法email格式
function validateEmail(email) {
	if(email.length<=0){
		return false;
	}
	if (email.length > 0) {
		var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if (!emailReg.test(email)) {
			return false;
		}
	}
	return true;
}


/**
 * 把编辑添加页面变成查看页
 * @param {} formid
 * @param {} spanid  反向不需要隐藏的span name
 */
function changeToviewPage(formid,spanname){
	var myform='#'+formid;
		 $(myform).find('input').attr("style","" +
		 		"border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;width:100%");
		 $(myform).find("span[name!='"+spanname+"']").hide();
}

function checkIdCard(idCard,currentDate){
	if(!currentDate instanceof Date)
		return false;
	idCard = $.trim(idCard);
    var checker={
        //Wi 加权因子 Xi 余数0~10对应的校验码 Pi省份代码  
        Wi:[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2],
        Xi:[1,0,"X",9,8,7,6,5,4,3,2],
        Pi:[11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91],
   
        //检验18位身份证号码出生日期是否有效
        //parseFloat过滤前导零，年份必需大于等于1900且小于等于当前年份，用Date()对象判断日期是否有效。  
        brithday18:function(idCard){
            var year=parseFloat(idCard.substr(6,4));
            var month=parseFloat(idCard.substr(10,2));
            var day=parseFloat(idCard.substr(12,2));
            var checkDay=new Date(year,month-1,day);
            if (1900<=year && checkDay<=currentDate && month==(checkDay.getMonth()+1) && day==checkDay.getDate()) {
                return true;  
            };  
        },
   
        //检验15位身份证号码出生日期是否有效  
        brithday15:function(idCard){
            var year=parseFloat(idCard.substr(6,2));
            var month=parseFloat(idCard.substr(8,2));
            var day=parseFloat(idCard.substr(10,2));
            var checkDay=new Date(year,month-1,day);
            if (month==(checkDay.getMonth()+1) && day==checkDay.getDate()) {
                return true;  
            }
        },  
   
        //检验校验码是否有效  
        validate:function(idCard){
        	/*
            var aIdCard=idCard.split("");
            var sum=0;
            for (var i = 0; i < checker.Wi.length; i++) {
                sum+=checker.Wi[i]*aIdCard[i]; //线性加权求和  
            }
            var index=sum%11;//求模，可能为0~10,可求对应的校验码是否于身份证的校验码匹配  
            if (checker.Xi[index]==aIdCard[17].toUpperCase()) {
                return true;  
            }
            */
        	return true;
        },
   
        //检验输入的省份编码是否有效  
        province:function(idCard){
            var p2=idCard.substr(0,2);
            for (var i = 0; i < checker.Pi.length; i++) {
                if(checker.Pi[i]==p2){
                    return true;
                }
            }
        }
    };
    
    //开始验证
    if (!/^((\d{14}[\d|x|X]{1})|(\d{17}[\d|x|X]{1}))$/.test(idCard)) {//判断是否全为18或15位数字，最后一位可以是大小写字母X  
    	return false;
    } else if (idCard.length==18) {
		if (checker.province(idCard)&&checker.brithday18(idCard)&&checker.validate(idCard))
			return true;
    } else if (idCard.length==15) {
        if (checker.province(idCard)&&checker.brithday15(idCard))
			return true;
    }
    return false;
}

/**
 * 验证是否为数字
 * @param oNum
 * @returns {Boolean}
 */
function isNumber(oNum) { 
	if(!oNum) return false; 
	var strP=/^\d+(\.\d+)?$/; 
	if(!strP.test(oNum)) return false; 
	try{ 
		if(parseFloat(oNum)!=oNum) return false; 
	}catch(ex) { 
		return false; 
	} 
	return true; 
}
