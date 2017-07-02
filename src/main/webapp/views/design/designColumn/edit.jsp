<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function modify(cid) {
    $('#columnAddForm').form('submit',{
        url : '${path}/pages/column/edit',
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
        	<input id="id" name="id" type="hidden" value="${column.id}"/>
            <table class="grid">
                <tr>
                    <td>字段名称</td>
                	<td>
                		<input id="columnName" name="name" readonly="readonly" type="text" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${column.name}"/>
					</td>
                </tr>
                <tr>
                    <td>字段类型</td>
                	<td>
                		<input id="columntype" name="type" readonly="readonly" type="text" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${column.type}"/>
					</td>
                </tr>
                <tr>
                    <td>字段描述</td>
                	<td>
                		<input id="columncomments" name="comments" readonly="readonly" type="text" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${column.comments}"/>
					</td>
                </tr>
                <tr>
                    <td><b style="color: red;">java类型</b></td>
                	<td>
                		<%-- <input id="columnjavaType" name="javaType" type="text" placeholder="请输入java" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value="${column.javaType}"/> --%>
                		<select id="columnjavaType" name="javaType" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true" >
		              		<option value="">-请选择-</option>
			                <option value="String">String</option>
			                <option value="Integer">Integer</option>
			                <option value="boolean">boolean</option>
			                <option value="Date">Date</option>
			                <option value="bigdecimal">bigdecimal</option>
						 </select>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>java名称</td>
                	<td>
                		<input id="columnjavaField" name="javaField" type="text" placeholder="请输入Java名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="256" value="${column.javaField}"/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>