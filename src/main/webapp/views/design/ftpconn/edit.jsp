<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid;
function modify(cid)  {
   	$('#systemEditForm').form('submit',{
          url : '${path}/pages/ftp/edit',
          
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
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="systemEditForm" method="post">
    		<input name="id" type="hidden" value="${ftpconn.id}"/>
            <table class="grid">
                <tr>
                    <td>连接编号</td>
                	<td>
                		<input id="ftpectCode" name="code" type="text" placeholder="请输入系统编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${ftpconn.code}"/>
					</td>
                </tr>
                <tr>
                    <td>连接名称</td>
                	<td>
                		<input id="ftpectName" name="name" type="text" placeholder="请输入系统名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${ftpconn.name}"/>
					</td>
                </tr>
                <tr>
                    <td>连接地址</td>
                	<td>
                		<input id="ftpectAddress" name="address" type="text" placeholder="请输入连接地址" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${ftpconn.address}"/>
					</td>
                </tr>
                <tr>
                    <td>连接端口</td>
                	<td>
                		<input id="ftpectPort" name="port" type="text" placeholder="请输入连接端口" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${ftpconn.port}"/>
					</td>
                </tr>
                <tr>
                    <td>用户名称</td>
                	<td>
                		<input id="ftpectUName" name="username" type="text" placeholder="请输入用户名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value="${ftpconn.username}"/>
					</td>
                </tr>
                <tr>
                    <td>用户密码</td>
                	<td>
                		<input id="ftpectPass" name="password" type="text" placeholder="请输入用户密码" class="easyui-textbox span2" data-options="required:false" maxlength="32" value="${ftpconn.password}"/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>