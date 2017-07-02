<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#roleType').combobox({
    	onSelect: function(record){
    		if(record.value=="xls"){
    			$("#objectId").combobox('clear');
    			var url = "${path}/pages/xls/getList";
        		$('#objectId').combobox('reload',url);  
    		}else if(record.value=="txt"){
    			$("#objectId").combobox('clear');
    			var url = "${path}/pages/txt/getList";
        		$('#objectId').combobox('reload',url);
    		}
    	}
    });
});
function add(cid) {
    $('#roleAddForm').form('submit',{
        url : '${path}/pages/importRole/taskSave',
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
        <form id="roleAddForm" method="post">
    		<input id="id" name="id" type="hidden" value="${role.id}"/>
            <table class="grid">
            	<tr>
                    <td>任务执行周期</td>
                	<td>
                		<input id="cronTask" name="cronTask" type="text" placeholder="请输入执行周期" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>