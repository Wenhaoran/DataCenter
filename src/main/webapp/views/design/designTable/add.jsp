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
    		<input id="dbId" name="dbId" type="hidden" value="${dbId}"/>
            <table class="grid">
                <tr>
                    <td><font color='red'>*</font>方案名称</td>
                	<td>
                		<input id="tableName" name="name" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>方案表选择</td>
                	<td>
                		<select id="table_table" name="tableName" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
			    			<option value="">-请选择-</option>
			    			<c:forEach items="${tableList}" var="table">
								<option value="${table.tableName}">${table.tableName}：${table.comments}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>描述</td>
                	<td>
                		<input id="tableComments" name="comments" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="256" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>java实体类名称</td>
                	<td>
                		<input id="tableClassName" name="className" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>