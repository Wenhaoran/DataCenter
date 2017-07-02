<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
  

<body>	

<script type="text/javascript">
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${r"${staticPath }"}/pages/export/reportDataGrid?id='+$("#exportId").val()+'&sql='+$("#sql").val()+'&dbId='+$("#dbId").val(),
            /*
            data:{
            	id:$("#exportId").val(),
            	sql:$("#sql").val(),
            	dbId:$("#dbId").val()
            },
            */
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            fit : true,
            fitColumns:true,
            pageSize : 100,
            pageList : [ 20, 50, 100, 200, 300, 400, 500 ],
            
            columns: [[
            ${columns}
             ] ],
            onLoadSuccess:function(data){
               
            },
            toolbar : '#datatoolbar'
        });
    });

         
    function exportFun(){
    	var url = '${r"${staticPath }"}/pages/export/exportReport';
    	window.location= '${r"${staticPath }"}/pages/export/exportReport?id='+$("#exportId").val()+'&sql='+$("#sql").val()+'&dbId='+$("#dbId").val();
    	//提交
        /* $.ajax({ 
            url : url,            
            type: "POST",  
            data:{
            	id:$("#exportId").val(),
            	sql:$("#sql").val(),
            	dbId:$("#dbId").val()
            },
            async: false,  
            dataType: "json",              
            cache: false,  
            success : function(result) {
                progressClose();                
                if (result.success) {                	    
                    window.location.href = basePath+'/common/download?filename='+result.msg;
                } else {
                	easyui_error(result.msg);
                }
            }
        });*/
    }
    </script>
    <div id="reportDetailLay" class="easyui-layout" data-options="fit:true,border:true,title:'数据列表'" >
	    <div data-options="region:'center',border:false"  >
            <table id="dataGrid" data-options="fit:true,border:false"></table>
        </div>
    </div>
    
    <div id="datatoolbar" style="display: none;"> 
    	<input type="hidden" id="exportId" value="${r"${exportId}"}">
        <input type="hidden" id="sql" value="${r"${sql}"}">
        <input type="hidden" id="dbId" value="${r"${dbId}"}">
        <a onclick="exportFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dc-download'">导出</a>
    </div>
    
</body>
