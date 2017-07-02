<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#roleType').combobox({
    	onSelect: function(record){
    		if(record.value=="xls"){
    			$("#objectId").combobox('clear');
    			var url = "${path}/pages/xls/getList";
        		$('#objectId').combobox('reload',url);  
    		}else if(record.value=="txt"){
    			$("#objectId").combobox('clear');
    			var url = "${path}/pages/txt/getList";
        		$('#objectId').combobox('reload',url);
    		}
    	}
    });
});
function add(cid) {
    $('#roleAddForm').form('submit',{
        url : '${path}/pages/importRole/add',
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
        <form id="roleAddForm" method="post">
    		<input id="id" name="id" type="hidden"/>
            <table class="grid">
            	<tr>
                    <td>规则名称</td>
                	<td>
                		<input id="roleName" name="name" type="text" placeholder="请输入系统名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>规则类型</td>
                	<td>
                		<select id="roleType" name="type" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <option value="txt">text</option>
			                <option value="xls">excel</option>
						 </select>
					</td>
                </tr>
                
                <tr>
                    <td>规则对象</td>
                	<td>
                		<select id="objectId" name="objId" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'id',textField:'name'"></select>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>