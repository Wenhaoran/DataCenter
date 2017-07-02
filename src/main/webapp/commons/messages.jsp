<%@ include file="/commons/taglibs.jsp" %>

<%-- Error Messages --%>
<c:if test="${not empty commonForm}">
	<div class="message">
		<img src="${ctx}/images/iconInformation.gif" alt="Info"/> &nbsp;&nbsp;${commonForm.msg}<br/>
	</div>    
</c:if>

<%-- Info Messages 
<c:if test="${not empty springErrors}">
	<div class="error">
        <c:forEach var="errorMsg" items="${springErrors}">
			<img src="${ctx}/images/iconWarning.gif" alt="Warning"/>${errorMsg}<br/>
        </c:forEach>
	</div>    
</c:if>
--%>



