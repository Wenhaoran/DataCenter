<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#objId').combobox({
		onClick:function(record){
			$("#columnId").combobox('clear');
			var url = "${path}/pages/importRt/getTableColumnList?rtId="+record.value;
			//$('#fieldrefField option:selected').val();
			$("#columnId").empty();
    		$('#columnId').combobox('reload',url);  
    	}
    });
	
	$('#importRTId').combobox({
		onClick:function(record){
			$("#tableColumn").combobox('clear');
			var url = "${path}/pages/importRt/getTableColumnList?rtId="+record.value;
			//$('#fieldrefField option:selected').val();
			$("#tableColumn").empty();
    		$('#tableColumn').combobox('reload',url);  
    	}
    });
	
	$('#tableColumn').combobox({
		onClick:function(record){
    	}
    });
	
	
	$('#matchId').combobox({
		onClick:function(record){
			$("#matchName").combobox('clear');
			$("#matchCode").combobox('clear');
			var url = "${path}/pages/importRt/getTableColumnList?rtId="+record.value;
			$("#matchName").empty();
    		$('#matchName').combobox('reload',url);  
    		$("#matchCode").empty();
    		$('#matchCode').combobox('reload',url);  
    	}
    });
	
});
function add(cid) {
    $('#xlsectAddForm').form('submit',{
        url : '${path}/pages/importDetail/add',
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
        <form id="xlsectAddForm" method="post">
    		<input id="roleId" name="roleId" type="hidden" value="${role.id}"/>
    		<input id="mappedType" name="mappedType" type="hidden" value="${mappedType}"/>
            <table class="grid">
            	<c:if test="${mappedType!='1'}">
            		<tr>
	                    <td>字段名称</td>
	                	<td>
	                		<select id="columnId" name="columnId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <c:forEach items="${role.objColumnList}" var="column">
								<option value="${column.id}">${column.name}:${column.code}</option>
							</c:forEach>
						 </select>
						</td>
	                </tr>
            	</c:if>
            	<c:if test="${mappedType=='1'}">
            		<tr>
	                    <td>主表</td>
	                	<td>
	                		<select id="objId" name="objId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
			              		<option value="">-请选择-</option>
				                <c:forEach items="${rtList}" var="rt">
									<option value="${rt.id}">${rt.dbTable}</option>
								</c:forEach>
							</select>
						</td>
	                </tr>
	                <tr>
	                    <c:if test="${mappedType!='1'}">
	                    	<td>表字段</td>
	                    </c:if>
						<c:if test="${mappedType!='0'}">
	                    	<td>主表字段</td>
	                    </c:if>
	                	<td>
	                		<select id="columnId" name="columnId" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'name',textField:'name'"></select>
						</td>
	                </tr>
            	</c:if>
            	
            	<c:if test="${mappedType=='2'}">
            		<tr>
	                    <td>匹配表</td>
	                	<td>
	                		<select id="matchId" name="matchId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
			              		<option value="">-请选择-</option>
				                <c:forEach items="${rtList}" var="rt">
									<option value="${rt.id}">${rt.dbTable}</option>
								</c:forEach>
							</select>
						</td>
	                </tr>
	                <tr>
	                    <td>匹配字段</td>
	                	<td>
	                		<select id="matchName" name="matchName" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'name',textField:'name'"></select>
						</td>
	                </tr>
	                <tr>
	                    <td>保存字段</td>
	                	<td>
	                		<select id="matchCode" name="matchCode" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'name',textField:'name'"></select>
						</td>
	                </tr>
            	</c:if>
            	
                <tr>
            		<c:if test="${mappedType!='1'}">
                    	<td>映射表</td>
                    </c:if>
					<c:if test="${mappedType=='1'}">
                    	<td>从表</td>
                    </c:if>
                	<td>
                		<select id="importRTId" name="importRTId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <c:forEach items="${rtList}" var="rt">
								<option value="${rt.id}">${rt.dbTable}</option>
							</c:forEach>
						</select>
					</td>
                </tr>
                <tr>
                    <c:if test="${mappedType!='1'}">
                    	<td>表字段</td>
                    </c:if>
					<c:if test="${mappedType=='1'}">
                    	<td>从表字段</td>
                    </c:if>
                	<td>
                		<select id="tableColumn" name="tableColumn" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'name',textField:'name'"></select>
					</td>
                </tr>
                <tr>
                    <td>字段类型(暂不可用)</td>
                	<td>
						<input id="columnType" readonly="readonly" name="columnType" type="text" class="easyui-textbox span2"  maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>