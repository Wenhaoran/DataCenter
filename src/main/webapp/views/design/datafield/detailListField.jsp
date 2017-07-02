<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var objectId = '${object.id}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/field/dataGridByObjectId?objectId='+objectId,
            striped : true,
            rownumbers : false,
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
                width : '10%',
                title : '字段名称',
                field : 'code',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '10%',
                title : '字段描述',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '10%',
                title : '字段类型',
                field : 'type',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			if(value=="ref"){
         				return "外键关联";
         			}else{
         				return value;
         			}
         		}
            } , 
            {
                width : '10%',
                title : '外键关联表',
                field : 'refTable',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '10%',
                title : '外键关联 字段',
                field : 'refField',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {field : 'action',title : '操作',width : '19%',
	         	formatter : function(value, row, index) {
	              var str = '';
	                      str += $.formatString('<a href="javascript:void(0)" class="field-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
	              return str;
	         	}
 			 }]],
            onLoadSuccess:function(data){
            	$('.field-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-dc-edit'});
            },
            toolbar : '#detailToolbar'
        });
    });

    function addField() {
    	var objectId = '${object.id}';
    	var url="${path}/pages/field/addPage?objectId="+objectId;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function delField() {
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
        $.post('${path}/pages/field/delete', {
            ids : ids,
        }, function(result) {
            if (result.success) {
            	easyui_alert(result.msg);
            	for(var i = checkedItems.length; i > 0; i--){
            		var index = detailDataGrid.datagrid('getRowIndex', checkedItems[i-1]);
            		detailDataGrid.datagrid('deleteRow', index);
            	}
            	progressClose();
                detailDataGrid.datagrid('reload');
            }
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
    
    function editFun(id){
		var url="${path}/pages/field/editPage?id="+id;
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			edit(cid);
		}).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function createTable(){
    	var objectId = '${object.id}';
    	$.ajax({
	        type : "post",  
	    	url : "${path}/pages/obj/create",  
	    	data:{id:objectId},
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
    <div class="easyui-layout" data-options="fit:true,border:true,title:'进货发票明细列表'">
			<div data-options="region:'north',fit:false,border:false,title:'查询条件',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
	    		<form id="detailSearchForm">
	    			<input id='"objectId"' name="objectId" value='${object.id}' type="hidden" />
	    			<input type="hidden" id="idsDetail" name="ids" value="" />
	    			<div class="search_condition">
	    				<p>
							<b>表名称：</b>
	                		<b style="color: red;">${object.name}</b>
						</p>
						<p>
							<b>是否已经创建表：</b>
	                		<c:if test="${object.status==0}">
	                			<b style="color: red;">否</b>
	                		</c:if>
	                		<c:if test="${object.status!=0}">
	                			<b style="color: red;">是</b>
	                		</c:if>
						</p>
    					<p>
							<b>字段</b>
	                		<input id="object_query" name="code" type="text" placeholder="请输入账户名称" class="easyui-textbox span2" maxlength="32" value=""/>
						</p>
						<p class="btnSear">
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-search',plain:true" onclick="detailSearchFun();">查询</a>
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-cancel',plain:true" onclick="detailCleanFun();">清空</a>
		                </p>
					</div>
	    		</form>
    		</div>
        <div data-options="region:'center',border:false">
            <table id="detailDataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    <div id="detailToolbar" style="display: none;">
    		<c:if test="${object.status==0}">
       			<a onclick="createTable();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">创建表</a>
       		</c:if>
       		<c:if test="${object.status!=0}">
       			<a onclick="createTable();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">更新表</a>
       		</c:if>
        	<a onclick="addField();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加字段</a>
            <a onclick="delField();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">删除字段</a>
    </div>
