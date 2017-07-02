<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="texpjourPurchInvoiceDetViewForm" method="post">
    		<input name="id" type="hidden" value="${asdf.id}"/>
            <table class="grid">
                <tr>
                    <td>发票主键</td>
                    <td>
                    	<input id="invoiceId" name="invoiceId" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.invoiceId}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>单据标识</td>
                    <td>
                    	<input id="billId" name="billId" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.billId}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>单据号</td>
                    <td>
                    	<input id="billNo" name="billNo" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.billNo}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>单据类型</td>
                    <td>
                    	<input id="billType" name="billType" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.billType}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>供应商账户ID</td>
                    <td>
                    	<input id="suppAcctId" name="suppAcctId" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.suppAcctId}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>进货码洋</td>
                    <td>
                    	<input id="purchCodePrice" name="purchCodePrice" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.purchCodePrice}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>进货实洋</td>
                    <td>
                    	<input id="purchRealPrice" name="purchRealPrice" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.purchRealPrice}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>折扣金额</td>
                    <td>
                    	<input id="discountAmount" name="discountAmount" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.discountAmount}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>种数</td>
                    <td>
                    	<input id="kindNum" name="kindNum" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.kindNum}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>册数</td>
                    <td>
                    	<input id="copiesNum" name="copiesNum" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.copiesNum}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td>
                    	<input id="remark" name="remark" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.remark}" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td>
                    	<input id="createOn" name="createOn" type="text" placeholder="请选择创建时间" class="easyui-textbox span2" value="<fmt:formatDate value='${texpjourPurchInvoiceDet.createOn}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>创建人名称</td>
                    <td>
                    	<input id="createbyName" name="createbyName" type="text" class="easyui-textbox span2" value="${texpjourPurchInvoiceDet.createbyName}" disabled="disabled"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>