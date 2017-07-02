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
    $('#objectAddForm').form('submit',{
        url : '${path}/pages/obj/add',
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
    		<input id="type" name="type" type="hidden" value="${type}"/>
    		<input id="dbId" name="dbId" type="hidden" value="${dbId}"/>
            <table class="grid">
            	<tr>
                    <td><font color='red'>*</font>编号</td>
                	<td>
                		<input id="objectCode" name="code" type="text" placeholder="请输入资源编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>名称</td>
                	<td>
                		<input id="objectCode" name="name" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>描述</td>
                	<td>
                		<input id="objectLink" name="description" type="text" placeholder="请输入描述" class="easyui-textbox span2" data-options="required:true" maxlength="100" value=""/>
					</td>
                </tr>
                <c:if test="${type == 2}">
					<tr>
                    <td><font color='red'>*</font>对象表名称</td>
                	<td>
                		<input id="objectTableName" name="tableName" type="text" placeholder="请输入表明" class="easyui-textbox span2" data-options="required:true" maxlength="100" value=""/>
					</td>
                </tr>
				</c:if>
                <tr>
                    <td><font color='red'>*</font>上级资源</td>
	                <td colspan="3"><select id="pId" name="pId" style="width: 200px; height: 29px;"></select>
	                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>