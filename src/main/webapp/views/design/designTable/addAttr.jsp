<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<body>	
<script type="text/javascript">
    var datagrid;
    $(function() {
    	var url = '${path}/pages/column/attrDataGrid?tableId=${tableId}';
    	datagrid = $('#datagrid').datagrid({
    		   url : url,
    		   striped : true,
               rownumbers : true,
               pagination : true,
               singleSelect : false,
               idField : 'id',
               sortName : 'id',
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
                    width : '5%',
                    title : '主键',
                    field : 'id',
                    sortable : true,
                    align : 'center',
                	hidden : true
                } , 
   				{
                   width : '15%',
                   title : '字段名',
                   field : 'name',
                   sortable : true,
                   align : 'center',
                   hidden : false
               } , 
   				{
                   width : '20%',
                   title : '字段描述',
                   field : 'comments',
                   sortable : true,
                   align : 'center',
                   hidden : false
               } , 
   				{
                   width : '24%',
                   title : '字段类型',
                   field : 'type',
                   sortable : true,
                   align : 'center',
                   hidden : false
               }   , 
   				{
                   width : '18%',
                   title : 'java类型',
                   field : 'javaType',
                   sortable : true,
                   align : 'center',
                   hidden : false
               }   , 
   				{
                   width : '18%',
                   title : 'Java名称',
                   field : 'javaField',
                   sortable : true,
                   align : 'center',
                   hidden : false
               }  
               ]],
            toolbar : '#toolbarInvoiceDetail'
        });
    });
    
    function addAttr() {
    	var checkedItems = datagrid.datagrid('getChecked');
        if(checkedItems.length == 0){
        	easyui_alert("请至少选择一条记录");
        }else{
        	//datagrid 必须设置 id 列，而且，只能获取id 列的值，我刚才测试，直接获取 name 列的值，获取结果是错误的，因此，在后台 把name 列的值，复制到id 列 上来使用。
        	var ids = '';
        	$.each(checkedItems, function(index, item){    
        		ids = ids+item.id+',';    		
        	});
        	progressLoad();
            $.post('${path}/pages/column/attr', {
         	   names : ids,
         	   tableId:'${tableId}'
            }, function(result) {
                if (result.success) {
                	easyui_alert(result.msg+"请手动关闭当前窗口");
                	datagrid.datagrid('reload');
                	dataGrid.datagrid('reload');
                }else{
             		easyui_alert(result.msg);
                    datagrid.datagrid('reload');
                }
                progressClose();
            }, 'JSON');
            //$("#dialogId").dialog("close");
    	}
    }
</script>
    <div   class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'center',border:false">
            <table id="datagrid" data-options="fit:true,border:false"></table>
        </div>
	    <div id="toolbarInvoiceDetail" style="display: none;">
	        <a onclick="addAttr();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">设置属性</a>
	    </div>
    </div>
    
</body>
