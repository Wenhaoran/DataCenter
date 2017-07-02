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

import cn.digitalpublishing.po.design.FTPConnect;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.design.FTPConnectForm;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.imp.FtpUtil;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/ftp")
public class FTPConnectController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, FTPConnectForm form) throws Exception {
		//Map<String,String> accountInfoMap = (Map<String, String>) request.getSession().getAttribute("pt_accountInfo");
		String forwardString = "design/ftpconn/list";
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
	public Object dataGrid(HttpServletRequest request, FTPConnect ftpconn,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			if(!Strings.isNullOrEmpty(ftpconn.getCode())){
				condition.put("code", ftpconn.getCode());
			}
			if(!Strings.isNullOrEmpty(ftpconn.getName())){
				condition.put("name", ftpconn.getName());
			}
			pageInfo.setCondition(condition);
			ftpConnectService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	/**
	 * @see 
	 * @param request
	 * @param ftpconn
	 * @return add 页面 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, FTPConnect ftpconn) throws Exception {
    	String forward = "design/ftpconn/add";
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
    public Object add(HttpServletRequest request,FTPConnect ftpconn) throws Exception {
    	try {
    		
    		ftpconn.setCreateId((String)request.getSession().getAttribute("accountId"));
    		ftpconn.setCreateDate(new Date());
    		
    		ftpConnectService.insertFTPConnect(ftpconn);			
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping("/editPage")
    public ModelAndView editPage(HttpServletRequest request, FTPConnect ftpConnect) throws Exception {
    	String forward = "design/ftpconn/edit";
    	Map<String, Object> model = new HashMap<String, Object>();
    	try {
    		String id = request.getParameter("id");
    		ftpConnect = ftpConnectService.getFTPConnectById(id);
        	model.put("ftpconn", ftpConnect);
        } catch(Exception e) {
			logger.error("跳转编辑页面出现异常，",e);
		}
        return new ModelAndView(forward, model);
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(FTPConnect ftpconn) throws Exception {
    	try {
    		ftpconn.setUpdateId("");
    		ftpconn.setUpdateDate(new Date());
    		ftpConnectService.updateFTPConnect(ftpconn);
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,FTPConnect ftpconn) throws Exception {
		String jsonarray = "";
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<FTPConnect> pageList = ftpConnectService.getFTPConnectList(condition);
			jsonarray = JSONArray.toJSONString(pageList);
		} catch (Exception e) {
			logger.error("更新出现异常，", e);
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    @RequestMapping(value = "/getTable", method = RequestMethod.POST)
	@ResponseBody
	public String getTable(HttpServletRequest request,FTPConnect ftpconn) throws Exception {
		String jsonarray = "";
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<FTPConnect> pageList = ftpConnectService.getFTPConnectList(condition);
			jsonarray = JSONArray.toJSONString(pageList);
		} catch (Exception e) {
			logger.error("更新出现异常，", e);
			e.printStackTrace();
		}
		return jsonarray;
	}
    
    @RequestMapping(value = "/testConnect", method = RequestMethod.POST)
	@ResponseBody
	public Object testConnect(HttpServletRequest request,HttpServletResponse response, String id) throws Exception {
		try {
//			String id = dbconn.getId();
			FTPConnect ftp = ftpConnectService.getFTPConnectById(id);
			
			Boolean ifFTP = FtpUtil.testFTPConnect(ftp.getAddress(),ftp.getUsername(),ftp.getPassword());
			if(!ifFTP){
				return renderError("链接失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("链接失败，错误信息为："+e.getMessage());
		}
		return renderSuccess("链接成功！");
	}
    
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) throws Exception {
    	try {
    		ftpConnectService.deleteFTPConnectById(id);
        } catch(Exception e) {
        	e.printStackTrace();
        	return renderError(e.getMessage());
    	}
        return renderSuccess("删除成功！");
    }
}