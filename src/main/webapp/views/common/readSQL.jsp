<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>SQL报表管理</title>
    <script type="text/javascript">
            
    $(document).ready(function(){
	    //定义账簿的事件
	    $('#reportId').combobox({
	        onChange: function (n, o) {        	
	        	getSQL();  
	        }
	    });
    });
    
    function getSQL(){
    	//校验存在
    	if($("#reportId").combobox("getValue") != ""){
    		$.ajax({ 
               type: "post", 
               url: "${path }/tbaseReport/getReportSql", 
               data: {reportId:$("#reportId").combobox("getValue")},
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
    	
    	if($("#sqlStr").val()==""){
    		easyui_alert("请输入SQL语句!");
    		return false;
    	}
    	 progressLoad();
         $.post('${path}/tbaseReport/setupSql', {
        	 sqlStr : $("#sqlStr").val()
         }, function(result) {
             if (result.success) {
            	 $('#reportWeb').attr("src","${path}/tbaseReport/reportSQL?timestamp="+result.msg);
             }else{
            	 easyui_error(result.msg);
             }
             progressClose();
         }, 'JSON');
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
    	if($("#sqlStr").val()==""){
    		easyui_alert("请输入SQL语句!");
    		return false;
    	}
       	var url="${path}/tbaseReport/addPage";
       
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
    			iconCls:'icon-dc-ok',
    			handler:function() {
    				add(id);
    			}
    		}, {
    			text:'取消',
    			iconCls:'icon-dc-cancel',
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
    <div class="easyui-layout" data-options="fit:true,border:true,title:'数据列表'" >
	    <div data-options="region:'north',border:false" style="height: 200px; overflow: hidden;background-color: #fff">
	        <form id="searchForm">
	            <table>
	            
	            	<tr>
	                    <th>报表列表:</th>
	                    <td>
	                    	<select id="reportId" name="reportId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'140',required:true">
	                    		<option value="">-请选择-</option>
	                    		<c:forEach items="${reportlist}" var="dic">
	                    			<option value="${dic.id}">${dic.reportName}</option>
	                    		</c:forEach>
                    		</select>	                    
	                    </td>	                    
	                </tr>
	                <tr>
	                    <th>SQL语句:</th>
	                    <td>
	                    	<textarea id="sqlStr" rows="5" cols="150" name="sqlStr"  class="textarea easyui-textbox"></textarea>
	                    </td>	                    
	                </tr>
	                <tr>
	                    <td align="center" colspan="2" align="center">
	                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-search',plain:true" onclick="searchFun();">查询</a>
	                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-cancel',plain:true" onclick="cleanFun();">清空</a>
	                    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-dc-save',plain:true" onclick="saveSqlFun();">保存</a>
	                    </td>                   
	                </tr>
	            </table>
	        </form>
	    </div>
        <div data-options="region:'center',border:false"  >
           <iframe id="reportWeb" runat="server" src="#" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
        </div>
    </div>
    
   
    
</body>
</html>