<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function add(cid) {
    $('#echartsAddForm').form('submit',{
        url : '${path}/pages/echarts/add',
       
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
        <form id="echartsAddForm" method="post">
    		<input id="id" name="id" type="hidden"/>
            <table class="grid">
                <tr>
                    <td>模板名称</td>
                	<td>
                		<input id="echartsName" name="name" type="text" placeholder="请输入图表名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>模板编码</td>
                	<td>
                		<input id="echartsCode" name="code" type="text" placeholder="请输入图表编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <tr>
                    <td>模板类型</td>
                	<td>
                		<input id="echartsType" name="type" type="text" placeholder="请输入图表类型" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>