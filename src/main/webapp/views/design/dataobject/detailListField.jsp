<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var roleId = '${role.id}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/account/dataGridByRoleId?roleId='+roleId,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            idField : 'id',
            sortName : 'inserttime',
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
                width : '10%',
                title : '账户名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
			{
                width : '10%',
                title : '账户类型',
                field : 'type',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case 1:
			                 return '本地账户';
			             case 2:
			                 return 'sina账户';
			        }
         		}
            } , 
            {
                width : '10%',
                title : '角色状态',
                field : 'status',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			switch (value) {
			             case 1:
			                 return '在用';
			             case 2:
			                 return '停用';
			        }
         		}
            }
			]],
			onDblClickRow:function(index, rowData){
				if(oldIndex != null && oldIndex != undefined){
					$('#detailDataGrid').datagrid('endEdit', oldIndex);
				}
				oldIndex = index;
				$('#detailDataGrid').datagrid('beginEdit', index);
			},
			onClickRow:function(rowIndex, rowData){
				$('#detailDataGrid').datagrid('endEdit', oldIndex);
			},
            onLoadSuccess:function(data){
                $('.accountRoleRS-easyui-linkbutton-edit').linkbutton({text:'解除关联',plain:true,iconCls:'icon-dc-del'});
            },
            toolbar : '#detailToolbar'
        });
    });

    function addRARSFun() {
    	var roleId = '${role.id}';
    	var url="${path}/pages/account/roleGiveAccount?roleId="+roleId;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加账户关联');
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
        var roleId = '${role.id}';
        $.post('${path}/pages/account/deleteRARS', {
            ids : ids,
            roleId : roleId
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

    function changeRealPrice() {
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
	    			<input id='roleId' name="roleId" value='${role.id}' type="hidden" />
	    			<input type="hidden" id="idsDetail" name="ids" value="" />
	    			<div class="search_condition">
	    				<p>
							<b>当前角色：</b>
	                		<b style="color: red;">${role.name}</b>
						</p>
    					<p>
							<b>账户名称</b>
	                		<input id="role_query" name="name" type="text" placeholder="请输入账户名称" class="easyui-textbox span2" maxlength="32" value=""/>
						</p>
    					<p>
							<b>账户类型</b>
	                		<select id="type_query" name="type" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px'">
			              		<option value="">-请选择-</option>
				                <option value="1">本地账户</option>
				                <option value="2">sina账户</option>
							 </select>
						</p>
    					<p>
							<b>账户状态</b>
	                		<select id="status_query" name="status" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px'">
			              		<option value="">-请选择-</option>
				                <option value="1">在用</option>
				                <option value="2">停用</option>
							 </select>
						</p>
						<p>
							<b>账户登记</b>
	                		<select id="level_query" name="level" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px'">
			              		<option value="">-请选择-</option>
				                <option value="1">超级用户</option>
				                <option value="2">普通用户</option>
							 </select>
						</p>
						<p class="btnSear">
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-search',plain:true" onclick="detailSearchFun();">查询</a>
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-cancel',plain:true" onclick="detailCleanFun();">清空</a>
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
        	<a onclick="addRARSFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">关联账户</a>
            <a onclick="detailDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">解除关联</a>
            <a onclick="changeRealPrice();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-filter'">刷新</a>
    </div>
