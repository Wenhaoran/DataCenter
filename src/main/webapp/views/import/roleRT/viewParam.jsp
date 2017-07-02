<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	var rtId = '${rt.id}';
    detailDataGrid = $('#detailDataGrid').datagrid({
        url : '${path}/pages/importRt/dataGridByRtId?rtId='+rtId,
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

</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false">
        <table id="detailDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>