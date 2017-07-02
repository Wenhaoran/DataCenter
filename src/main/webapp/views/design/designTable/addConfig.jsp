<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

$(function() {
	
});

function add(cid) {
    $('#tableAddForm').form('submit',{
        url : '${path}/pages/config/addConfig',
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
	<form id="tableAddForm" method="post">
		<%-- <input id="tableId" name="tableId" type="hidden" value="${tableId}"/>
        <table class="grid">
            <tr>
                <td><font color='red'>*</font>方案名称</td>
            	<td>
            		<input id="tableColumnName" name="name" type="text" placeholder="请输入方案名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
				</td>
            </tr>
        </table> --%>
		<div class="easyui-tabs" data-options="tabWidth:112" style="overflow: auto; width: 900px; height: 430px; padding: 0px;">
			<div title="代码下载到本地" style="padding: 3px">
				<input id="tableId" name="tableId" type="hidden" value="${tableId}"/>
				<table class="grid">
					<tr>
						<td>代码下载到本地规则：</td>
						<td>
							<b style="color: red;">生成结构：{包名}/{项目名}/{分层(mapper,po,service,controller)}/{模块名}/{java类}</b>
						</td>
					</tr>
					<tr>
						<td>包路径</td>
						<td>
							<input id="tableConfigPackagePath" name="packagePath" type="text" placeholder="请输入包路径" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
					<tr>
						<td>项目名称</td>
						<td>
							<input id="tableConfigprojectName" name="projectName" type="text" placeholder="请输入项目名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
					<tr>
						<td>模块名称</td>
						<td>
							<input id="tableConfigModuleName" name="moduleName" type="text" placeholder="请输入模块名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
				</table>
			</div>
			<div title="代码上传ftp" style="padding: 3px">
				<table class="grid">
					<tr>
						<td>ftp服务器地址</td>
						<td>
							<input id="tableConfighostName" name="hostName" type="text" placeholder="请输入ftp服务器地址" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
					<tr>
						<td>ftp服务器端口</td>
						<td>
							<input id="tableConfigPort" name="port" type="text" placeholder="请输入ftp服务器端口" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
					<tr>
						<td>ftp登录账户</td>
						<td>
							<input id="tableConfigUserName" name="username" type="text" placeholder="请输入ftp登录账户" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
					<tr>
						<td>ftp登录密码</td>
						<td>
							<input id="tableConfigpassword" name="password" type="text" placeholder="请输入ftp登录密码" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
					<tr>
						<td>ftp保存路径</td>
						<td>
							<input id="tableConfigPathname" name="pathname" type="text" placeholder="请输入ftp保存路径" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
    </form>
</div>