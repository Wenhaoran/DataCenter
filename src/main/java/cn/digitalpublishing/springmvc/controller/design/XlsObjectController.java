package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.excel.XlsObjectForm;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/xls")
public class XlsObjectController extends BaseController {
	
	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, XlsObjectForm form) throws Exception {
		//Map<String,String> accountInfoMap = (Map<String, String>) request.getSession().getAttribute("pt_accountInfo");
		String forwardString = "design/excel/list";
		Map<String, Object> model = new HashMap<String, Object>();
			form.setIsSuccess(SUCCESS);
		try {
			
		} catch (Exception e) {
			form.setIsSuccess(FAILURE);
			form.setMsg((e instanceof DesignException) ? ((DesignException) e).getMessage() : e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGrid(HttpServletRequest request, XlsObject excel,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(excel.getCode())){
				condition.put("code", excel.getCode());
			}
			if(!Strings.isNullOrEmpty(excel.getName())){
				condition.put("name", excel.getName());
			}
			pageInfo.setCondition(condition);
			xlsObjectService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addpage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, XlsObject excel) throws Exception {
    	String forward = "design/excel/add";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		Map<String, Object> condition = new HashMap<String, Object>();
			List<FTPConnect> ftpList = ftpConnectService.getFTPConnectList(condition);
			model.put("ftpList", ftpList);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
	
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,XlsObject excel) throws Exception {
    	try {
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("path", excel.getPath());
    		List<XlsObject> xlsList = xlsObjectService.getXlsObjectList(condition);
    		if(xlsList.size()>0){
    			return renderError("添加失败！excel目录 不能重复");
    		}
    		if(excel.getPathType().equals("2")){
    			excel.setFtpId("");
    		}else if(Strings.isNullOrEmpty(excel.getFtpId())){
    			 return renderError("添加失败！目录类型为FTP，则FTP选择 必填");
    		}
    		excel.setType("0");//是否 映射数据库 表	未映射表
    		excel.setMapped("0");//是否映射完成	字段映射未完成
    		excel.setIfRole("0");//是否设置 导入规则 	未配置 导入规则
    		excel.setCreateId((String)request.getSession().getAttribute("accountId"));
    		excel.setCreateDate(new Date());
    		excel.setStatus(0);
    		xlsObjectService.insertXlsObject(excel);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    
    @RequestMapping(value = "/mappedPage", method = RequestMethod.GET)
    public ModelAndView mapped(HttpServletRequest request, String id) throws Exception {
    	String forward = "design/excel/mappedTable";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		XlsObject xls = xlsObjectService.getXlsObjectById(id);
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("status", 1);
			List<DeDBConnect> connList = deDBConnectService.getDBConnectList(condition);
			condition.clear();
			List<FTPConnect> ftpList = ftpConnectService.getFTPConnectList(condition);
			model.put("ftpList", ftpList);
			model.put("connList", connList);
			model.put("xls", xls);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/mappedsave", method = RequestMethod.POST)
    @ResponseBody
    public Object mappedSave(HttpServletRequest request,XlsObject excel) throws Exception {
    	try {
    		excel.setType("1");//是否 映射数据库 表  已映射表
    		excel.setUpdateId((String)request.getSession().getAttribute("accountId"));
    		excel.setUpdateDate(new Date());
    		excel.setStatus(0);
    		xlsObjectService.updateXlsObject(excel);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    
    @RequestMapping(value = "/setRolePage", method = RequestMethod.GET)
    public ModelAndView setRole(HttpServletRequest request, String id) throws Exception {
    	String forward = "design/excel/rolePage";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		XlsObject xls = xlsObjectService.getXlsObjectById(id);
    		Map<String, Object> condition = new HashMap<String, Object>();
    		
			List<FTPConnect> ftpList = ftpConnectService.getFTPConnectList(condition);
			List<DeDBConnect> connList = deDBConnectService.getDBConnectList(condition);
			model.put("ftpList", ftpList);
			model.put("connList", connList);
			model.put("xls", xls);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/rolesave", method = RequestMethod.POST)
    @ResponseBody
    public Object roleSave(HttpServletRequest request,XlsObject excel) throws Exception {
    	try {
    		excel.setType("1");//是否 映射数据库 表  已映射表
    		excel.setUpdateId((String)request.getSession().getAttribute("accountId"));
    		excel.setUpdateDate(new Date());
    		excel.setStatus(0);
    		xlsObjectService.updateXlsObject(excel);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping(value = "/mappedOK") 
  	@ResponseBody
  	public Object deleteAMRS(HttpServletRequest request,String xlsId) throws Exception {//null
  		try {
  			XlsObject xls = xlsObjectService.getXlsObjectById(xlsId);
  			xls.setMapped("1");
  			xlsObjectService.updateXlsObject(xls);
  		} catch (Exception e) {
  			logger.error("设置出现异常，", e);
  			e.printStackTrace();
  		}
  		return renderSuccess("设置成功！");
  	}
    
    
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request) throws Exception {
		String jsonarray = "";
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<XlsObject> pageList = xlsObjectService.getXlsObjectList(condition);
			jsonarray = JSONArray.toJSONString(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    
    @RequestMapping("/editPage")
    public ModelAndView editPage(HttpServletRequest request, XlsObject object) throws Exception {
    	String forward = "design/excel/edit";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try {
    		String id = request.getParameter("id");
    		object = xlsObjectService.getXlsObjectById(id);
    		Map<String, Object> condition = new HashMap<String, Object>();
			List<FTPConnect> ftpList = ftpConnectService.getFTPConnectList(condition);
			model.put("ftpList", ftpList);
        	model.put("object", object);
        } catch(Exception e) {
			logger.error("跳转编辑页面出现异常，",e);
		}
        return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(XlsObject object) throws Exception {
    	try {
    		object.setUpdateId("");
    		object.setUpdateDate(new Date());
    		xlsObjectService.updateXlsObject(object);
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) throws Exception {
    	try {
    		xlsObjectService.deleteXlsObjectById(id);
        } catch(Exception e) {
        	e.printStackTrace();
        	return renderError(e.getMessage());
    	}
        return renderSuccess("删除成功！");
    }
}