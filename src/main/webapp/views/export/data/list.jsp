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
	var exportDataGrid;
	var cacheRow;
    $(function() {
        exportDataGrid = $('#exportDataGrid').datagrid({
            url : '${path}/pages/export/dataGrid',
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
                width : '5%',
                title : '报表名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
			{
                width : '5%',
                title : '源数据库',
                field : 'connName',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '25%',
                title : '报表sql',
                field : 'sql',
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
					str += $.formatString('<a href="javascript:void(0)" data-row="del_{0}" class="role-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="deleteFun(\'{1}\',\'{2}\');" >删除</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="executeSQL_{0}" class="role-easyui-linkbutton-executeSQL" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="executeSQL(\'{1}\',\'{2}\');" >执行sql</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="charts_{0}" class="role-easyui-linkbutton-charts" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="charts(\'{1}\',\'{2}\');" >执行sql</a>', index, row.id, index);
					return str;
                }
            } 
			]],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'icon-dc-del'});
                $('.role-easyui-linkbutton-executeSQL').linkbutton({text:'执行sql',plain:true,iconCls:'icon-dc-account'});
                $('.role-easyui-linkbutton-charts').linkbutton({text:'单数据源图表',plain:true,iconCls:'icon-dc-account'});
            },
            toolbar : '#toolbar'
        });
    });
    
    
    function deleteFun(id,index) {
        $.messager.confirm('询问', '您是否要删除当前报表查询？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path}/pages/export/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                    	easyui_alert(result.msg);
                    	exportDataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function charts(id,index) {
		$('#listLay').layout('remove','south');
    	$('#listLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/echarts/echartsListManagerByExportId?exportId='+id+'&type=one',
    	    title: '报表数据',
    	    split: true
    	});
    }
    
    function executeSQL(id,index) {
		$('#listLay').layout('remove','south');
    	$('#listLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/export/viewReportPage?id='+id,
    	    title: '报表数据',
    	    split: true
    	});
    }
</script>
</head>
<body>	
	<div id=listLay class="easyui-layout" data-options="fit:true,border:true,title:'岗位列表'" >
	   <div class="easyui-layout" data-options="fit:true,border:true">
	        <div data-options="region:'center',border:false">
	            <table id="exportDataGrid" data-options="fit:true,border:false"></table>
	        </div>
	    </div>
    </div>
</body>
</html>