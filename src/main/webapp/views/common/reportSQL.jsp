<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>SQL报表管理</title>
    <script type="text/javascript">
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/common/reportDataGrid?timestamp='+$("#timestamp").val(),
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            fit : true,
            fitColumns:true,
            pageSize : 20,
            pageList : [ 20, 50, 100, 200, 300, 400, 500 ],
            
            columns: [[
             ] ],
            onLoadSuccess:function(data){
               
            },
            toolbar : '#toolbar'
        });
    });

         
    function exportFun(){
    		
    }
    </script>
</head>
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'数据列表'" >
         <input type="hidden" id="timestamp" value="${timestamp}">
	    <div data-options="region:'center',border:false"  >
            <table id="dataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    
    <div id="toolbar" style="display: none;"> 
        
        <a onclick="exportFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-export'">导出</a>
        
    </div>
    
</body>
</html>