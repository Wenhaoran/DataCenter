<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${staticPath}/static/My97DatePicker/skin/WdatePicker.css" />
<script type="text/javascript"
	src="${staticPath}/static/My97DatePicker/WdatePicker.js"
	charset="UTF-8"></script>
<script type="text/javascript">
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div class="easyui-tabs" data-options="tabWidth:112" style="overflow: auto; width: 900px; height: 430px; padding: 0px;">
		<div title="详情记录" style="padding: 3px">
			<div title="销售订单信息" class="easyui-panel" style="padding: 0px; overflow-y: auto" data-options="border:false">
				<table class="grid">
					<tr>
						<td width="90px">销售批次号</td>
						<td>
							<input id="tableClassName" name="className" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
						<td width="90px">销售订单号</td>
						<td>
							<input id="tableClassName" name="className" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
						<td width="90px">创建时间</td>
						<td>
							<input id="tableClassName" name="className" type="text" placeholder="请输入资源名称" class="easyui-textbox span2" data-options="width:240,required:true" maxlength="32" value=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div title="动作记录" style="padding: 0px">
			<table id="recordDataGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>
</div>
