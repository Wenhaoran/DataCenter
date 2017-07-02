<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

$(function() {
	var jsonString = '${jsonMapString}';
	$('#echartJsonjsonId').combobox({
		onClick:function(record){
			$("#echartJsonTemp").empty();
			var jsonstr = JSON.parse(jsonString);
			for(var json of jsonstr){
				if(record.value == json.id){
					$('#echartJsonTemp').textbox("setValue", json.temp);
				}
			}
    		
    	}
    });
	
	var type = '${rs.type}';
	if(type!='one'){
		$('#exportId').combobox({
			onClick:function(record){
				$("#echartJsoncolumn").combobox('clear');
				var url = "${path}/pages/export/getList?exportId="+record.value;
				//$('#fieldrefField option:selected').val();
				$("#echartJsoncolumn").empty();
	    		$('#echartJsoncolumn').combobox('reload',url);  
	    	}
	    });
	}
});

function addMapPage(cid) {
	var rsId = '${rs.id}';
    $('#echartJsonMapAddForm').form('submit',{
        url : '${path}/pages/echarts/saveJsonMapValue?rsId='+rsId,
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
        <form id="echartJsonMapAddForm" method="post">
    		<input id="echartId" name="echartId" type="hidden" value="${echart.id}"/>
    		<c:if test="${rs.type=='one'}">
    			<input id="exportId" name="expId" type="hidden" value="${export.id}"/>
    		</c:if>
    		
            <table class="grid">
                <tr>
                    <td>json数据源</td>
                	<td>
                		<select id="echartJsonjsonId" name="jsonId" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
			    			<option value="">-请选择-</option>
			    			<c:forEach items="${jsonList}" var="json">
								<option value="${json.id}">${json.name}：${json.code}</option>
							</c:forEach>
						 </select>
					</td>
                </tr>
                <tr>
                    <td>数据源模板</td>
                	<td>
                		<input id="echartJsonTemp" type="text" readonly="readonly" data-options="width:240,height:26,panelHeight:'140px'" class="easyui-textbox span2" maxlength="1024" value=""/>
					</td>
                </tr>
                <tr>
                    <td>映射第几个数据</td>
                	<td>
                		<input id="echartJsonMapNum" name="jmapOrder" type="text" placeholder="请输入 映射第几个数据" class="easyui-numberbox span2" data-options="required:true" maxlength="32" value=""/>
					</td>
                </tr>
                <c:if test="${rs.type!='one'}">
                	<tr>
	                    <td>报表选择</td>
	                	<td>
	                		<select id="exportId" name="expId" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
				    			<option value="">-请选择-</option>
				    			<c:forEach items="${dataExportList}" var="data">
									<option value="${data.id}">${data.name}</option>
								</c:forEach>
							 </select>
						</td>
	                </tr>
	                <tr>
	                    <td>当前报表的哪个字段来映射</td>
	                	<td>
	                		<select id="echartJsoncolumn" name="column" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',valueField:'name',textField:'name'"><!-- ,valueField:'name',textField:'name' -->
							</select>
						</td>
	                </tr>
                </c:if>
                <c:if test="${rs.type=='one'}">
                	<tr>
	                    <td>当前报表的哪个字段来映射</td>
	                	<td>
	                		<select id="echartJsoncolumn" name="column" class="easyui-combobox" data-options="width:240,height:26,editable:true,panelHeight:'140px',required:true">
				    			<option value="">-请选择-</option>
				    			<c:forEach items="${columnList}" var="column">
									<option value="${column}">${column}</option>
								</c:forEach>
							 </select>
						</td>
	                </tr>
                </c:if>
            </table>
        </form>
    </div>
</div>