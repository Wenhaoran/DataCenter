<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
function add(cid) {
    $('#tbaseReportAddForm').form('submit',{
        url : '${path}/pages/export/add',
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
        <form id="tbaseReportAddForm" method="post">
    		<input id="id" name="id" type="hidden"/>
    		<input id="exportSql" name="sql" type="hidden" value="${sql}"/>
    		<input id="exportDbId" name="dbId" type="hidden" value="${dbId}"/>
            <table class="grid">
                <tr>
                    <td><font color='red'>*</font>报表名称</td>
                	<td>
                		<input id="exportName" name="name" type="text" placeholder="请输入报表名称" class="easyui-validatebox span2" data-options="required:true" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>