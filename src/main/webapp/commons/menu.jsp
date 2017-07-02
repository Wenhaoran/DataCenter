<%--标签 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<%-- <link rel="shortcut icon" href="${staticPath }/static/style/images/favicon.ico" /> --%>
<!-- [my97日期时间控件] -->

<!-- [EasyUI] -->
<script type="text/javascript" src="${staticPath }/static/jquery/jquery-3.1.1.min.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		
		var ticket = '${sessionScope.ticket}';
		var roleId = '${form.roleId}';
		var sysId = '${form.sysId}';
		var accountId = '${form.accountId}';
		getMenuList(roleId, sysId, ticket);
	});
	
	function createMenuListParent(resourceData,html,ticket){
		for(var [key,value] of resourceData){
			if(value.children.length > 0){
				html.push("");
				html.push("<li>");
				html.push("<span>" + value.name + "</span>");
				html.push("<ul>");
				var childrenList = value.children;
				for(var child of childrenList){
					createMenuListChild(child,html,ticket);
				}
				html.push("");
				html.push("</ul>");
				html.push("</li>");
			}else{
				html.push("<li>");
				var url = assemblyUrl(value, ticket);
				html.push("");
				html.push("<a href='###' onclick='addFrameTab(\""+ value.name +"\", \""+ url +"\")'>");
				html.push("<span style='cursor:pointer;'>" + value.name + "</span>");
				html.push("</a>");
				html.push("");
				html.push("");
				html.push("</li>");
			}
		}
	}

	function createMenuListChild(resourceData,html,ticket){
		if(resourceData.children !=null && resourceData.children.length > 0){
			html.push("");
			html.push("<li>");
			html.push("<span>" + resourceData.name + "</span>");
			html.push("<ul>");
			for(var child of resourceData.children){
				createMenuListChild(child,html,ticket);
			}
			html.push("");
			html.push("</ul>");
			html.push("</li>");
		}else{
			html.push("");
			html.push("<li>");
			var url = assemblyUrl(resourceData, ticket);
			html.push("");
			html.push("<a href='###' onclick='addFrameTab(\""+ resourceData.name +"\", \""+ url +"\")'>");
			html.push("<span style='cursor:pointer;'>" + resourceData.name + "</span>");
			html.push("</a>");
			html.push("");
			html.push("");
			html.push("</li>");
		}
	}


	function getMenuList(roleId, sysId, ticket) {
		$.ajax({
			"dataType" : 'json',
			"type" : 'POST',
			"url" : basePath + '/pages/frame/getResourceZTreeByRoleId',
			"data" : {
				'sysId' : sysId,
				'roleId' : roleId
			},
			"success" : function(response) {
				if (response.isSuccess == "true") {
					var html = [];
					var resourceData = new Map();
					var resourceListData = new Map();
					var resourceList = response.aaData;
					if (resourceList != null) {
						for (var i = 0; i < resourceList.length; i++) {
							var resource = resourceList[i];
							resourceListData.set(resource.id, resource);
						}
					}
					if (resourceList != null) {
						for (var i = 0; i < resourceList.length; i++) {
							var resource = resourceList[i];
							
							if (resource.parentResourceId == null || resource.parentResourceId == "") {
								if(resource.children === undefined || resource.children == 0){
									resource.children = [];
								}
								resourceData.set(resource.id, resource);
							}else{
								var parentResource = resourceData.get(resource.parentResourceId);
								if (parentResource != null) {
									parentResource.children.push(resource);
								}else{
									var parentResource2 = resourceListData.get(resource.parentResourceId);
									if(parentResource2.children === undefined || parentResource2.children.length == 0){
										parentResource2.children = [];
									}
									 
									parentResource2.children.push(resource);
								}
							}
						}
					}
					if(resourceData.size > 0){
						createMenuListParent(resourceData,html,ticket);
					}else{
						html.push("<li iconCls=\"\">");
						html.push("<span>无权限</span> ");
						html.push("<ul>");
						html.push("");
						html.push("</ul>");
						html.push("</li>");   
					}
					
					var defaultResourceName = null;
					var defaultResourceUrl = null;
					//var resources = resourceData.values();
					
					$("#dtree").html(html.join(""));
					console.log(html.join(""));
					//addFrameTab(defaultResourceName, defaultResourceUrl);
				} else {
					alertMsg('alertModal', 'alertMsg', response.msg);
					window.location.href = basePath + response.url;
				}
			},
			"error" : function(response) {
				window.location.href = basePath ;
			}
		});
	}
</script>

<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${staticPath }/static/easyui/themes/default/easyui.css" />
<script type="text/javascript" src="${staticPath }/static/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
 <%--  --%>

 <!-- [jQuery] -->
<script type="text/javascript" src="${staticPath }/static/easyui/jquery.min.js" charset="utf-8"></script>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${staticPath }/static/easyui/themes/icon.css" />
<script type="text/javascript" src="${staticPath }/static/easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<!-- [扩展JS] -->
<script type="text/javascript" src="${staticPath }/static/extJs.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/base.js?v20161227191521" charset="utf-8"></script>
<%-- <script type="text/javascript" src="${staticPath }/static/easyui-panletitle.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${staticPath }/static/utils.js" charset="utf-8"></script>
 --%><script type="text/javascript" src="${staticPath }/static/validator.js?2017011014" charset="utf-8"></script>
<!-- [上传文件组件] -->

<script src="${staticPath }/static/uploadify/jquery.uploadifive.js" type="text/javascript"></script>
<script src="${staticPath }/static/uploadify/jquery.uploadifive.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${staticPath }/static/uploadify/uploadifive.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/static/style/css/search.css?v201612261732"/>
<!-- [扩展样式] -->
<link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/dreamlu.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/base.css" />
<%-- <link rel="stylesheet" type="text/css" href="${staticPath }/static/icommon.css" /> --%>
<script type="text/javascript">
    var basePath = "${staticPath }";
   
</script>

