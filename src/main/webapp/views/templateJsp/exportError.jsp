<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<script type="text/javascript">
    $(function() {
    	console.log('${errInfo}');
    	$("#errInfo").val('${errInfo}');
    });

</script>
</head>
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'报表查询错误'">
			<div data-options="region:'north',fit:false,border:false,title:'错误日志',hideCollapsedContent:false" style="height:70px;overflow: hidden;padding:1px;background-color: #fff">
	    		<form id="detailSearchForm">
	    			<div class="search_condition">
	    				<p>
							<b style="color: red;">查询错误：</b>
	                		<b id="errInfo" style="color: red;">${errInfo}</b>
						</p>
					</div>
	    		</form>
    		</div>
    </div>
