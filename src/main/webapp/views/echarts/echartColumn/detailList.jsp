<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
	var oldIndex;
    var detailDataGrid;
    $(function() {
    	var echartId = '${echarts.id}';
        detailDataGrid = $('#detailDataGrid').datagrid({
            url : '${path}/pages/echartColumn/dataGridByEchartId?echartId='+echartId,
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
                title : '字段名称',
                field : 'name',
                sortable : false,
                align : 'center',
                hidden : false
            } , {
                width : '10%',
                title : '字段编码',
                field : 'code',
                sortable : false,
                align : 'center',
                hidden : false
            }  , {
                width : '10%',
                title : '默认值',
                field : 'type',
                sortable : false,
                align : 'center',
                hidden : false
            } , {
                field : 'action2',
                title : '操作',
                width : '25%',
                align : 'center',
                formatter : function(value, row, index) {
                    /* var str = '';
                   	 str += $.formatString('<a hidden href="javascript:void(0)" class="echartColumn-easyui-linkbutton-edit" data-row="column_edit_{0}" data-options="plain:true,iconCls:\'icon-dc-edit\'" onclick="editColumn(\'{1}\',\'{2}\');" >编辑</a>', index, row.id, index);
					return str; */
                }
            }
			]],
            onLoadSuccess:function(data){
                //$('.echartColumn-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'icon-dc-edit'});
            },
            toolbar : '#detailToolbar'
        });
    });

    function addEERSFun() {
    	var echartId = '${echarts.id}';
    	var url="${path}/pages/echartColumn/addAttr?echartId="+echartId;//?invoiceId="+'${purchInvoice.id}'+"&billType=1
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
			addDetail(cid);
		}).dialog('open').dialog('setTitle', '添加字段');
    }
    
    function editColumn(id,index){
    	var url="${path}/pages/echartColumn/editPage?id="+id;
    	
        openEditDialog(url, 500, 900, detailDataGrid, function(cid) {
        	modify(cid);
		}).dialog('open').dialog('setTitle', '更改字段');
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
        
        $.post('${path}/pages/echartColumn/delete', {
            ids : ids,
            echartId :'${echarts.id}'
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
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'必填字段列表'">
   	<div data-options="region:'north',fit:false,border:false,title:'查询条件',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
    		<form id="detailSearchForm">
    			<div class="search_condition">
    				<p>
						<b>图表名称：</b>
                		<b style="color: red;">${echarts.name}</b>
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
        	<a onclick="addEERSFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-add'">增加字段</a>
            <a onclick="detailDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-del'">删除字段</a>
    </div>
