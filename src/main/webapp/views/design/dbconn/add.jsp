<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function add(cid) {
    $('#dbconnectAddForm').form('submit',{
        url : '${path}/pages/dbconn/add',
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
        <form id="dbconnectAddForm" method="post">
    		<input id="id" name="id" type="hidden"/>
            <table class="grid">
                <tr>
                    <td>连接编号</td>
                	<td>
                		<input id="dbconnectCode" name="code" type="text" placeholder="请输入系统编号" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>连接名称</td>
                	<td>
                		<input id="dbconnectName" name="name" type="text" placeholder="请输入系统名称" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>数据库类型</td>
                	<td>
                		<!-- <input id="dbconnectName" name="name" type="text" placeholder="请输入系统名称" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/> -->
                		<select id="dbconnectType" name="type" class="easyui-combobox" validType="comboxRequired['-请选择-']" data-options="width:120,height:26,editable:false,panelHeight:'140px',required:true">
		              		<option value="">-请选择-</option>
			                <option value="mysql">mysql</option>
			                <option value="oracle">oracle</option>
			                <option value="sqlserver">sqlserver</option>
						 </select>
					</td>
                </tr>
                <tr>
                    <td>连接地址</td>
                	<td>
                		<input id="dbconnectAddress" name="address" type="text" placeholder="请输入连接地址" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>连接端口</td>
                	<td>
                		<input id="dbconnectPort" name="port" type="text" placeholder="请输入连接端口" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>数据库名称</td>
                	<td>
                		<input id="dbconnectDBname" name="dbname" type="text" placeholder="请输入数据库名称" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>用户名称</td>
                	<td>
                		<input id="dbconnectUName" name="username" type="text" placeholder="请输入用户名称" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>用户密码</td>
                	<td>
                		<input id="dbconnectPass" name="password" type="text" placeholder="请输入用户密码" class="easyui-textbox span2" data-options="required:false" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>