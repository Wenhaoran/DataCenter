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
	var currency_Button_Mod = '';
	var dataGrid;
	var cacheRow;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path}/pages/ftp/dataGrid',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'createDate',//id
            sortOrder : 'desc',
            fit : true,
            fitColumns : true,
            fix : false,
            autoRowHeight : false,
          	//onClickCell:function(){}
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns: [[
			{ 
			   field : 'ck', checkbox : true
			},
			{
                width : '10%',
                title : '主键',
                field : 'id',
                sortable : true,
                align : 'center',
            	hidden : true
            } , 
			{
                width : '10%',
                title : '连接编号',
                field : 'code',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
			{
                width : '10%',
                title : '连接名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '10%',
                title : '连接地址',
                field : 'address',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '10%',
                title : '连接端口',
                field : 'port',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                field : 'action',
                title : '操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="ftp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="editFun(\'{1}\',\'{2}\');" >编辑</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="del_{0}" class="ftp-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="deleteFun(\'{1}\',\'{2}\');" >删除</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="conn_{0}" class="ftp-easyui-linkbutton-conn" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="connect(\'{1}\');" >测试连接</a>', index, row.id);
					return str;
                }
            } 
			]],
            onLoadSuccess:function(data){
                $('.ftp-easyui-linkbutton-edit').linkbutton({text:'更改',plain:true,iconCls:'icon-dc-edit'});
                $('.ftp-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-dc-del'});
                $('.ftp-easyui-linkbutton-conn').linkbutton({text:'测试连接',plain:true,iconCls:'icon-dc-upload'});
            },
            toolbar : '#toolbar'
        });
    });
    
    function deleteFun(id,index){
    	$.ajax({
    		"dataType" : "json",
    		"type" : "GET",
    		"url" : '${path}/pages/ftp/delete?id='+id,
    		"cache" : false,
    		"success" : function(response) {
    			if (response.success) {
   	            	easyui_alert(response.msg);
   	            	dataGrid.datagrid('reload');
   	            }
    		},
    		"error" : function(response) {
    			easyui_error(response.msg);
    			dataGrid.datagrid('reload');
    		}
    	});
    }

    //新增页面
    function addFun() {
    	var url="${path}/pages/ftp/addPage";
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        var url="${path}/pages/ftp/editPage?id=" + id;
		openEditDialog(url, 500, 700,dataGrid, function(cid) {
			modify(cid);
		}).dialog('open').dialog('setTitle', '更改系统状态');
    }

    
    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    
    function cleanFun() {
        $('#searchForm input').val('');
        $('#searchForm select').combobox({
        	onLoadSuccess: function (row, data) {
        		$(this).combobox('setValue', '');
        	}
        });
        dataGrid.datagrid('load', {});
    }
    
    function connect(id){
    	$.ajax({
	        type : "post",  
	    	url : "${path}/pages/ftp/testConnect",  
	    	data:{id:id},
	    	success : function(result) {
	    		var data = JSON.parse(result);
                 if (data.success) {  
                	easyui_alert(data.msg);
                } else {
                	easyui_error(data.msg);
                } 
            }
	     });
    }
</script>
</head>
<body>	
   <div class="easyui-layout" data-options="fit:true,border:true">
     
        <div data-options="region:'center',border:false">
            <table id="dataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    <div id="toolbar" style="display: none;">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">添加</a>
        <div data-options="region:'north',border:false,title:'查询条件'" style="height:70px; overflow: hidden;background-color: #fff">
	    		<form id="searchForm">
	    			<div class="search_condition">
    					<p>
							<b>连接编号：</b>
	                		<input id="code_query" name="code" type="text" placeholder="连接编号" class="easyui-textbox span2" maxlength="32" value=""/>
						</p>
    					<p>
							<b>连接名称：</b>
	                		<input id="name_query" name="name" type="text" placeholder="连接名称" class="easyui-textbox span2" maxlength="50" value=""/>
						</p>
	    				<p class="btnSear">
			                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-search',plain:true" onclick="searchFun();">查询</a>
			                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-cancel',plain:true" onclick="cleanFun();">清空</a>
		                </p>  
	    			</div>
	    		</form>
    		</div>
    </div>
</body>
</html>