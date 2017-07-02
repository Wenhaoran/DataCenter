<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function addDetail(cid) {
    $('#echartColumnAddForm').form('submit',{
        url : '${path}/pages/echartColumnValue/add',
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
        <form id="echartColumnAddForm" method="post">
    		<input id="echartId" name="echartId" type="hidden" value="${echarts.id}"/>
    		<input id="expId" name="expId" type="hidden" value="${export.id}"/>
            <table class="grid">
                <tr>
                    <td>字段名称</td>
                	<td>
                		<input id="echartColumnName" name="name" type="text" placeholder="请输入图表名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>字段编码</td>
                	<td>
                		<input id="echartColumnCode" name="code" type="text" placeholder="请输入图表编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>默认值</td>
                	<td>
                		<input id="echartColumnType" name="type" type="text" placeholder="请输入默认值" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>