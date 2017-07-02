<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/basejs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>SQL报表管理</title>
    <script type="text/javascript">
            
    $(document).ready(function(){
	    //定义账簿的事件
	    $('#readSQLexportId').combobox({
	        onChange: function (n, o) {        	
	        	getSQL();  
	        }
	    });
    });
    
    function getSQL(){
    	//校验存在
    	if($("#readSQLexportId").combobox("getValue") != ""){
    		$.ajax({ 
               type: "post", 
               url: "${path }/pages/export/getReportSql", 
               data: {exportId:$("#readSQLexportId").combobox("getValue")},
               async: false,
               dataType: "json", 
               success: function (result) { 
            	   var success = result.success;
            	   if(success){
            		   	$("#sqlStr").val(result.msg);
    	   	   		}else{		   	   			
    	   	   			easyui_error(result.msg);	   	   				   	   			
    	   	   		}     
               }, 
               error: function (XMLHttpRequest, textStatus, errorThrown) { 
            	   	   easyui_error(errorThrown); 
                       return false;
               } 
        	});
    	}else{
    		$("#sqlStr").val("");
    	}
    }
    
    
    
    function searchFun() {
    	if($("#exportDbId").val()==""){
    		easyui_alert("请选择 数据源!");
    		return false;
    	}
    	if($("#sqlStr").val()==""){
    		easyui_error("请输入SQL语句!");
    		return false;
    	}
    	// progressLoad();
    	
    	$('#listLay').layout('remove','south');
    	var dbId = $("#exportDbId").val();
    	var sql = $("#sqlStr").val();
    	var uuid = "sql"+Date.parse(new Date());
    	$('#listLay').layout('add',{    
    	    region: 'south',    
    	    width: '100%',
    	    height:'60%',
    	    href:'${path}/pages/export/viewReportPage?id='+uuid+'&dbId='+dbId+'&sql='+sql,
    	    title: '报表数据',
    	    split: true
    	});
         
    }
    
    function cleanFun() {
       $('#searchForm input').val('');
	   $('#searchForm select').combobox({
        	onLoadSuccess: function (row, data) {
        		$(this).combobox('setValue', '');
        	}
        });
	   $('#sqlStr').val('');
	   dataGrid.datagrid('load', {});
    }
    
    function saveSqlFun(){    
    	if($("#exportDbId").val()==""){
    		easyui_alert("请选择 数据源!");
    		return false;
    	}
    	if($("#sqlStr").val()==""){
    		easyui_alert("请输入SQL语句!");
    		return false;
    	}
    	var dbId = $('#exportDbId').val();
    	var sql = $('#sqlStr').val();
       	var url="${path}/pages/export/addPage?dbId="+dbId+"&sql="+sql;
       	
       	var id = "dialogIdsql";
       	
    	$(document.body).append("<div id='"+id+"'></div>");
    	var mdialog = $('#'+id).dialog({
    		href:url,
    		width:400,
    		height:300,
    		border:false,
    		cache:false,
    		collapsible:false,
    		maximizable:false,
    		resizable:false,
    		modal:true,
    		closed:true,
    		buttons:[ {
    			text:'保存',
    			iconCls:'icon-ok',
    			handler:function() {
    				add(id);
    			}
    		}, {
    			text:'取消',
    			iconCls:'icon-cancel',
    			handler:function() {
    				$("#"+id).dialog("close");
    			}
    		} ],
    		onClose:function() {
    			$("#"+id).dialog('destroy');
    		}
    	});
    	mdialog.dialog('open').dialog('setTitle', '保存自定义sql');
    }
    </script>
</head>
<body>	
    <div id=listLay class="easyui-layout" data-options="fit:true,border:true,title:'数据列表'" >
	    <div data-options="region:'north',border:false" style="height: 200px; overflow: hidden;background-color: #fff">
	        <form id="searchForm">
	            <table>
	            	<tr>
	                    <th>数据源:</th>
	                    <td>
	                    	<select id="exportDbId" name="dbId" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:140,height:29,editable:false,panelHeight:'140',required:true">
	                    		<option value="">-请选择-</option>
	                    		<c:forEach items="${connList}" var="conn">
	                    			<option value="${conn.id}">${conn.name}</option>
	                    		</c:forEach>
                    		</select>	                    
	                    </td>	                    
	                </tr>
	            	<tr>
	                    <th>报表列表:</th>
	                    <td>
	                    	<select id="readSQLexportId" name="exportId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'140',required:true">
	                    		<option value="">-请选择-</option>
	                    		<c:forEach items="${exportList}" var="export">
	                    			<option value="${export.id}">${export.name}</option>
	                    		</c:forEach>
                    		</select>	                    
	                    </td>	                    
	                </tr>
	                <tr>
	                    <th>SQL语句:</th>
	                    <td>
	                    	<textarea id="sqlStr" rows="5" cols="150" name="sqlStr"  class="textarea easyui-validatebox"></textarea>
	                    </td>	                    
	                </tr>
	                <tr>
	                    <td align="center" colspan="2" align="center">
	                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
	                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
	                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveSqlFun();">保存</a>
	                    </td>                   
	                </tr>
	            </table>
	        </form>
	    </div>
        
    </div>
</body>
</html>