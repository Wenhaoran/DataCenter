<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function modify(cid) {
    $('#tableAddForm').form('submit',{
        url : '${path}/pages/des/edit',
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
        	<input id="id" name="id" type="hidden" value="${table.id}"/>
            <table class="grid">
                <tr>
                    <td><font color='red'>*</font>方案名称</td>
                	<td>
                		<input id="tableName" name="name" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${table.name}"/>
					</td>
                </tr>
                <tr>
                    <td><b style="color: red;">方案表不能更改</b></td>
                	<td>
                		<input id="tabletabName" name="tableName" readonly="readonly" type="text" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${table.tableName}"/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>描述</td>
                	<td>
                		<input id="tableComments" name="comments" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="256" value="${table.comments}"/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>java实体类名称</td>
                	<td>
                		<input id="tableClassName" name="className" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${table.className}"/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>