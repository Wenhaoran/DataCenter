<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>SQL报表管理</title>
   
</head>
<body>	
    <div class="easyui-layout" data-options="fit:true,border:true,title:'打印'" >
	    
        <div data-options="region:'center',border:false"  >
           <iframe id="report" runat="server" src="${tourl} " width="100%" height="98%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
        </div>
    </div>
    
   
    
</body>
</html>