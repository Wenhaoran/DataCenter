<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

$(function() {
	
});

function modify(cid) {
    $('#objectAddForm').form('submit',{
        url : '${path}/pages/obj/edit',
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
        <form id="objectAddForm" method="post">
    		<input id="objectId" name="id" type="hidden" value="${object.id}"/>
            <table class="grid">
            	<tr>
                    <td><font color='red'>*</font>编号</td>
                	<td>
                		<input id="objectCode" name="code" type="text" placeholder="请输入资源编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.code}"/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>名称</td>
                	<td>
                		<input id="objectCode" name="name" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.name}"/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>描述</td>
                	<td>
                		<input id="objectLink" name="description" type="text" placeholder="请输入描述" class="easyui-textbox span2" data-options="required:true" maxlength="100" value="${object.description}"/>
					</td>
                </tr>
                <c:if test="${object.type == 2}">
					<tr>
                    <td><font color='red'>*</font>对象表名称</td>
                	<td>
                		<input id="objectTableName" name="tableName" type="text" readonly="readonly" class="easyui-textbox span2" data-options="required:true" maxlength="100" value="${object.tableName}"/>
					</td>
                </tr>
				</c:if>
            </table>
        </form>
    </div>
</div>