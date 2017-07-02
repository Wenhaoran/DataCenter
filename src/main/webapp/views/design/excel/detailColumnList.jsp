<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var xlsId = '${xls.id}';
    	var xlstype = '${xls.type}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/xlscolumn/dataGridByXlsId?xlsId='+xlsId,
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
                width : '40%',
                title : '字段值',
                field : 'code',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '40%',
                title : '字段描述',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false
            }// , 
			/* {
                width : '10%',
                title : '是否映射数据库字段',
                field : 'status',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case 1:
			                 return '是';
			             case 0:
			                 return '否';
			        }
         		}
            } , 
            {
                width : '10%',
                title : '映射字段',
                field : 'tabColumn',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                field : 'action',
                title : '操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
					if(xlstype !="0"&&row.type == "0"){
						str += $.formatString('<a href="javascript:void(0)" data-row="edit_{0}" class="column-easyui-linkbutton-table" data-options="plain:true,iconCls:\'icon-dc-del\'" onclick="mappedColumn(\'{1}\',\'{2}\');" >映射表字段</a>', index, row.id, index);
					}
					return str;
                }
            }  */
			]],
            onLoadSuccess:function(data){
                $('.column-easyui-linkbutton-table').linkbutton({text:'映射表字段',plain:true,iconCls:'icon-dc-fileUpload'});
            },
            toolbar : '#detailToolbar'
        });
    });
    
    function mappedColumn(id,index){
    	var xlsId = '${xls.id}';
		var url="${path}/pages/xlscolumn/mappedColumn?id="+id+"&xlsId="+xlsId;
    	
		openRSEditDialog(url, 500, 900, dataGrid,detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '映射字段');
    }

    function add() {
    	var xlsId = '${xls.id}';
    	var url="${path}/pages/xlscolumn/addPage?xlsId="+xlsId;
    	
    	openRSEditDialog(url, 500, 900, dataGrid,detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function detailDeleteFun() {
    	var checkedItems = detailDataGrid.datagrid('getChecked');
    	if(checkedItems.length == 0){
    		easyui_alert('请至少选择一条记录！');
    		return false;
    	}
    	
    	var ids = '';
    	$.each(checkedItems, function(index, item){    
    		ids+=item.id+',';    		
    	});
//     	$('#idsDetail').val(ids);
        progressLoad();
        var xlsId = '${xls.id}';
        $.post('${path}/pages/xlscolumn/deleteColumn', {
            ids : ids,
            xlsId : xlsId
        }, function(result) {
            if (result.success) {
            	easyui_alert(result.msg);
            	for(var i = checkedItems.length; i > 0; i--){
            		var index = detailDataGrid.datagrid('getRowIndex', checkedItems[i-1]);
            		detailDataGrid.datagrid('deleteRow', index);
            	}
            	dataGrid.datagrid('reload');
                detailDataGrid.datagrid('reload');
            }
            progressClose();
        }, 'JSON');
	        
    }

    function detailSearchFun() {
        detailDataGrid.datagrid('load', $.serializeObject($('#detailSearchForm')));
    }
    
    function detailCleanFun() {
        $('#detailSearchForm input').val('');
        
        $('#detailSearchForm select').combobox({
        	onLoadSuccess: function (row, data) {
        		$(this).combobox('setValue', '');
        	}
        });
        detailDataGrid.datagrid('load', {});
    }
    
    function mappedOK(){
    	var xlsId = '${xls.id}';
    	$.post('${path}/pages/xls/mappedOK', {
            xlsId : xlsId
        }, function(result) {
            if (result.success) {
            	easyui_alert(result.msg);
            	dataGrid.datagrid('reload');
                detailDataGrid.datagrid('reload');
            }
            progressClose();
        }, 'JSON');
    }
</script>
</head>
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'进货发票明细列表'">
			<div data-options="region:'north',fit:false,border:false,title:'查询条件',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
	    		<form id="detailSearchForm">
	    			<input id='"xlsId"' name="xlsId" value='${xls.id}' type="hidden" />
	    			<input type="hidden" id="idsDetail" name="ids" value="" />
	    			<div class="search_condition">
	    				<p>
							<b>当前excel：</b>
	                		<b style="color: red;">${xls.name}</b>
						</p>
    					<%-- <p>
							<b>对象是否映射数据库表</b>
	                		<c:if test="${xls.type==0}">
	                			<b style="color: red;">否</b>
	                		</c:if>
	                		<c:if test="${xls.type==1}">
	                			<b style="color: red;">是</b>
	                		</c:if>
						</p>
    					<p>
							<b>字段名称</b>
	                		<input id="xls_query" name="name" type="text" placeholder="请输入字段名称" class="easyui-validatebox span2" maxlength="32" value=""/>
						</p>
						<p class="btnSear">
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="detailSearchFun();">查询</a>
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="detailCleanFun();">清空</a>
		                </p> --%>
					</div>
	    		</form>
    		</div>
        <div data-options="region:'center',border:false">
        	<form id="detailListForm" method="post">
        	</form>
            <table id="detailDataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    <div id="detailToolbar" style="display: none;">
        	<a onclick="add();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">增加字段</a>
            <a onclick="detailDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-line'">删除字段</a>
            <c:if test="${xls.type==1}">
       			<!-- <a onclick="mappedOK();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">字段映射完成</a> -->
       		</c:if>
    </div>
