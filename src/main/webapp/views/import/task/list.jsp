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
            url : '${path}/pages/importRole/dataGrid',
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
                width : '5%',
                title : '规则名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
			{
                width : '5%',
                title : '对象名称',
                field : 'objName',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '5%',
                title : '对象类型',
                field : 'type',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                width : '10%',
                title : '当前状态',
                field : 'importType',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case "0":
			                 return '<b style="color: red;">请配置映射数据表</b>';
			             case "1":
			                 return '<b style="color: red;">请设置映射规则明细</b>';
			             case "2":
			                 return '<b style="color: red;">请设置导入任务时间</b>';
			             case "3":
			                 return '<b style="color: green;">可以导入</b>';
			             case "4":
			                 return '<b style="color: red;">请设置映射规则明细</b>';
			        }
         		}
            } , 
            {
                width : '5%',
                title : '是否可用',
                field : 'status',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case 0:
			                 return '不可用';
			             case 1:
			                 return '可用';
			        }
         		}
            } , 
            {
                field : 'action',
                title : '基本操作',
                width : '30%',
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                   
					str += $.formatString('<a href="javascript:void(0)" data-row="status_{0}" class="role-easyui-linkbutton-status" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="changeStatus(\'{1}\',\'{2}\');" >启用/停用</a>', index, row.id, index);
					
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="table_{0}" class="role-easyui-linkbutton-table" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="mappedTable(\'{1}\',\'{2}\');" >映射表</a>', index, row.id, index);
					if(row.importType!="0"){
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" data-row="column_{0}" class="role-easyui-linkbutton-detail" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="roleDetail(\'{1}\',\'{2}\');" >规则明细</a>', index, row.id, index);	
	                }
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="task_{0}" class="role-easyui-linkbutton-task" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="task(\'{1}\',\'{2}\');" >任务定时</a>', index, row.id, index);
					if(row.importType=="3"){
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" data-row="sql_{0}" class="role-easyui-linkbutton-sql" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="showSql(\'{1}\',\'{2}\');" >查看sql</a>', index, row.id, index);	
					}
					
					return str;
                }
            } , 
            {
                field : 'action2',
                title : '导入操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
					if(row.status!=0){
						str += $.formatString('<a href="javascript:void(0)" data-row="del_{0}" class="role-easyui-linkbutton-begin" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="begin(\'{1}\',\'{2}\');" >开始导入</a>', index, row.id, index);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="role-easyui-linkbutton-pause" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="pause(\'{1}\',\'{2}\');" >暂停导入</a>', index, row.id, index);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" data-row="one_{0}" class="role-easyui-linkbutton-one" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="one(\'{1}\',\'{2}\');" >执行一次</a>', index, row.id, index);
					}
					return str;
                }
            } 
			]],
            onLoadSuccess:function(data){
            	for(var num = 0;num<data.rows.length;num++){
                	if(data.rows[num].status == 0){
                		$("a[data-row='status_"+num+"']").linkbutton({text:'启用',plain:true,iconCls:'icon-dc-add'});	
                    }else{
                    	$("a[data-row='status_"+num+"']").linkbutton({text:'停用',plain:true,iconCls:'icon-dc-del'});
                    }
                }
                $('.role-easyui-linkbutton-table').linkbutton({text:'映射表',plain:true,iconCls:'icon-dc-account'});
                $('.role-easyui-linkbutton-detail').linkbutton({text:'规则明细',plain:true,iconCls:'icon-dc-account'});
                $('.role-easyui-linkbutton-task').linkbutton({text:'任务定时',plain:true,iconCls:'icon-dc-fileUpload'});
                $('.role-easyui-linkbutton-sql').linkbutton({text:'查看sql',plain:true,iconCls:'icon-dc-account'});
                $('.role-easyui-linkbutton-begin').linkbutton({text:'开始导入',plain:true,iconCls:'icon-dc-account'});
                $('.role-easyui-linkbutton-pause').linkbutton({text:'暂停导入',plain:true,iconCls:'icon-dc-account'});
                $('.role-easyui-linkbutton-one').linkbutton({text:'执行一次',plain:true,iconCls:'icon-dc-account'});
            },
            toolbar : '#toolbar'
        });
    });
    
    function changeStatus(id,index){
    	$.ajax({
    		"dataType" : "json",
    		"type" : "GET",
    		"url" : '${path}/pages/importRole/changeStatus?roleId='+id,
    		"cache" : false,
    		"success" : function(response) {
    			if (response.success) {
   	            	easyui_alert(response.msg);
   	            	dataGrid.datagrid('reload');
   	            }
    		},
    		"error" : function(response) {
    			//easyui_error(response.msg);
    			dataGrid.datagrid('reload');
    		}
    	});
    }

    //新增页面
    function addFun() {
    	var url="${path}/pages/importRole/addPage";
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '新增');
    }
    
    function task(id,index){
    	var url="${path}/pages/importRole/taskPage?roleId="+id;
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '设置定时任务');
    }
    
    function mappedTable(id,index){
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/importRt/fieldListManagerByRoleId?roleId='+id,//'${path}/pages/xlscolumn/fieldListManagerByObjectId?id=',//?xlsId= +id
    	    title: '查看',    
    	    split: true
    	}); 
    }
    
    function roleDetail(id,index){
    	$('#ListLay').layout('remove','south');
    	$('#ListLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/importDetail/fieldListManagerByRoleId?roleId='+id,//'${path}/pages/xlscolumn/fieldListManagerByObjectId?id=',//?xlsId= +id
    	    title: '查看字段',    
    	    split: true
    	});  
    }
    
    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        var url="${path}/pages/xls/editPage?id=" + id;
		openEditDialog(url, 200, 300, dataGrid, function(cid) {
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