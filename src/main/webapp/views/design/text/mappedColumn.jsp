<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#columnDbId').combobox({
		onClick:function(record){
			var colList = '${coluList}';
			var colObjList = JSON.parse(colList);
			for(var col in colObjList){
				var name = colObjList[col].name;
				if(name == record.value){
					$('#tabColumnType').textbox("setValue", colObjList[col].type);
				}
			}
    	}
    });
});
function addDetail(cid) {
    $('#columnectAddForm').form('submit',{
        url : '${path}/pages/xlscolumn/mappedSave',
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
        <form id="columnectAddForm" method="post">
    		<input id="id" name="id" type="hidden" value="${column.id}"/>
    		<input id="xlsId" name="xlsId" type="hidden" value="${xls.id}"/>
            <table class="grid">
                <tr>
                    <td><font color='red'>*</font>映射字段</td>
                	<td>
                		<select id="columnDbId" name="tabColumn" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
			    			<option value="">-请选择-</option>
			    			<c:forEach items="${columnList}" var="colu">
								<option value="${colu.name}">${colu.name}：${colu.type}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>映射字段类型</td>
                	<td>
                		<input id="tabColumnType" name="tabColumnType" type="text" class="easyui-textbox span2"  maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>