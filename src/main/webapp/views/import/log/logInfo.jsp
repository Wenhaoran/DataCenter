<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
function add(cid) {
	
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="xlsectAddForm" method="post">
    		<input id="importRoleId" name="importRoleId" type="hidden" value="${role.id}"/>
            <table class="grid">
            	<tr>
                    <td><font color='red'>*</font>字段 描述</td>
                	<td>
                		<input id="fieldCode" name="name" type="text" placeholder="请输入 字段描述" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>字段 描述</td>
                	<td>
                		<input id="fieldCode" name="name" type="text" placeholder="请输入 字段描述" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                
            </table>
        </form>
    </div>
</div>