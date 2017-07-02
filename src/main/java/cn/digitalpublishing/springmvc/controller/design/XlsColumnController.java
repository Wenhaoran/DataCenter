package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;

@Controller
@RequestMapping("/pages/xlscolumn")
public class XlsColumnController extends BaseController {

	@RequestMapping(value = "/columnListByXlsId")
    public ModelAndView columnListByXlsId(HttpServletRequest request,String xlsId) throws Exception {
        String forward = "design/excel/detailColumnList";
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	XlsObject xls = xlsObjectService.getXlsObjectById(xlsId);
			String name = xls.getName();
			model.put("name", name);
			model.put("xls", xls);
        } catch(Exception e) {
    		logger.error("查询列表出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
	
  	@RequestMapping(value = "/dataGridByXlsId", method = RequestMethod.POST)
  	@ResponseBody
  	public Object dataGridByXlsId(HttpServletRequest request,XlsColumn column,Integer page, Integer rows, String sort, String order) {
  		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
  		String xlsId = request.getParameter("xlsId");
  		try {
  			Map<String, Object> condition = new HashMap<String, Object>();
  			//当前 excel ID
  			if(!Strings.isNullOrEmpty(xlsId)){
  				condition.put("xlsId", xlsId);
  			}
  			pageInfo.setCondition(condition);
  			xlsColumnService.findDataGrid(pageInfo);
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("查询列表出现异常，", e);
  		}
  		return pageInfo;
  	}
  	
  	@RequestMapping(value = "/deleteColumn") 
  	@ResponseBody
  	public Object deleteAMRS(HttpServletRequest request,String[] ids,String xlsId) throws Exception {//null
  		try {
  			xlsColumnService.batchDelByXlsId(xlsId, ids);
  		} catch (Exception e) {
  			logger.error("解除出现异常，", e);
  			e.printStackTrace();
  		}
  		return renderSuccess("解除成功！");
  	}
  	
  	@RequestMapping(value = "/mappedColumn", method = RequestMethod.GET)
    public ModelAndView mappedColumn(HttpServletRequest request, String id) throws Exception {
    	String forward = "design/excel/mappedColumn";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		XlsColumn column = xlsColumnService.getXlsColumnById(id);
    		XlsObject obj = xlsObjectService.getXlsObjectById(column.getXlsId());
    		DeDBConnect conn = deDBConnectService.getDBConnectById(obj.getDbId());
    		DesignTable table = new DesignTable();
    		table.setDbconn(conn);
    		table.setTableName(obj.getDbTable());
    		List<DesignTableColumn> columnList = DBUtil.getColumnList(table);
    		Map<String, Object> condition = new HashMap<String, Object>();
    		
			List<FTPConnect> ftpList = ftpConnectService.getFTPConnectList(condition);
			List<DeDBConnect> connList = deDBConnectService.getDBConnectList(condition);
			String coluList = JSONArray.toJSONString(columnList);
			model.put("coluList", coluList);
			model.put("columnList", columnList);
			model.put("ftpList", ftpList);
			model.put("connList", connList);
			model.put("xls", obj);
			model.put("column", column);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/mappedSave", method = RequestMethod.POST)
    @ResponseBody
    public Object mappedSave(HttpServletRequest request,XlsColumn column) throws Exception {
    	try {
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("xlsId", column.getXlsId());
    		//condition.put("tabColumn", column.getTabColumn());
    		List<XlsColumn> columnPathList = xlsColumnService.getXlsColumnList(condition);
    		if(columnPathList.size()>0){
    			return renderError("添加失败！关联字段不能重复选择");
    		}
    		
//    		column.setType("1");//
    		column.setUpdateId((String)request.getSession().getAttribute("accountId"));
    		column.setUpdateDate(new Date());
    		column.setStatus(1);//status == 1 ，已经映射数据库表 字段
    		xlsColumnService.updateXlsColumn(column);	
    		
    		condition.clear();//查询当前xls 对象 ，所有的字段，是否 全部映射完成，是：更改xls 状态为，映射完成。
    		condition.put("xlsId", column.getXlsId());
    		condition.put("status",0);
    		List<XlsColumn> mappedList = xlsColumnService.getXlsColumnList(condition);
    		XlsObject xls = xlsObjectService.getXlsObjectById(column.getXlsId());
    		if(mappedList.size()== 0){
    			xls.setMapped("1");
    			xlsObjectService.updateXlsObject(xls);
    		}else{
    			xls.setMapped("0");
    			xlsObjectService.updateXlsObject(xls);
    		}
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, XlsColumn column) throws Exception {
    	String forward = "design/xlscolumn/add";
    	String xlsId = request.getParameter("xlsId");
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		model.put("xlsId", xlsId);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }

	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,XlsColumn column) throws Exception {
    	try {
    		column.setType("0");//是否 映射数据库 表	未映射表
    		
    		column.setCreateId((String)request.getSession().getAttribute("accountId"));
    		column.setCreateDate(new Date());
    		column.setStatus(0);
    		xlsColumnService.insertXlsColumn(column);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
}