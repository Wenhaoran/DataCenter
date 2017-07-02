<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#txtPathType').combobox({
    	onSelect: function(record){
    		var pathType = record.value;//$("#dbId").val();
    		if(pathType==2){
    			$("#ftpIdSelect").hide();
    		}else{
    			$("#ftpIdSelect").show();
    		}
    	}
    });
});
function add(cid) {
    $('#txtAddForm').form('submit',{
        url : '${path}/pages/txt/save',
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
        <form id="txtAddForm" method="post">
    		<input id="id" name="id" type="hidden"/>
            <table class="grid">
            	<tr>
                    <td>对象名称</td>
                	<td>
                		<input id="txtName" name="name" type="text" placeholder="请输入系统名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>对象编号</td>
                	<td>
                		<input id="txtCode" name="code" type="text" placeholder="请输入系统编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                
                <tr>
                    <td>目录类型</td>
                	<td>
                		<select id="txtPathType" name="pathType" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <option value="1">ftp服务器路径</option>
			                <option value="2">服务器本地</option>
						 </select>
					</td>
                </tr>
                <tr id="ftpIdSelect">
                    <td>FTP服务器选择</td>
                	<td>
                		<select id="txtPathFTPId" name="ftpId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <c:forEach items="${ftpList}" var="ftp">
								<option value="${ftp.id}">${ftp.name}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td>text间隔类型</td>
                	<td>
                		<input id="txtType" name="type" type="text" placeholder="请输入连接地址" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>text默认路径</td>
                	<td>
                		<input id="txtPath" name="path" type="text" placeholder="请输入连接地址" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>text成功路径</td>
                	<td>
                		<input id="txtPathSuccess" name="pathSuccess" type="text" placeholder="请输入用户名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>text失败路径</td>
                	<td>
                		<input id="txtPathError" name="pathError" type="text" placeholder="请输入用户密码" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>