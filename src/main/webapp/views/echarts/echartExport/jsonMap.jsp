<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
	var jsonMapListDataGrid;
	$(function() {
	  	jsonMapListDataGrid = $('#jsonMapListDataGrid').datagrid({
	            url : '${path}/pages/echarts/jsonMapListDataGrid',
	            queryParams: {
	            	rsId : '${rsId}',
            	},
	            striped : true,
	            rownumbers : true,
	            pagination : true,
	            singleSelect : false,
	            autoRowHeight:false,
	            idField : 'id',
	            sortName : 'createDate',//id
	            sortOrder : 'desc',
	            fit : true,
	            fitColumns:true,
	            fix:false,
	            pageSize : 20,
	            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
	            columns: [[
	            { 
	           	   field:'ck',checkbox:true
	           	},
				{
	                width : '10%',
	                title : '主键',
	                field : 'id',
	                sortable : false,
	                align : 'center',
	            	hidden : true
	            } , 
				{
	                width : '15%',
	                title : '对应 json数据名称',
	                field : 'jsonName',
	                sortable : false,
	                align : 'center',
	                hidden : false
	            } , 
	            {
	                width : '15%',
	                title : '对应数据模板',
	                field : 'temp',
	                sortable : false,
	                align : 'center',
	                hidden : false
	            } , 
	            {
	                width : '15%',
	                title : '对应数据序号',
	                field : 'jmapOrder',
	                sortable : false,
	                align : 'center',
	                hidden : false
	            } , 
	            {
	                width : '15%',
	                title : '对应报表名称',
	                field : 'jsonCode',
	                sortable : false,
	                align : 'center',
	                hidden : false
	            } , 
	            {
	                width : '15%',
	                title : '对应当前报表字段',
	                field : 'column',
	                sortable : false,
	                align : 'center',
	                hidden : false
	            }
				]],
	            onLoadSuccess:function(data){
	            },
	            toolbar : '#addInstockDetListToolbar'
	        });
	});
	
	function addMap() {
		var rsId = '${rsId}';
		
		var url="${path}/pages/echarts/addJsonMapValue?rsId="+rsId;
    	
		openEditTwoDialog(url, 500, 900, jsonMapListDataGrid, function(cid) {
			addMapPage(cid);
		}).dialog('open').dialog('setTitle', '添加映射');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
        <div id="DetailLay" class="easyui-layout" data-options="fit:true,border:true,title:'映射明细信息'">
		    <%-- <div data-options="region:'north',border:false" style="width:100%;height: 30px; overflow: hidden;background-color: #fff">
		     	<form id="jsonMapDelForm" method="post">
		        	<input type="text" id="jsonMapEchartId" name="echartId" value="${echart.id}" />
		        	<input type="text" id="jsonMapExportId" name="exportId" value="${export.id}" />
		        	<input type="hidden" id="jsonMapIdsId" />
		        </form>
		    </div> --%>
		    <div data-options="region:'center',fit:true,border:true" style="height:95%;">
		        <table id="jsonMapListDataGrid" data-options="border:false"></table>
		    </div>
		    <div id="addInstockDetListToolbar" >
		    	<a onclick="addMap();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加映射</a>
		        <a onclick="detailDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">删除映射</a>
			</div>
		</div>
</div>