<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/basejs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${staticPath}/static/style/css/search.css"/>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>系统信息管理</title>
<script type="text/javascript">
	var detailDataGrid;
	$(function() {
	    detailDataGrid = $('#detailDataGrid').datagrid({
	        url : '${path}/pages/echarts/dataGridByTypeMany',
	        striped : true,
	        rownumbers : true,
	        pagination : true,
	        singleSelect : false,
	        idField : 'id',
	        sortName : 'createDate',
	        sortOrder : 'desc',
	        fit : true,
	        fitColumns : true,
	        fix : false,
	        autoRowHeight : false,
	        pageSize : 20,
	        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
	        columns: [[
	        { 
	       	   field : 'ck', checkbox : true
	       	},{
	            width : '10%',
	            title : '主键',
	            field : 'id',
	            sortable : true,
	            align : 'center',
	        	hidden : true
	        } , {
	            width : '10%',
	            title : '图表名称',
	            field : 'name',
	            sortable : false,
	            align : 'center',
	            hidden : false,
	       /*  } , {
	            width : '10%',
	            title : '图表编码',
	            field : 'code',
	            sortable : false,
	            align : 'center',
	            hidden : false */
	        } , {
	            width : '10%',
	            title : '模板名称',
	            field : 'echartName',
	            sortable : false,
	            align : 'center',
	            hidden : false
	        } , {
	            field : 'action2',
	            title : '操作',
	            width : '25%',
	            align : 'center',
	            formatter : function(value, row, index) {
	                var str = '';
	               	str += $.formatString('<a hidden href="javascript:void(0)" class="echartExport-easyui-linkbutton-mustColumn" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="mustColumn(\'{1}\',\'{2}\');" >必填信息</a>', index, row.id, index);
	               	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
	               	str += $.formatString('<a hidden href="javascript:void(0)" class="echartExport-easyui-linkbutton-jsonMap" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="jsonMap(\'{1}\',\'{2}\');" >数据映射</a>', index, row.id, index);
	               	if(row.status=='0'){
	               		str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
	                   	str += $.formatString('<a hidden href="javascript:void(0)" class="echartExport-easyui-linkbutton-generate" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="generate(\'{1}\',\'{2}\');" >生成图表</a>', index, row.id, index);
	               	}
					return str;
	            }
	        }
			]],
	        onLoadSuccess:function(data){
	            $('.echartExport-easyui-linkbutton-mustColumn').linkbutton({text:'必填信息',plain:true,iconCls:'icon-dc-view'});
	            $('.echartExport-easyui-linkbutton-jsonMap').linkbutton({text:'数据映射',plain:true,iconCls:'icon-dc-account'});
	            $('.echartExport-easyui-linkbutton-generate').linkbutton({text:'生成图表',plain:true,iconCls:'icon-dc-book'});
	        },
	        toolbar : '#detailToolbar'
	    });
	});
    
    function deleteFun(id,index){
    	$.ajax({
    		"dataType" : "json",
    		"type" : "GET",
    		"url" : '${path}/pages/echarts/delete?id='+id,
    		"cache" : false,
    		"success" : function(response) {
    			if (response.success) {
   	            	easyui_alert(response.msg);
   	            	detailDataGrid.datagrid('reload');
   	            }
    		},
    		"error" : function(response) {
    			easyui_error(response.msg);
    			detailDataGrid.datagrid('reload');
    		}
    	});
    }

    //新增页面
    function addFun() {
    	var url="${path}/pages/echarts/addAttr?type=many";
        openEditDialog(url, 500, 700, detailDataGrid, function(cid) {
        	addDetail(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    function generate(id,index) {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/generate?rsId="+id;
    	
    	openViewDialog(url, 500, 900, detailDataGrid).dialog('open').dialog('setTitle', '生成图表');
    }
    
    function mustColumn(id,index) {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/addMustColumn?rsId="+id;
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function jsonMap(id,index) {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/addJsonMap?rsId="+id;
    	
    	openViewDialog(url, 500, 900, detailDataGrid).dialog('open').dialog('setTitle', '查看数据映射');
    }
    
    
</script>
</head>
<body>	
   <div id="ListLay" class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'center',border:false">
            <table id="detailDataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    <div id="detailToolbar" style="display: none;">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">添加</a>
    </div>
</body>
</html>