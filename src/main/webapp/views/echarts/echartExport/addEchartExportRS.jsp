<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function addDetail(cid) {
    $('#echartColumnAddForm').form('submit',{
        url : '${path}/pages/echarts/saveEchartExportRS',
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
    		<input id="exportId" name="exportId" type="hidden" value="${export.id}"/>
    		<input id="type" name="type" type="hidden" value="${type}"/>
            <table class="grid">
            	<c:if test="${type!='one'}">
            		<tr>
	                    <td>图表名称</td>
	                	<td>
	                		<input id="echartExportRSName" name="name" type="text" placeholder="请输入图表名称" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
						</td>
	                </tr>
	                <!-- <tr>
	                    <td>图表编码</td>
	                	<td>
	                		<input id="echartExportRSCode" name="code" type="text" placeholder="请输入图表编号" class="easyui-textbox span2" data-options="required:true" maxlength="32" value=""/>
						</td>
	                </tr> -->
            	</c:if>
                <tr>
                    <td>模板选择</td>
                	<td>
                		<select id="echartId" name="echartId" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
			    			<option value="">-请选择-</option>
			    			<c:forEach items="${echartsList}" var="echarts">
								<option value="${echarts.id}">${echarts.name}：${echarts.code}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>