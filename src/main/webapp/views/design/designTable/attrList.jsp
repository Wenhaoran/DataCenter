<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

$(function() {
	var dbId= '${dbId}';
    $('#pId').combotree({
        url : '${path }/pages/obj/tree?dbId='+dbId+'&islist=true',
        parentField : 'pId',
        lines : true,
        panelHeight : '300'
    });
});

function add(cid) {
    $('#tableAddForm').form('submit',{
        url : '${path}/pages/des/add',
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
        <form id="tableAddForm" method="post">
    		<input id="tableId" name="tableId" type="hidden" value="${tableId}"/>
            <table class="grid">
                
            </table>
        </form>
    </div>
</div>