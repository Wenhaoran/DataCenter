<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var isValid = false;

function addDetail(cid) {
    $('#echartJsonAddForm').form('submit',{
        url : '${path}/pages/echartJson/add',
        onSubmit : function(param) {
        	var textarea = $('#echartJsonNametextarea').val();
            var sourceVal = JSON.stringify(textarea);
            //console.log(textarea);
            //console.log(sourceVal);
            var replaceSource ; 
            if(RegExp(textarea).test("'")){
            	replaceSource = textarea.replace(/'/g, "DMXY");
            }else{
            	replaceSource = textarea.replace(/"/g, "DMXY");
            }
            //console.log(replaceSource);
            param.source = replaceSource;
        },
        success : function(result) {
            progressClose();
            result = $.parseJSON(result);
            if (result.success) {
            	$("#"+cid).dialog("close");
				$("#"+cid).dialog("destroy");
            }else{
            	easyui_error(result.msg);
            }
        }
    });
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="echartJsonAddForm" method="post">
    		<input id="echartId" name="echartId" type="hidden" value="${echarts.id}"/>
            <table class="grid">
            	<tr>
                    <td>数据别名</td>
                	<td>
                		<input id="echartJsonName" name="name" type="text" placeholder="请输入 别名" class="easyui-textbox span6" data-options="required:true" maxlength="json" value=""/>
					</td>
                </tr>
                <tr>
                    <td>数据编码</td>
                	<td>
                		<input id="echartJsonCode" name="code" type="text" placeholder="请输入 编码" class="easyui-textbox span6" data-options="required:true" maxlength="json" value=""/>
					</td>
                </tr>
                <tr>
                    <td>数据类型</td>
                	<td>
                		<select id="type_query" name="type" class="easyui-combobox" data-options="width:320,height:26,editable:false,panelHeight:'140px'">
		              		<option value="">-请选择-</option>
			                <option value="oneArray">单个元素的数组集合</option>
			                <option value="manyArray">多元素数组 的 数组集合</option>
			                <option value="jsonValueString">json格式数据(key:value value：字符串)</option>
			                <option value="jsonValueArray">json格式数据(key:value value：数组)</option>
			                <option value="jsonArray">简单json数组</option>
			                <option value="other">任何复杂 数据，请拆成多个简单数据源</option>
						 </select>
					</td>
                </tr>
                <tr>
                    <td>json源数据</td>
                	<td>
                		<!-- <input id="echartJsonName" name="source" type="text" class="easyui-textbox span6" data-options="required:false" maxlength="json" value=""/> -->
                		<textarea id="echartJsonNametextarea" rows="15" cols="75" ></textarea>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>
