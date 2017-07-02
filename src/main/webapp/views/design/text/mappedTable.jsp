<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#xlsDbId').combobox({
		onClick:function(record){
			$("#xlsDbtable").combobox('clear');
			var url = "${path}/pages/dbconn/getTableList?dbId="+record.value;
			$("#xlsDbtable").empty();
    		$('#xlsDbtable').combobox('reload',url);  
    	}
    });
	
	$('#xlsDbtable').combobox({
		onClick:function(record){
			if($('#xlsDbId option:selected').val()==""){
				easyui_error("请先选择被关联表");
			}
		},
    });
});
function add(cid) {
    $('#xlsectAddForm').form('submit',{
        url : '${path}/pages/xls/mappedsave',
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
    		<input id="id" name="id" type="hidden" value="${xls.id}"/>
            <table class="grid">
                <tr>
                    <td><font color='red'>*</font>映射源</td>
                	<td>
                		<select id="xlsDbId" name="dbId" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
			    			<option value="">-请选择-</option>
			    			<c:forEach items="${connList}" var="conn">
								<option value="${conn.id}">${conn.name}：${conn.code}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>映射表</td>
                	<td>
                		<select id="xlsDbtable" name="dbTable" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'tableName',textField:'tableName'">
						 </select>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>