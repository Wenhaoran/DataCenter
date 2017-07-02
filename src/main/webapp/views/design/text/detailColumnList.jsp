<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var txtId = '${txt.id}';
    	var txttype = '${txt.type}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/txtcolumn/dataGridByTxtId?txtId='+txtId,
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
            }]],
            onLoadSuccess:function(data){
                $('.column-easyui-linkbutton-table').linkbutton({text:'映射表字段',plain:true,iconCls:'icon-dc-fileUpload'});
            },
            toolbar : '#detailToolbar'
        });
    });
    
    function add() {
    	var txtId = '${txt.id}';
    	var url="${path}/pages/txtcolumn/addPage?txtId="+txtId;
    	
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
        progressLoad();
        var txtId = '${txt.id}';
        $.post('${path}/pages/txtcolumn/deleteColumn', {
            ids : ids,
            txtId : txtId
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
    
</script>
</head>
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'进货发票明细列表'">
			<div data-options="region:'north',fit:false,border:false,title:'查询条件',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
	    		<form id="detailSearchForm">
	    			<input id='"txtId"' name="txtId" value='${txt.id}' type="hidden" />
	    			<input type="hidden" id="idsDetail" name="ids" value="" />
	    			<div class="search_condition">
	    				<p>
							<b>当前text：</b>
	                		<b style="color: red;">${txt.name}</b>
						</p>
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
    </div>
