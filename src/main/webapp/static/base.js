
function openContent(title,href){
	var tt = $('#tabs');
    if (tt.tabs('exists', title)) {
        tt.tabs('select', title);
        var currTab = tt.tabs('getTab', title);
        tt.tabs('update', {tab: currTab, options: {content: content, closable: true}});         
    } else {
        if (href) {
//    		var webpath = basePath + href;
            var content = '<iframe frameborder="0" src="'+href+ '" style="border:0;width:100%;height:99.5%;"></iframe>';
        } else {
            var content = '未实现';
        }
        tt.tabs('add', {
            title : title,
            content : content,
            closable : true
            //iconCls: 'icon-default'  
        });
    }
			
}

function openEditTreeDialogT(url,height,width,treeGrid,tree,saveCallBack){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			treeGrid.treegrid("reload");
			tree.tree("reload");
		}
	});
	return mdialog;
}

/**
 * @注释：编辑、添加对话框
 * @param url	访问地址
 * @param height 宽的高度
 * @param width  框的宽度
 * @param dataDatagrid	列表ID
 * @param saveCallBack	点击“保存”按钮后，触发的操作函数
 * 
 */
function openEditDialog(url,height,width,dataGrid,saveCallBack){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			if (dataGrid) {
				dataGrid.datagrid("reload");
			}
		}
	});
	return mdialog;
}

function openEditTwoDialog(url,height,width,dataGrid,saveCallBack){
	var id = "dialogTwoId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			if (dataGrid) {
				dataGrid.datagrid("reload");
			}
		}
	});
	return mdialog;
}

function openAddFacus(url,height,width,dataGrid,saveCallBack){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			if (dataGrid) {
				dataGrid.datagrid("reload");
			}
		},
		onLoad: function () {  
			$('#proSelfCode').focus();
        }  
		
	});
	return mdialog;
}

/**
 * @注释：编辑、添加对话框
 * @param url	访问地址
 * @param height 宽的高度
 * @param width  框的宽度
 * @param dataDatagrid	列表ID
 * @param saveCallBack	点击“保存”按钮后，触发的操作函数
 * 
 */
function openEditTreeDialog(url,height,width,treeGrid,saveCallBack){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			treeGrid.treegrid("reload");
		}
	});
	return mdialog;
}

/**
 * @注释：编辑、添加对话框
 * @param url	访问地址
 * @param height 宽的高度
 * @param width  框的宽度
 * @param dataDatagrid	列表ID
 * @param saveCallBack	点击“保存”按钮后，触发的操作函数
 * 
 */
function openRSEditDialog(url, height, width, firstDataGrid, secondDataGrid, saveCallBack){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			firstDataGrid.datagrid("reload");
			secondDataGrid.datagrid("reload");
		}
	});
	return mdialog;
}

/**
 * @注释：编辑、添加对话框,刷新树
 * @param url	访问地址
 * @param height 宽的高度
 * @param width  框的宽度
 * @param tree	树ID
 * @param node 刷新指定节点下的子节点
 * @param saveCallBack	点击“保存”按钮后，触发的操作函数
 * 
 */
function openDisposeTreeDataDialog(url,height,width,tree,node,saveCallBack){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'保存',
			iconCls:'icon-ok',
			handler:function() {
				saveCallBack(id,node);
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog("destroy");
			tree.tree("reload",node.target);
		}
	});
	return mdialog;
}


/**
 * @注释：查看对话框
 * @param url	访问地址
 * @param height 宽的高度
 * @param width  框的宽度
 * @param dataDatagrid	列表ID
 *
 * 
 */
function openViewDialog(url,height,width,dataGrid){
	var id = "dialogId";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		href:url,
		width:width,
		height:height,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:true,
		resizable:true,
		modal:true,
		closed:true,
		buttons:[ {
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function() {
				$("#"+id).dialog("close");
			}
		} ],
		onClose:function() {
			$("#"+id).dialog('destroy');
			dataGrid.datagrid("reload");
		}
	});
	return mdialog;
}

/**
 * @注释：展示列表
 * @param url	访问地址
 * 
 *//*
		function listdata(url,headTitle){
			//----------数据列表初始化-----------------------
			var dataDatagrid = $("#dataGrid").datagrid({
			url:url,
			title:headTitle,
			fitColumns:true,
			singleSelect:true,
			pagination:true,
			rownumbers:true,
			showPageList:false,
			pagePosition:'bottom',//top','bottom','both'。
			fit:true,
			border:false,
			width:'auto',
			height:'auto',
			striped:true,
			loadMsg : '数据装载中......',
		    onLoadSuccess:function(data){ 

		    } 
		});

		dataDatagrid.datagrid('getPager').pagination({
		    showPageList:false,
		    pageSize:10
		});

		return dataDatagrid;
		}*/



//function easyui_alert(msg){
//	jQuery.messager.alert('信息提示',msg,'info');   
//}
//function easyui_error(msg){
//	jQuery.messager.alert('错误提示',msg,'error');   
//}
function easyui_alert(msg){
	$.messager.show({
		title:'系统消息',
		msg:"<img src='/design/static/messageIcon/ok.png' style='margin-top:13px;margin-left:13px''><b><font style='margin-left:15px;'>"+msg+"<font></b>",
		timeout:2000,
		showType:'slide',
		width:'280px',
		height:'120px'
	});
  
}
function easyui_error(msg){
	$.messager.show({
		title:'系统消息',
		msg:"<img src='/design/static/messageIcon/warning.png' style='margin-top:13px;margin-left:13px''><b><font style='margin-left:15px;'>"+msg+"<font></b>",
		timeout:2000,
		showType:'slide',
		width:'280px',
		height:'120px'
	});
}
/**
 * 取得数据区高度
 */
function setDataHeight(){
	var screenHeight = document.body.clientHeight;
	var bodyHeight = screenHeight*1;
	//alert(bodyHeight);
	var dataH = bodyHeight*0.7;
	$("#wholedatadiv").css("height",bodyHeight);
	$("#datalistdiv").css("height",dataH+"px");

}


function selectShowCls(url,callback){
	var id="selectCls";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		title : '选择显示列',
		width : 500,
		height : 400,
		href : url,
		buttons : [ {
			text : '确定',
			handler : function() {                    
				submitData(id);
				if(typeof callback == 'function'){
					callback();
	        	} else {
	        		window.location=callback;
	        	}
			}
		},
		{
			text : '关闭',
			handler : function() {                 	
				$("#"+id).dialog("close");
			}
		} ]
	}); 
}


function exportXls(url){

	var id="exportCls";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
		title : '选择导出列',
		width : 500,
		height : 400,
		href : url,
		buttons : [ {
			text : '确定',
			handler : function() {                    
				submitData(id);                   
				//window.location=success_backurl;
			}
		},
		{
			text : '关闭',
			handler : function() {                 	
				$("#"+id).dialog("close");
			}
		} ]
	}); 
}

function importXls(templeteName,url,datagrid){
	var id="importXls";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
         title : '导入',
         width : 500,
         height : 400,
         href : url,
         resizable : true,
         buttons : [
		{
			text : '下载模板',
		    handler : function() {
		    	window.location.href = basePath + '/common/downloadTemplete?filename='+templeteName;
		    }
		 },
		 {
		    text : '导入数据',
		    handler : function() {  
		   	 $('#import_xls').uploadifive('upload');
		    }
		 },
         {
             text : '停止导入',
             handler : function() {  
            	 $('#import_xls').uploadifive('stop');
             }
         } ,
         {
             text : '关闭',
             handler : function() {                 	
            	 $("#"+id).dialog("close");
            	 if (typeof datagrid != 'undefined') {
         	 		datagrid.datagrid('reload');
            	 }
             }
         } ],
         onClose:function() {
			$("#"+id).dialog('destroy');
			if (typeof datagrid != 'undefined') {
     	 		datagrid.datagrid('reload');
			}
		}
     }); 
}

function uploadFiles(url){
	
	var id="uploadFiles";
	$(document.body).append("<div id='"+id+"'></div>");
	var mdialog = $('#'+id).dialog({
         title : '上传',
         width : 500,
         height : 400,
         href : url,
         resizable : true,
         buttons : [
		 {
		    text : '上传文件',
		    handler : function() {  
		   	 $('#file_upload').uploadifive('upload');
		    }
		 },
         {
             text : '停止上传',
             handler : function() {  
            	 $('#file_upload').uploadifive('stop');
             }
         } ,
         {
             text : '关闭',
             handler : function() {                 	
            	 $("#"+id).dialog("close");
             }
         } ]
     }); 
}

/**
 * @param element 下拉框的标签ID
 * @param val  val
 * @param text 展示数据
 * @param url 获取下拉框数据的url
 * @param all 是否需要全匹配
 * 			true为全匹配
 * 			false为头部匹配
 */
function autocomplete(element,val,text,url,all){
	$("#"+element).combobox({
	    valueField: val,     
        textField: text,  
        multiple:false,  
        multiline:false,  
        editable:true,  
        hasDownArrow:false,
        filter: function(q, row){  
            var opts = $(this).combobox('options');
            if(all == undefined || all){
            	return row[opts.textField].indexOf(q.toUpperCase()) >-1;
            }else{
            	return row[opts.textField].indexOf(q.toUpperCase()) ==0;
            }
        },  
        onSelect:function(record) {
        	$("#"+element).val(eval('record.' + val));
        	if(typeof evalProVal == 'function'){
        		evalProVal();
        	}
        },  
        url:url  
	});  
}

function downloadFile(filename,downtype){
	
	var url = basePath+'/common/commonDownload';  
    
   	var form = $("<form>");   //定义一个form表单
    form.attr('style','display:none');   //在form表单中添加查询参数
    form.attr('target','');
    form.attr('method','post');
    form.attr('action',url);
    
    var input1 = $('<input>'); 
    input1.attr('type','hidden'); 
    input1.attr('name','filename'); 
    input1.attr('value',filename);                    
    var input2 = $('<input>'); 
    input2.attr('type','hidden'); 
    input2.attr('name','downtype'); 
    input2.attr('value',downtype);   
    
    $('body').append(form);  //将表单放置在web中
    form.append(input1);   //将查询参数控件提交到表单上
    form.append(input2);   //将查询参数控件提交到表单上
    form.submit();   //表单提交
}

function findProductInfoByCodeAndType(selfcode, codetype){
	var user_data;
	var url = basePath +'/common/getProInfo';
	$.ajax({ 
        type: "post", 
        url: url, 
        data: 
        {
        	selfcode : selfcode,
        	codetype : codetype
        },
        async: false,
        dataType: "json", 
        success: function (result) { 
        	 if(!result.success){
     	   		$.messager.alert('错误', result.msg, 'error');
        	 } else {
        		 if(result.obj.length == 1){
        			 //当ISBN对应单个产品，直接返回产品信息
        			 user_data = result.obj[0];
        		 }else{
        			 //当ISBN对应多个产品，弹出产品选择框
        			 chooseProduct(result.obj);
        		 }              
        	 }
        }, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
            alert(errorThrown); 
            return null;
        } 
 	});
	if(user_data)
		return user_data;
}
function chooseProduct(obj){
	var id ='productSelector';
	$(document.body).append("<div id='"+id+"'><table id='productDataGrid' class='easyui-datagrid'></table></div>");
	var mdialog = $('#'+id).dialog({
   		width:1200,
   		height:400,
   		border:false,
   		cache:false,
   		collapsible:false,
   		maximizable:false,
   		resizable:false,
   		modal:true,
   		closed:true,
   		buttons:[ {
   			text:'选择',
   			iconCls:'icon-ok',
   			handler:function() {
   				var rows = productDataGrid.datagrid('getSelections');
   				if(rows.length == 0){
   					$.messager.alert('错误', "未选中产品", 'error');
   				}else{
   					var product = {
   							'proCode':rows[0].proCode,
   							'proName':rows[0].proName,
   							'proSelfCode':rows[0].proSelfCode,
   							'proType':rows[0].proType,
   							'proTypeStr':rows[0].proTypeStr,
   							'proAuthor':rows[0].proAuthor,
   							'proPubDate':rows[0].proPubDate,
   							'proPubName':rows[0].proPubName,
   							'proBriefIntroduction':rows[0].proBriefIntroduction,
   							'proPackageNo':rows[0].proPackageNo,
   							'proPagenum':rows[0].proPagenum,
   							'proEdition':rows[0].proEdition,
   							'proBinding':rows[0].proBinding,
   							'proCurrencyCode':rows[0].proCurrencyCode,
   							'proPubYear':rows[0].proPubYear,
   							'proPrice':rows[0].proPrice,
   							'proWeight':rows[0].proWeight,
   							'proCheckstatus' :rows[0].proCheckstatus
   					}
   					//这个方法自己定义在子页面，供调用当前方法调用，用于返回选中的产品信息
   					setProductMore(product);
   					$("#"+id).dialog("close");
   				}
   				
   			}
   		}, {
   			text:'取消',
   			iconCls:'icon-cancel',
   			handler:function() {
   				$("#"+id).dialog("close");
   			}
   		} ]
   	});
	var productDataGrid = $("#productDataGrid").datagrid({
		 striped : true,
         rownumbers : true,
         pagination : true,
         singleSelect : true,
         idField : 'id',
         sortName : 'id',
         sortOrder : 'desc',
         fit : true,
         fitColumns:true,
         fix:false,
         pageSize : 20,
         pageList : [ 10, 20, 30, 40, 50, 100 ],
         columns: [[
            { 
	       	   field:'ck',checkbox:true
	       	},
			{
	            width : '10%',
	            title : '产品编号',
	            field : 'proCode',
	            sortable : true,
	        	hidden : false
	        } , 
	        {
	            width : '15%',
	            title : '产品名称',
	            field : 'proName',
	            sortable : true,
	        	hidden : false
	        } , 
			{
	            width : '15%',
	            title : 'ISBN',
	            field : 'proSelfCode',
	            sortable : true,
	            hidden : false
	        } , 
			{
	            width : '10%',
	            title : '产品类型',
	            field : 'proTypeStr',
	            sortable : true,
	            hidden : false
	        } , 
			{
	            width : '10%',
	            title : '作者',
	            field : 'proAuthor',
	            sortable : true,
	            hidden : false
	        } , 
			{
	            width : '10%',
	            title : '出版日期',
	            field : 'proPubDate',
	            sortable : true,
	            hidden : false
	        } , 
			{
	            width : '15%',
	            title : '出版社名称',
	            field : 'proPubName',
	            sortable : true,
	            hidden : false
	        } , 
			{
	            width : '15%',
	            title : '书目简介',
	            field : 'proBriefIntroduction',
	            sortable : true,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '包册数',
	            field : 'proPackageNo',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '页数',
	            field : 'proPagenum',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '版别',
	            field : 'proEdition',
	            sortable : false,
	            hidden : true
	        },  
	        {
	            width : '5%',
	            title : '装帧',
	            field : 'proBinding',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '币制',
	            field : 'proCurrencyCode',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '出版年',
	            field : 'proPubYear',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '价格',
	            field : 'proPrice',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '重量',
	            field : 'proWeight',
	            sortable : false,
	            hidden : true
	        },
	        {
	            width : '5%',
	            title : '审读状态',
	            field : 'proCheckstatus',
	            sortable : false,
	            hidden : true
	        }
		]]
	});
	productDataGrid.datagrid('loadData', { total: 0, rows: [] });  
	var rows=[];
	for(var i = 0 ;i< obj.length;i++){
		var product = obj[i];
		rows.push( 
		{
			proCode : product.proCode,
			proSelfCode : product.proSelfCode,
			proType : product.proType,
			proTypeStr : product.proTypeStr,
			proAuthor : product.proAuthor,
			proPubDate : product.proPubDate,
			proPubName : product.proPubName,
			proName : product.proName,
			proBriefIntroduction : product.proBriefIntroduction,
			proPackageNo : product.proPackageNo,
			proPagenum : product.proPagenum,
			proEdition : product.proEdition,
			proBinding : product.proBinding,
			proCurrencyCode : product.proCurrencyCode,
			proPubYear : product.proPubYear,
			proPrice : product.proPrice,
			proCheckstatus : product.proCheckstatus,
			proWeight : product.proWeight
		});
	}
	//赋值数据
	productDataGrid.datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
   	mdialog.dialog('open').dialog('setTitle', '选择产品');
  	mdialog.dialog('center');
}

//实现前端分页
function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}
//展示 产品datagrid 
function chooseProductInTask(obj){
var id ='productSelector';
$(document.body).append("<div id='"+id+"'><table id='productDataGrid' class='easyui-datagrid'></table></div>");
var mdialog = $('#'+id).dialog({
		width:850,
		height:350,
		border:false,
		cache:false,
		collapsible:false,
		maximizable:false,
		resizable:false,
		modal:true,
		closed:true,
	 	buttons: [{
     text:'添加',
     iconCls:'icon-add',
     handler:function(){
          productAdd();
     }
	 },{
     text:'修改',
     iconCls:'icon-edit',
     handler:function() {
			var rows = productDataGrid.datagrid('getSelections');
			if(rows.length == 0){
				$.messager.alert('错误', "未选中产品", 'error');
			}else{
				var product = {
						'proCode':rows[0].proCode,
						'proName':rows[0].proName,
						'proSelfCode':rows[0].proSelfCode,
						'proType':rows[0].proType,
						'proAuthor':rows[0].proAuthor,
						'proPubDate':rows[0].proPubDate,
						'proPubName':rows[0].proPubName,
						'proBriefIntroduction':rows[0].proBriefIntroduction,
						'proPackageNo':rows[0].proPackageNo,
						'proPagenum':rows[0].proPagenum,
						'proEdition':rows[0].proEdition,
						'proBinding':rows[0].proBinding,
						'proCurrencyCode':rows[0].proCurrencyCode,
						'proPubYear':rows[0].proPubYear,
						'proPrice':rows[0].proPrice,
						'proWeight':rows[0].proWeight,
						'proCheckstatus' :rows[0].proCheckstatus
				}
				//这个方法自己定义在子页面，供调用当前方法调用，用于返回选中的产品信息
				setProductMore(product);
				productEdit();
				$("#"+id).dialog("close");
			}
			
		}
	}, {
     text:'取消',
     iconCls:'icon-cancel',
     handler:function(){
         productClose();
     }
	 }]
	});
var productDataGrid = $("#productDataGrid").datagrid({
	 striped : true,
     rownumbers : true,
     pagination : true,
     singleSelect : true,
     idField : 'id',
     sortName : 'id',
     sortOrder : 'desc',
     fit : true,
     fitColumns:true,
     fix:false,
     pageSize : 20,
     pageList : [ 10, 20, 30, 40, 50, 100 ],
     columns: [[
        { 
       	   field:'ck',checkbox:true
       	},
		{
            width : '17%',
            title : '产品编号',
            field : 'proCode',
            sortable : true,
        	hidden : false
        } , 
        {
            width : '23%',
            title : '产品名称',
            field : 'proName',
            sortable : true,
        	hidden : false
        } , 
		{
            width : '17%',
            title : 'ISBN',
            field : 'proSelfCode',
            sortable : true,
            hidden : false
        } , 
		{
            width : '10%',
            title : '作者',
            field : 'proAuthor',
            sortable : true,
            hidden : false
        } , 
		{
            width : '15%',
            title : '出版日期',
            field : 'proPubDate',
            sortable : true,
            hidden : false
        } , 
		{
            width : '15%',
            title : '出版社名称',
            field : 'proPubName',
            sortable : true,
            hidden : false
        } , 
        {
            width : '5%',
            title : '版别',
            field : 'proEdition',
            sortable : false,
            hidden : true
        },  
        {
            width : '5%',
            title : '装帧',
            field : 'proBinding',
            sortable : false,
            hidden : true
        },
        {
            width : '5%',
            title : '币制',
            field : 'proCurrencyCode',
            sortable : false,
            hidden : true
        },
        {
            width : '5%',
            title : '价格',
            field : 'proPrice',
            sortable : false,
            hidden : true
        },
	]]
});
productDataGrid.datagrid('loadData', { total: 0, rows: [] });  
var rows=[];
for(var i = 0 ;i< obj.length;i++){
	var product = obj[i];
	rows.push( 
	{
		proCode : product.proCode,
		proSelfCode : product.proSelfCode,
		proType : product.proType,
		proAuthor : product.proAuthor,
		proPubDate : product.proPubDate,
		proPubName : product.proPubName,
		proName : product.proName,
		proBriefIntroduction : product.proBriefIntroduction,
		proPackageNo : product.proPackageNo,
		proPagenum : product.proPagenum,
		proEdition : product.proEdition,
		proBinding : product.proBinding,
		proCurrencyCode : product.proCurrencyCode,
		proPubYear : product.proPubYear,
		proPrice : product.proPrice,
		proCheckstatus : product.proCheckstatus,
		proWeight : product.proWeight
	});
}
//赋值数据
productDataGrid.datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
	mdialog.dialog('open').dialog('setTitle', '选择产品');
	mdialog.dialog('center');
}
/* function fixHeight(percent)     
	    {     
	        return (document.body.clientHeight) * percent ;      
	    }  

	    function fixWidth(percent)     
	    {     
	        return (document.body.clientWidth - 5) * percent ;      
	    } */ 
/**
 * 添加此代码 为解决IE浏览器不识别startsWith函数
 */
if (typeof String.prototype.startsWith != 'function') {		 
	String.prototype.startsWith = function (prefix){
		return this.slice(0, prefix.length) === prefix;
	};		
}
/**
 * 根据输入的ISBN号，检验ISBN的有效性。依据 GB/T 5795-2006 和 ISO 2108:2005 ISBN
 * 10位标准和13位标准实现（13位标准自2007年1月1日开始实行，在此之前采用10位标准）。
 * 
 * @param var
 *            isbn：需要进行校验的ISBN字符串
 * @return true：所输入的ISBN校验正确；<br/>
 *         false：所输入的ISBN校验错误
 */
function checkISBN(isbn) {
	var count = 0;
	var checkBitInt = 0;
	var cs = isbn.split("");
	if(cs.length == 10){
		for (var i = 0; i < 9; i++) {
			if (cs[i] < '0' || cs[i] > '9') {
				progressClose();
				//$.messager.alert('错误', "ISBN异常！！", 'error'); // 校验失败
				isValid = false;
				return false;
			}
			var c = cs[i] - '0';
			count += c * (10 - i);
		}
		if (cs[9] >= '0' && cs[9] <= '9') {
			checkBitInt = cs[9] - '0';
		} else if (cs[9] == 'X' || cs[9] == 'x') {
			checkBitInt = 10;
		} else {
			progressClose();
			isValid = false;
			return false;
		}
		if ((count + checkBitInt) % 11 == 0) {
			return true; // 校验成功
		} else {
			progressClose();
			isValid = false;
			return false;
		}
	} else if(cs.length == 13){ 
		if(isbn.startsWith("222")){
			var subject = "[0-9]+(.[0-9]+)?";
			if(!isbn.match(subject)){
				progressClose();
				return false;
			}
			if(isbn.length != 13){
				progressClose();
				return false;
			}
			return true;
		}
		var countEven = 0;
		var countOdd = 0;
		for (var i = 0; i < 12; i++) {
			var c = cs[i] - '0';
			if (c < 0 || c > 9) {
				progressClose();
				return false;
			}
			if ((i & 0x1) == 0) {
				countOdd += c;
			} else {
				countEven += c;
			}
		}
		count = countOdd + (countEven * 3);

		if (cs[12] < '0' || cs[12] > '9') {
			progressClose();
			return false;
		}

		checkBitInt = cs[12] - '0';
		if ((count + checkBitInt) % 10 == 0) {
			return true; // 校验成功
		} else {
			progressClose();
			return false;
		}
	}
	else{
		progressClose();
		//$.messager.alert('错误', "ISBN异常！！", 'error'); // 校验失败
		return false;
	}
}		

function findCnpicByCodeAndType(selfcode, codetype){
	var user_data;
	var url = basePath +'/common/getCnpicProduct';
	$.ajax({ 
        type: "post", 
        url: url, 
        data: 
        {
        	selfcode : selfcode,
        	codetype : codetype
        },
        async: false,
        dataType: "json", 
        success: function (result) { 
        	 if(!result.success){
     	   		$.messager.alert('错误', result.msg, 'error');
        	 } else {
        		 if(result.obj.length == 1){
        			 //当ISBN对应单个产品，直接返回产品信息
        			 user_data = result.obj[0];
        		 }else{
        			 //当ISBN对应多个产品，弹出产品选择框
        			 chooseCnpicProduct(result.obj);
        		 }              
        	 }
        }, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
            alert(errorThrown); 
            return null;
        } 
 	});
	if(user_data)
		return user_data;
}

function chooseCnpicProduct(obj){
	var id ='productSelector';
	$(document.body).append("<div id='"+id+"'><table id='productDataGrid' class='easyui-datagrid'></table></div>");
	var mdialog = $('#'+id).dialog({
   		width:600,
   		height:300,
   		border:false,
   		cache:false,
   		collapsible:false,
   		maximizable:false,
   		resizable:false,
   		modal:true,
   		closed:true,
   		buttons:[ {
   			text:'选择',
   			iconCls:'icon-ok',
   			handler:function() {
   				var rows = productDataGrid.datagrid('getSelections');
   				if(rows.length == 0){
   					$.messager.alert('错误', "未选中产品", 'error');
   				}else{
   					var product = {
   							'contentId':rows[0].contentId,
   							'contentTitle':rows[0].contentTitle
   					}
   					//这个方法自己定义在子页面，供调用当前方法调用，用于返回选中的产品信息
   					setProductMore(product);
   					$("#"+id).dialog("close");
   				}
   				
   			}
   		}, {
   			text:'取消',
   			iconCls:'icon-cancel',
   			handler:function() {
   				$("#"+id).dialog("close");
   			}
   		} ]
   	});
	var productDataGrid = $("#productDataGrid").datagrid({
		 striped : true,
         rownumbers : true,
         pagination : true,
         singleSelect : true,
         idField : 'contentId',
         sortName : 'contentId',
         sortOrder : 'desc',
         fit : true,
         fitColumns:true,
         fix:false,
         pageSize : 20,
         pageList : [ 10, 20, 30, 40, 50, 100 ],
         columns: [[
            { 
	       	   field:'ck',checkbox:true
	       	},
			{
	            width : '10%',
	            title : '产品ID',
	            field : 'contentId',
	            sortable : true,
	        	hidden : true
	        } , 
	        {
	        	width : '10%',
	        	title : '产品标题',
	        	field : 'contentTitle',
	        	sortable : true,
	        	hidden : false
	        } , 
	        {
	            width : '15%',
	            title : 'ISBN',
	            field : 'isbn',
	            sortable : true,
	        	hidden : false
	        } , 
			{
	            width : '15%',
	            title : 'ISSN',
	            field : 'issn',
	            sortable : true,
	            hidden : false
	        } , 
			{
	            width : '10%',
	            title : '作者',
	            field : 'contentAuthor',
	            sortable : true,
	            hidden : false
	        } 
		]]
	});
	productDataGrid.datagrid('loadData', { total: 0, rows: [] });  
	var rows=[];
	for(var i = 0 ;i< obj.length;i++){
		var product = obj[i];
		rows.push( 
		{
			contentId : product.contentId,
			contentTitle : product.contentTitle,
			contentAuthor : product.contentAuthor,
			isbn : product.isbn,
			issn : product.issn
		});
	}
	//赋值数据
	productDataGrid.datagrid({loadFilter:pagerFilter}).datagrid('loadData', rows);
   	mdialog.dialog('open').dialog('setTitle', '选择产品');
  	mdialog.dialog('center');
}
