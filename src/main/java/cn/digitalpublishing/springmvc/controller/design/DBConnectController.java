package cn.digitalpublishing.springmvc.controller.design;

import java.sql.Connection;
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
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.design.DBConnectForm;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

@Controller
@RequestMapping("/pages/dbconn")
public class DBConnectController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, DBConnectForm form) throws Exception {
		
		//Map<String,String> accountInfoMap = (Map<String, String>) request.getSession().getAttribute("pt_accountInfo");
		
		String forwardString = "design/dbconn/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setIsSuccess(SUCCESS);
		} catch (Exception e) {
			form.setIsSuccess(FAILURE);
			form.setMsg((e instanceof DesignException) ? ((DesignException) e).getMessage() : e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGrid(HttpServletRequest request, DeDBConnect dbconn,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(dbconn.getCode())){
				condition.put("code", dbconn.getCode());
			}
			if(!Strings.isNullOrEmpty(dbconn.getName())){
				condition.put("name", dbconn.getName());
			}
			if(!Strings.isNullOrEmpty(dbconn.getName())){
				condition.put("dbname", dbconn.getDbname());
			}
			pageInfo.setCondition(condition);
			deDBConnectService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/testConnect", method = RequestMethod.POST)
	@ResponseBody
	public Object testConnect(HttpServletRequest request,HttpServletResponse response, String id) throws Exception {
		try {
//			String id = dbconn.getId();
			DeDBConnect dbconn = deDBConnectService.getDBConnectById(id);
			Connection conn = DBUtil.getConnect(dbconn);
			if(!conn.isClosed()){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("链接失败，错误信息为："+e.getMessage());
		}
		return renderSuccess("链接成功！");
	}
	
	/**
	 * @see 
	 * @param request
	 * @param dbconn
	 * @return add 页面 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, DeDBConnect dbconn) throws Exception {
    	String forward = "design/dbconn/add";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
    		
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }

	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request,DeDBConnect dbconn) throws Exception {
    	try {
    		
    		dbconn.setCreateId((String)request.getSession().getAttribute("accountId"));
    		dbconn.setCreateDate(new Date());
    		Connection conn = DBUtil.getConnect(dbconn);
    		if(conn ==null){
    			dbconn.setStatus(0);
    		}else if(!conn.isClosed()){
				conn.close();
				dbconn.setStatus(1);
			}
    		
    		deDBConnectService.insertDBConnect(dbconn);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping("/editPage")
    public ModelAndView editPage(HttpServletRequest request, DeDBConnect DeDBConnect) throws Exception {
    	String forward = "design/dbconn/edit";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try {
    		String id = request.getParameter("id");
    		DeDBConnect = deDBConnectService.getDBConnectById(id);
        	model.put("dbconn", DeDBConnect);
        } catch(Exception e) {
			logger.error("跳转编辑页面出现异常，",e);
		}
        return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(DeDBConnect dbconn) throws Exception {
    	try {
    		//String accountId = (String)request.getSession().getAttribute("accountId");
    		dbconn.setUpdateId("accountId");
    		dbconn.setUpdateDate(new Date());
    		Connection conn = DBUtil.getConnect(dbconn);
    		if(conn ==null){
    			dbconn.setStatus(0);
    		}else if(!conn.isClosed()){
				conn.close();
				dbconn.setStatus(1);
			}
    		deDBConnectService.updateDBConnect(dbconn);
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,DeDBConnect dbconn) throws Exception {
		String jsonarray = "";
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<DeDBConnect> pageList = deDBConnectService.getDBConnectList(condition);
			jsonarray = JSONArray.toJSONString(pageList);
		} catch (Exception e) {
			logger.error("更新出现异常，", e);
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    @RequestMapping(value = "/getTableList", method = RequestMethod.POST)
	@ResponseBody
	public String getTableList(HttpServletRequest request,String dbId) throws Exception {
		String jsonarray = "";
		try {
			DeDBConnect db = deDBConnectService.getDBConnectById(dbId);
			List<DesignTable> pageList = DBUtil.getTableList(db);
			jsonarray = JSONArray.toJSONString(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Object delete(String id) throws Exception {
		try {
			deDBConnectService.deleteDBConnectById(id);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
}