<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var roleId = '${role.id}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/importDetail/dataGridByRoleId?roleId='+roleId,
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
                title : '映射类型',
                field : 'mappedType',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			if(value=="0"){
         				return "文件映射:属性  --> 表字段";
         			}else if(value=="1"){
         				return "主从映射:表字段  --> 表映射";
         			}else if(value=="2"){
         				return "匹配映射";
         			}
         		}
            } , 
            {
                width : '10%',
                title : '何处来？',
                field : 'objName',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '10%',
                title : '来源字段',
                field : 'columnName',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '10%',
                title : '去何处？',
                field : 'tableName',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			return "表："+value;
         		}
            } , 
            {
                width : '10%',
                title : '映射字段',
                field : 'tableColumn',
                sortable : false,
                align : 'center',
                hidden : false
            }]],
            onLoadSuccess:function(data){
            },
            toolbar : '#detailToolbar'
        });
    });

    function addDetail(type) {
    	var roleId = '${role.id}';
    	var url="${path}/pages/importDetail/addPage?roleId="+roleId+"&mappedType="+type;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	
    	openRSEditDialog(url, 500, 900,dataGrid, detailDataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '添加映射');
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
        $.post('${path}/pages/importDetail/delete', {
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

    function mappedOK(){
    	var roleId = '${role.id}';
    	$.ajax({
	        type : "post",  
	    	url : "${path}/pages/importRole/mappedOK",  
	    	data:{roleId:roleId},
	    	success : function(result) {
	    		var data = JSON.parse(result);
                 if (data.success) {  
                	easyui_alert(data.msg);
                } else {
                	easyui_error(data.msg);
                } 
                 detailDataGrid.datagrid('reload');
                 dataGrid.datagrid('reload');
            }
	     });
    }
    
</script>
</head>
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'进货发票明细列表'">
			<div data-options="region:'north',fit:false,border:false,title:'查询条件',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
	    		<form id="detailSearchForm">
	    			<input id='"roleId"' name="roleId" value='${role.id}' type="hidden" />
	    			<input type="hidden" id="idsDetail" name="ids" value="" />
	    			<div class="search_condition">
	    				<p>
							<b>规则名称：</b>
	                		<b style="color: red;">${role.name}</b>
						</p>
						<p>
							<b>映射是否完成：</b>
	                		<c:if test="${role.importType!=0&&role.importType!=1}">
	                			<b style="color: red;">是</b>
	                		</c:if>
	                		<c:if test="${role.importType==0||role.importType==1}">
	                			<b style="color: red;">否</b>
	                		</c:if>
						</p>
    					<!-- <p>
							<b>字段</b>
	                		<input id="object_query" name="code" type="text" placeholder="请输入账户名称" class="easyui-textbox span2" maxlength="32" value=""/>
						</p> -->
						<!--<p class="btnSear">
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-search',plain:true" onclick="detailSearchFun();">查询</a>
		                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-cancel',plain:true" onclick="detailCleanFun();">清空</a>
		                </p>-->
					</div>
	    		</form>
    		</div>
        <div data-options="region:'center',border:false">
            <table id="detailDataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    <div id="detailToolbar" style="display: none;">
        	<a onclick="addDetail(0);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加文件映射</a>
        	<a onclick="addDetail(1);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加主从映射</a>
        	<a onclick="addDetail(2);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加匹配映射</a>
            <a onclick="delField();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">删除映射明细</a>
            <a onclick="mappedOK();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-save'">映射完成</a>
    </div>
