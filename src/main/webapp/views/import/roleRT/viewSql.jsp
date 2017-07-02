<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	var rtSql = '${rt.sql}';
	$("#sqlStr").val(rtSql);
});

function viewSql(cid) {
    $('#viewSqlForm').form('submit',{
        url : '${path}/pages/importRt/updateSql',
        success : function(result) {
            result = $.parseJSON(result);
            if (result.success) {
            	easyui_alert(result.msg);
            	$("#"+cid).dialog("close");
            } else {
            	easyui_error(result.msg);
            }
        }
    });
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="viewSqlForm" method="post">
    		<input id="rtId" name="id" type="hidden" value="${rt.id}"/>
            <table class="grid">
            	<tr id="rtSql">
                    <td>sql语句</td>
                	<td>
                		<textarea id="sqlStr" rows="5" cols="150" name="sql"  class="textarea easyui-textbox"></textarea>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>