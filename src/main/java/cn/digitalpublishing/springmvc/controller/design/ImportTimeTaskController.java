package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.digitalpublishing.po.imp.ImportTimeTask;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.imp.ImportTimeTaskForm;
import cn.digitalpublishing.util.DesignException;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/task")
public class ImportTimeTaskController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, ImportTimeTaskForm form) throws Exception {
		String forwardString = "import/task/list";
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
	public Object dataGrid(HttpServletRequest request, ImportTimeTask task,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 连接编号，连接名称，数据库名称
			pageInfo.setCondition(condition);
			importTimeTaskService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	/**
	 * @see 
	 * @param request
	 * @param excel
	 * @return add 页面 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request,ImportTimeTask task) throws Exception {
    	String forward = "import/task/add";
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
    public Object add(HttpServletRequest request,ImportTimeTask task) throws Exception {
    	try {
    		task.setCreateId((String) request.getSession().getAttribute("accountId"));
    		task.setCreateDate(new Date());
    		importTimeTaskService.insertImportTimeTask(task);
   		} catch(Exception e) {
			logger.error("添加出现异常，",e);
			e.printStackTrace();
		}
        return renderSuccess("添加成功！");
    }
    
}