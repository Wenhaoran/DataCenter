<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
$(function(){
	  $("#transport").combobox({
	    	onChange: function (n,o) {
	    		//校验编号不能重复
	    		$.ajax({ 
	    	           type: "post", 
	    	           url: "${path}/tbusisInqueryDetail/transportChange", 
	    	           data: {
		    	        	   detailids : $("#detailids").val(),
		    	        	   trans : n
	    	        	  	 },
	    	           async: false,
	    	           dataType: "json", 
	    	           success: function (result) { 
	    	        	   if(!result.success){
	    	        	   		$.messager.alert('错误', result.msg, 'error');
	    	        	   }else{
	    	        		   $("#content").val(result.obj);
	    	        	   }             
	    	           }, 
	    	           error: function (XMLHttpRequest, textStatus, errorThrown) { 
	    	                   alert(errorThrown); 
	    	                   return false;
	    	           } 
	    	    	});
	    	}
	    });
});


function sendEmail(cid)  {
    $('#tbaseMailTemplateEditForm').form('submit',{
        url : '${path}/tbusisInqueryDetail/sendEmail',
        onSubmit : function() {
            progressLoad();
            var isValid = $(this).form('validate');
            if (!isValid) {
                progressClose();
            }
            return isValid;
        },
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
    <div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="padding: 3px;">
        <form id="tbaseMailTemplateEditForm" method="post">
    		<input id='detailids' name="detailids" type="hidden" value="${detailids}"/>
            <table class="grid">
                <tr>
                    <td><font color='red'>*</font>收件人：</td>
                    <td>
                    	<input id="toMail" name="toMail" type="text" placeholder="请输入收件人" class="easyui-textbox" data-options="required:true"  style="width: 700px;height: 20px;" maxlength="1024" value="${toUser}"/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>抄送：</td>
                    <td>
                    	<input id="toMail" name="toMail" type="text" placeholder="请输入抄送人" class="easyui-textbox" data-options="required:true"  style="width: 700px;height: 20px;"  maxlength="2048" value="${cCEmail}"/>
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>主题：</td>
                    <td>
                    	<input id="title" name="title" type="text" placeholder="请输入主题" class="easyui-textbox" data-options="required:true"  style="width: 700px;height: 20px;"  maxlength="2048" value="${title}"/>
					</td>
                </tr>
                 <tr>
                 	<td><font color='red'>*</font>运输商：</td>
                	<td>
                    	<select id="transport" name="transport" class="easyui-combobox" data-options="width:300,height:29,editable:false,panelHeight:'auto',required:true" >
                    		<option value="">-请选择-</option> 
                    		<c:forEach items="${transList}" var="dic">
                    			<option value="${dic.id}">${dic.transName}</option>
                    		</c:forEach>
                    	</select>
					</td>
				</tr>
				 <tr>
                    <td>附件</td>
                    <td>
                   		 <a href="${filepath }">${filename}</a>&nbsp;&nbsp;
					</td>
                </tr>
                <tr>
                    <td><font color='red'>*</font>正文：</td>
					<td>
					<textarea rows="35" cols="120" id="content" name = 'body' class="easyui-textbox " data-options="required:true"  >${content}</textarea>
					</td>
                </tr>
            </table>
        </form>
    </div>
</div>
