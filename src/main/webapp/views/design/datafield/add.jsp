<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

$(function() {
	var dbId = '${dbId}';
	$('#fieldrefTable').combobox({
		onClick:function(record){
			$("#fieldrefField").combobox('clear');
			var url = "${path}/pages/field/getList?tableName="+record.value+"&dbId="+dbId;
			//$('#fieldrefField option:selected').val();
			$("#fieldrefField").empty();
    		$('#fieldrefField').combobox('reload',url);  
    	}
    });
	
	$('#fieldrefField').combobox({
		onClick:function(record){
//			alert($('#fieldrefTable').value);
			if($('#fieldrefTable option:selected').val()==""){
				easyui_error("请先选择被关联表");
			}
		},
    });
});

function addDetail(cid) {
    $('#fieldAddForm').form('submit',{
        url : '${path}/pages/field/add',
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
        <form id="fieldAddForm" method="post">
    		<input id="tableId" name="tableId" type="hidden" value="${tableId}"/>
            <table class="grid">
            	<tr>
                    <td><font color='red'>*</font>字段 名称</td>
                	<td>
                		<input id="fieldCode" name="code" type="text" placeholder="请输入 字段名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>字段 描述</td>
                	<td>
                		<input id="fieldCode" name="name" type="text" placeholder="请输入 字段描述" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>字段类型</td>
                	<td>
                		<select id="fieldType" name="type" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:120,height:26,editable:false,panelHeight:'140px'">
		              		<option value="">-请选择-</option>
			                <option value="varchar">字符串：varchar</option>
			                <option value="decimal">数字：decimal</option>
			                <option value="datetime">时间：datetime</option>
			                <option value="text">长文本：text</option>
			                <option value="ref">外键引用</option>
						 </select>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>字段长度</td>
                	<td>
                		<input id="fieldSize" name="size" type="text" placeholder="请输入 字段长度" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>被关联表</td>
                	<td>
                		<!-- <input id="fieldRefTable" name="refTable" type="text" placeholder="请输入 字段长度" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/> -->
                		<select id="fieldrefTable" name="refTable" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
			    			<option value="">-请选择-</option>
			    			<c:forEach items="${tableList}" var="table">
								<option value="${table.tableName}">${table.tableName}：${table.comments}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>被关联表字段</td>
                	<td>
                		<!-- <input id="fieldRefField" name="refField" type="text" placeholder="请输入 字段长度" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/> -->
                		<select id="fieldrefField" name="refField" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'name',textField:'name'">
						 </select>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>