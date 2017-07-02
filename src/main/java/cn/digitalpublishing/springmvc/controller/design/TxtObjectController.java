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
import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.txt.TxtObjectForm;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/txt")
public class TxtObjectController extends BaseController {
	
	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, TxtObjectForm form) throws Exception {
		//Map<String,String> accountInfoMap = (Map<String, String>) request.getSession().getAttribute("pt_accountInfo");
		String forwardString = "design/text/list";
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
	public Object dataGrid(HttpServletRequest request, TxtObject text,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(text.getCode())){
				condition.put("code", text.getCode());
			}
			if(!Strings.isNullOrEmpty(text.getName())){
				condition.put("name", text.getName());
			}
			pageInfo.setCondition(condition);
			txtObjectService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addpage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, TxtObject text) throws Exception {
    	String forward = "design/text/add";
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
    public Object add(HttpServletRequest request,TxtObject text) throws Exception {
    	try {
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("path", text.getPath());
    		List<TxtObject> xlsList = txtObjectService.getTxtObjectList(condition);
    		if(xlsList.size()>0){
    			return renderError("添加失败！text目录 不能重复");
    		}
    		if(text.getPathType().equals("2")){
    			text.setFtpId("");
    		}else if(Strings.isNullOrEmpty(text.getFtpId())){
    			 return renderError("添加失败！目录类型为FTP，则FTP选择 必填");
    		}
    		text.setCreateId((String)request.getSession().getAttribute("accountId"));
    		text.setCreateDate(new Date());
    		text.setStatus(0);
    		txtObjectService.insertTxtObject(text);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    
    @RequestMapping(value = "/mappedPage", method = RequestMethod.GET)
    public ModelAndView mapped(HttpServletRequest request, String id) throws Exception {
    	String forward = "design/text/mappedTable";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		TxtObject xls = txtObjectService.getTxtObjectById(id);
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
    public Object mappedSave(HttpServletRequest request,TxtObject text) throws Exception {
    	try {
    		text.setType("1");//是否 映射数据库 表  已映射表
    		text.setUpdateId((String)request.getSession().getAttribute("accountId"));
    		text.setUpdateDate(new Date());
    		text.setStatus(0);
    		txtObjectService.updateTxtObject(text);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    
    @RequestMapping(value = "/setRolePage", method = RequestMethod.GET)
    public ModelAndView setRole(HttpServletRequest request, String id) throws Exception {
    	String forward = "design/text/rolePage";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		TxtObject xls = txtObjectService.getTxtObjectById(id);
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
    public Object roleSave(HttpServletRequest request,TxtObject text) throws Exception {
    	try {
    		text.setType("1");//是否 映射数据库 表  已映射表
    		text.setUpdateId((String)request.getSession().getAttribute("accountId"));
    		text.setUpdateDate(new Date());
    		text.setStatus(0);
    		txtObjectService.updateTxtObject(text);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request) throws Exception {
		String jsonarray = "";
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<TxtObject> pageList = txtObjectService.getTxtObjectList(condition);
			jsonarray = JSONArray.toJSONString(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    
    @RequestMapping("/editPage")
    public ModelAndView editPage(HttpServletRequest request, TxtObject object) throws Exception {
    	String forward = "design/text/edit";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try {
    		String id = request.getParameter("id");
    		object = txtObjectService.getTxtObjectById(id);
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
    public Object edit(TxtObject object) throws Exception {
    	try {
    		object.setUpdateId("");
    		object.setUpdateDate(new Date());
    		txtObjectService.updateTxtObject(object);
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
    		txtObjectService.deleteTxtObjectById(id);
        } catch(Exception e) {
        	e.printStackTrace();
        	return renderError(e.getMessage());
    	}
        return renderSuccess("删除成功！");
    }
}