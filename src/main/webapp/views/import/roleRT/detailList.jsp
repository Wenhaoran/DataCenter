<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var roleId = '${role.id}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/importRt/dataGridByRoleId?roleId='+roleId,
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
                title : '数据源',
                field : 'dbName',
                sortable : false,
                align : 'center',
                hidden : false
            } , 
            {
                width : '10%',
                title : '源表',
                field : 'dbTable',
                sortable : false,
                align : 'center',
                hidden : false
            },{
                width : '10%',
                title : '执行顺序',
                field : 'insertSort',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			if(value==null){
         				return "执行顺序 默认是0";
         			}else{
         				return value;
         			}
         		}
            } , 
            {
                width : '18%',
                title : '当前表插入sql',
                field : 'sql',
                sortable : false,
                align : 'center',
                hidden : false,
                formatter : function(value, row, index) {
         			if(value==null){
         				return "<b style='color: red;width: 400px'>在规则明细（字段映射）完成后，会创建插入sql</b>";
         			}else{
         				return value;
         			}
         		}
                
            } , 
            {
                field : 'action',
                title : '操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
					str += $.formatString('<a href="javascript:void(0)" data-row="sql_{0}" class="roleRT-easyui-linkbutton-sql" data-options="plain:true,iconCls:\'icon-dc-sql\'" onclick="viewSqlPage(\'{1}\',\'{2}\');" >查看sql</a>', index, row.id, index);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" data-row="param_{0}" class="roleRT-easyui-linkbutton-param" data-options="plain:true,iconCls:\'icon-dc-param\'" onclick="viewParamPage(\'{1}\',\'{2}\');" >查看参数</a>', index, row.id, index);
					return str;
                }
            }  ]],
            onLoadSuccess:function(data){
            	$('.roleRT-easyui-linkbutton-sql').linkbutton({text:'查看sql',plain:true,iconCls:'icon-dc-account'});
                $('.roleRT-easyui-linkbutton-param').linkbutton({text:'查看参数',plain:true,iconCls:'icon-dc-account'});
            },
            toolbar : '#detailToolbar'
        });
    });

    function viewSqlPage(id,index) {
    	var url="${path}/pages/importRt/viewSql?rtId="+id;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	
    	openEditDialog(url, 500, 900,  detailDataGrid, function(cid) {
    		viewSql(cid);
		}).dialog('open').dialog('setTitle', '更改sql');
    }
    
    function viewParamPage(id,index) {
    	var url="${path}/pages/importRt/viewParam?rtId="+id;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	openViewDialog(url, 500, 900, detailDataGrid).dialog('open').dialog('setTitle', '查看参数');
    }
    
    function addDetail() {
    	var roleId = '${role.id}';
    	var url="${path}/pages/importRt/addPage?roleId="+roleId;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	
    	openEditDialog(url, 500, 900,  detailDataGrid, function(cid) {
			add(cid);
		}).dialog('open').dialog('setTitle', '添加映射表');
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
        $.post('${path}/pages/importRt/delete', {
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
        	<a onclick="addDetail();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加映射表</a>
            <!-- <a onclick="delField();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">删除映射 表</a> -->
    </div>
