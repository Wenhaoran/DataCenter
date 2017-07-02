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
            url : '${path}/pages/impLog/dataGrid',
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
                title : '是否成功',
                field : 'type',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			if(value=="成功"){
         				return '<b style="color: green;">成功</b>';
         			}else{
         				return '<b style="color: red;">失败</b>';
         			}
         		}
            } , 
            {
                width : '25%',
                title : '错误原因',
                field : 'errorInfo',
                sortable : false,
                align : 'center',
                hidden : false,
            } , 
            {
                field : 'action2',
                title : '导入操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
					str += $.formatString('<a href="javascript:void(0)" data-row="showLogInfo_{0}" class="role-easyui-linkbutton-showLogInfo" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="showLogInfo(\'{1}\',\'{2}\');" >查看日志明细</a>', index, row.id, index);
					return str;
                }
            } 
			]],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-showLogInfo').linkbutton({text:'查看日志明细',plain:true,iconCls:'icon-dc-account'});
            },
            toolbar : '#toolbar'
        });
    });
    

    //新增页面
    function showLogInfo(id,index) {
    	var url="${path}/pages/impLog/showLogInfo?logId="+id;
        openEditDialog(url, 500, 700, dataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '查看日志明细');
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
								<b>规则名称：</b>
		                		<select id="roleId" name="roleId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
					    			<option value="">-请选择-</option>
					    			<c:forEach items="${roleList}" var="role">
										<option value="${role.id}">${role.name}</option>
									</c:forEach>
								 </select>
							</p>
		    				<p>
								<b>导入类型：</b>
		                		<select id="fileType" name="fileType" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
				              		<option value="">-请选择-</option>
					                <option value="txt">text:txt</option>
					                <option value="xls">excel:xls</option>
								 </select>
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