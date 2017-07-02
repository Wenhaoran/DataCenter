<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	
});
function addDetail(cid) {
    $('#columnAddForm').form('submit',{
        url : '${path}/pages/xlscolumn/add',
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
        <form id="columnAddForm" method="post">
    		<input id="xlsId" name="xlsId" type="hidden" value="${xlsId}"/>
            <table class="grid">
            	<tr>
                    <td>字段描述</td>
                	<td>
                		<input id="columnName" name="name" type="text" placeholder="请输入字段描述" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>字段名称</td>
                	<td>
                		<input id="columnCode" name="code" type="text" placeholder="请输入字段名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>