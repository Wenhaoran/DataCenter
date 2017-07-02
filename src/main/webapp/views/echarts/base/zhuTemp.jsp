<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function addDetail(cid) {
    $('#echartColumnAddForm').form('submit',{
        url : '${path}/pages/echarts/saveMustColumn',
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
    		<input id="echartId" name="echartId" type="hidden" value="${echart.id}"/>
    		<input id="exportId" name="exportId" type="hidden" value="${export.id}"/>
    		<input id="rsId" name="rsId" type="hidden" value="${rs.id}"/>
            <table class="grid">
                <tr>
                    <td>图表名称</td>
                	<td>
                		<input id="title" name="title" type="text" placeholder="请输入图表名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                
            </table>
        </form>
    </div>
</div>