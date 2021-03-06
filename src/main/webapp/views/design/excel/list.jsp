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
            url : '${path}/pages/xls/dataGrid',
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
                width : '1%',
                title : '主键',
                field : 'id',
                sortable : true,
                align : 'center',
            	hidden : true
            } , 
            {
                width : '10%',
                title : '对象名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
			{
                width : '5%',
                title : '对象编号',
                field : 'code',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '6%',
                title : '目录类型',
                field : 'pathType',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case "1":
			                 return 'ftp目录';
			             case "2":
			                 return '服务器本地目录';
			        }
         		}
            } , 
            {
                width : '10%',
                title : 'excel保存路径',
                field : 'path',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
           /*  {
                width : '5%',
                title : '是否映射表',
                field : 'type',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case "1":
			                 return '<b style="color: green;">是</b>';
			             case "0":
			                 return '<b style="color: red;">否</b>';
			        }
         		}
            } , 
            {
                width : '5%',
                title : '映射表名',
                field : 'dbTable',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '5%',
                title : '字段映射是否完成',
                field : 'mapped',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case "1":
			                 return '<b style="color: green;">是</b>';
			             case "0":
			                 return '<b style="color: red;">否</b>';
			        }
         		}
            } , 
            {
                width : '7%',
                title : '是否设置导入规则',
                field : 'ifRole',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case "1":
			                 return '<b style="color: green;">是</b>';
			             case "0":
			                 return '<b style="color: red;">否</b>';
			        }
         		}
            } ,  */
            {
                field : 'action',
                title : '操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="xls-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="editFun(\'{1}\',\'{2}\');" >编辑</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="del_{0}" class="xls-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="deleteFun(\'{1}\',\'{2}\');" >删除</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="xls-easyui-linkbutton-column" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="columnView(\'{1}\',\'{2}\');" >查看字段</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					/* if(row.type == "0"){
						str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="xls-easyui-linkbutton-table" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="mappedTable(\'{1}\',\'{2}\');" >映射表</a>', index, row.id, index);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					}
					str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="xls-easyui-linkbutton-role" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="importRule(\'{1}\',\'{2}\');" >设置导入规则</a>', index, row.id, index);
					if(row.ifRole == "1"){
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="xls-easyui-linkbutton-import" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="beginImport(\'{1}\',\'{2}\');" >开始导入数据</a>', index, row.id, index);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="xls-easyui-linkbutton-pause" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="pause(\'{1}\',\'{2}\');" >暂停导入</a>', index, row.id, index);
					} */
					return str;
                }
            } 
			]],
            onLoadSuccess:function(data){
                $('.xls-easyui-linkbutton-edit').linkbutton({text:'更改',plain:true,iconCls:'icon-dc-edit'});
                $('.xls-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-dc-del'});
                $('.xls-easyui-linkbutton-column').linkbutton({text:'设置字段',plain:true,iconCls:'icon-dc-account'});
                $('.xls-easyui-linkbutton-table').linkbutton({text:'映射表',plain:true,iconCls:'icon-dc-fileUpload'});
                $('.xls-easyui-linkbutton-role').linkbutton({text:'导入规则',plain:true,iconCls:'icon-dc-person'});
                $('.xls-easyui-linkbutton-import').linkbutton({text:'开始导入',plain:true,iconCls:'icon-dc-cost'});
                $('.xls-easyui-linkbutton-pause').linkbutton({text:'暂停导入',plain:true,iconCls:'icon-dc-print'});
            },
            toolbar : '#toolbar'
        });
    });
    
    function deleteFun(id,index){
    	$.ajax({
    		"dataType" : "json",
    		"type" : "GET",
    		"url" : '${path}/pages/xls/delete?id='+id,
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
    	var url="${path}/pages/xls/addpage";
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    function mappedTable(id,index){
    	var url="${path}/pages/xls/mappedPage?id="+id;
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '映射表');
    }
    
    function columnView(id,index){
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/xlscolumn/columnListByXlsId?xlsId='+id,//'${path}/pages/xlscolumn/fieldListManagerByObjectId?id=',//?xlsId= +id
    	    title: '查看字段',    
    	    split: true
    	});  
    }
    
    function editFun(id) {
        var url="${path}/pages/xls/editPage?id=" + id;
		openEditDialog(url, 500, 700,  dataGrid, function(cid) {
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
    
    function importRule(id,index){
    	var url="${path}/pages/xls/setRolePage?id="+id;
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '设置导入规则');
    }
    
</script>
</head>
<body>	
	<div id="ListLay" class="easyui-layout" data-options="fit:true,border:true,title:'岗位列表'" >
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
								<b>对象名称：</b>
		                		<input id="name_query" name="name" type="text" placeholder="连接名称" class="easyui-textbox span2" maxlength="50" value=""/>
							</p>
	    					<p>
								<b>对象编号：</b>
		                		<input id="code_query" name="code" type="text" placeholder="连接编号" class="easyui-textbox span2" maxlength="32" value=""/>
							</p>
		    				<p class="btnSear">
				                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-search',plain:true" onclick="searchFun();">查询</a>
				                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-close',plain:true" onclick="cleanFun();">清空</a>
			                </p>  
		    			</div>
		    		</form>
	    		</div>
	    </div>
    </div>
</body>
</html>