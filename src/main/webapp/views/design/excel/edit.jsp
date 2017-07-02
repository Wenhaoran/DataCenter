<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;
$(function() {
	$('#xlsectPathType').combobox({
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

function modify(cid) {
    $('#xlsectAddForm').form('submit',{
        url : '${path}/pages/xls/edit',
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
    		<input id="id" name="id" type="hidden" value="${object.id}"/>
            <table class="grid">
            	<tr>
                    <td>对象名称</td>
                	<td>
                		<input id="xlsectName" name="name" type="text" placeholder="请输入系统名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.name}"/>
					</td>
                </tr>
                <tr>
                    <td>对象编号</td>
                	<td>
                		<input id="xlsectCode" name="code" type="text" placeholder="请输入系统编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.code}"/>
					</td>
                </tr>
                
                <tr>
                    <td>目录类型</td>
                	<td>
                		<select id="xlsectPathType" name="pathType" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <option value="1">ftp服务器路径</option>
			                <option value="2">服务器本地</option>
						 </select>
					</td>
                </tr>
                <tr id="ftpIdSelect">
                    <td>FTP服务器选择</td>
                	<td>
                		<select id="xlsectPathType" name="ftpId" class="easyui-combobox" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <c:forEach items="${ftpList}" var="ftp">
								<option value="${ftp.id}" <c:if test="${object.ftpId == ftp.id}">selected="selected"</c:if>>${ftp.name}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td>excel默认路径</td>
                	<td>
                		<input id="xlsectPath" name="path" type="text" placeholder="请输入连接地址" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.path}"/>
					</td>
                </tr>
                <tr>
                    <td>excel成功路径</td>
                	<td>
                		<input id="xlsectPathSuccess" name="pathSuccess" type="text" placeholder="请输入用户名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.pathSuccess}"/>
					</td>
                </tr>
                <tr>
                    <td>excel失败路径</td>
                	<td>
                		<input id="xlsectPathError" name="pathError" type="text" placeholder="请输入用户密码" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${object.pathError}"/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>