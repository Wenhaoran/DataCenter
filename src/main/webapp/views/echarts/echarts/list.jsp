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
	var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path}/pages/echarts/dataGrid',
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
                title : '模板名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
			{
                width : '10%',
                title : '模板编码',
                field : 'code',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '10%',
                title : '模板类型',
                field : 'type',
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
                    str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="echarts-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="editFun(\'{1}\',\'{2}\');" >编辑</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="del_{0}" class="echarts-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="deleteFun(\'{1}\',\'{2}\');" >删除</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="must_{0}" class="echarts-easyui-linkbutton-must" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="must(\'{1}\',\'{2}\');" >必填字段</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="json_{0}" class="echarts-easyui-linkbutton-json" data-options="plain:true,iconCls:\'icon-dc-json\'" onclick="json(\'{1}\',\'{2}\');" >json对象</a>', index, row.id, index);
					return str;
                }
            } 
			]],
            onLoadSuccess:function(data){
                $('.echarts-easyui-linkbutton-edit').linkbutton({text:'更改',plain:true,iconCls:'icon-dc-edit'});
                $('.echarts-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-dc-del'});
                $('.echarts-easyui-linkbutton-must').linkbutton({text:'必填字段',plain:true,iconCls:'icon-dc-view'});
                $('.echarts-easyui-linkbutton-json').linkbutton({text:'json数据对象',plain:true,iconCls:'icon-dc-upload'});
            },
            toolbar : '#toolbar'
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
    	var url="${path}/pages/echarts/addPage";
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
        var url="${path}/pages/echarts/editPage?id=" + id;
		openEditDialog(url, 500, 700,dataGrid, function(cid) {
			modify(cid);
		}).dialog('open').dialog('setTitle', '更改系统状态');
    }

    
    function must(id,index) {
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/echartColumn/columnListManagerByEchartId?echarId='+id,
    	    title: '当前必填字段信息',    
    	    split: true
    	}); 
    }
    
    function json(id,index) {
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/echartJson/jsonListManagerByEchartId?echarId='+id,
    	    title: '当前json对象信息',    
    	    split: true
    	}); 
    }
    
    
</script>
</head>
<body>	
   <div id="ListLay" class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'center',border:false">
            <table id="dataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    <div id="toolbar" style="display: none;">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">添加</a>
    </div>
</body>
</html>