<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	var dbId= ${dbId}
	$('#dbId').combobox({
		onClick:function(record){
			$("#dbTable").combobox('clear');
			var url = "${path}/pages/dbconn/getTableList?dbId="+record.value;
			$("#dbTable").empty();
    		$('#dbTable').combobox('reload',url);  
    	}
    });
});
function add(cid) {
    $('#xlsectAddForm').form('submit',{
        url : '${path}/pages/importRt/add',
        onSubmit : function() {
            progressLoad();
            isValid = $(this).form('validate');
            if (!isValid) {
                progressClose();
            }
            return isValid;
        },
        success : function(result) {
            progressClose();
            result = $.parseJSON(result);
            if (result.success) {
            	easyui_alert(result.msg);
            	$("#"+cid).dialog("close");
				$("#"+cid).dialog("destroy");
            } else {
            	easyui_error(result.msg);
            }
        }
    });
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="xlsectAddForm" method="post">
    		<input id="importRoleId" name="importRoleId" type="hidden" value="${role.id}"/>
            <table class="grid">
            	
            	<tr id="dbIdSelect">
                    <td>数据源</td>
                	<td>
                		<select id="dbId" name="dbId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <c:forEach items="${connList}" var="conn">
								<option value="${conn.id}">${conn.name}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td>表</td>
                	<td>
                		<select id="dbTable" name="dbTable" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'tableName',textField:'tableName'"></select>
					</td>
                </tr>
                
            </table>
        </form>
    </div>
</div>