<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var exportId = '${export.id}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/echarts/dataGridByExportId?exportId='+exportId,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            idField : 'id',
            sortName : 'a.inserttime',
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
           	},{
                width : '10%',
                title : '主键',
                field : 'id',
                sortable : true,
                align : 'center',
            	hidden : true
            } , {
                width : '10%',
                title : '图表名称',
                field : 'echartName',
                sortable : false,
                align : 'center',
                hidden : false,
               
            } , {
                width : '10%',
                title : '图表编码',
                field : 'echartCode',
                sortable : false,
                align : 'center',
                hidden : false
            } , {
                field : 'action2',
                title : '操作',
                width : '25%',
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                   	str += $.formatString('<a hidden href="javascript:void(0)" class="echartExport-easyui-linkbutton-mustColumn" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="mustColumn(\'{1}\',\'{2}\');" >必填信息</a>', index, row.id, index);
                   	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                   	str += $.formatString('<a hidden href="javascript:void(0)" class="echartExport-easyui-linkbutton-jsonMap" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="jsonMap(\'{1}\',\'{2}\');" >数据映射</a>', index, row.id, index);
                   	if(row.status=='0'){
                   		str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                       	str += $.formatString('<a hidden href="javascript:void(0)" class="echartExport-easyui-linkbutton-generate" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="generate(\'{1}\',\'{2}\');" >生成图表</a>', index, row.id, index);
                   	}
					return str;
                }
            }
			]],
            onLoadSuccess:function(data){
                $('.echartExport-easyui-linkbutton-mustColumn').linkbutton({text:'必填信息',plain:true,iconCls:'icon-dc-view'});
                $('.echartExport-easyui-linkbutton-jsonMap').linkbutton({text:'数据映射',plain:true,iconCls:'icon-dc-account'});
                $('.echartExport-easyui-linkbutton-generate').linkbutton({text:'生成图表',plain:true,iconCls:'icon-dc-book'});
            },
            toolbar : '#detailToolbar'
        });
    });
    
    function generate(id,index) {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/generate?rsId="+id;
    	
    	openViewDialog(url, 500, 900, detailDataGrid).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function mustColumn(id,index) {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/addMustColumn?exportId="+exportId+"&rsId="+id;
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function jsonMap(id,index) {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/addJsonMap?exportId="+exportId+"&rsId="+id;
    	
    	openViewDialog(url, 500, 900, detailDataGrid).dialog('open').dialog('setTitle', '查看数据映射');
    }

    function addEChart() {
    	var exportId = '${export.id}';
    	var url="${path}/pages/echarts/addAttr?exportId="+exportId;
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加图表');
    }
    
    function detailDeleteFun() {
    	var checkedItems = detailDataGrid.datagrid('getChecked');
    	if(checkedItems.length == 0){
    		easyui_alert('请至少选择一条记录！');
    		return false;
    	}
    	var ids = [];
    	$.each(checkedItems, function(index, item){    
    		ids.push(item.id);
    	});
//     	$('#idsDetail').val(ids);
        progressLoad();
        
        $.post('${path}/pages/echarts/delete', {
            ids : ids,
            exportId :'${export.id}'
        }, function(result) {
            if (result.success) {
            	easyui_alert(result.msg);
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
    <div class="easyui-layout" data-options="fit:true,border:true,title:'必填字段列表'">
   	<div data-options="region:'north',fit:false,border:false,title:'查询条件',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
    		<form id="detailSearchForm">
    			<div class="search_condition">
    				<p>
						<b>报表名称：</b>
                		<b style="color: red;">${export.name}</b>
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
       	<a onclick="addEChart();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加图表</a>
        <a onclick="detailDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">删除图表</a>
    </div>
