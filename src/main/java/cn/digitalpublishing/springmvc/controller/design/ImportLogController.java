package cn.digitalpublishing.springmvc.controller.design;

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

import com.google.common.base.Strings;

import cn.digitalpublishing.po.imp.ImportLog;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.imp.ImportLogForm;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/impLog")
public class ImportLogController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, ImportLogForm form) throws Exception {
		//Map<String,String> accountInfoMap = (Map<String, String>) request.getSession().getAttribute("pt_accountInfo");
		String forwardString = "import/log/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<ImportRole> roleList = importRoleService.getImportRoleList(condition); 
			
			form.setIsSuccess(SUCCESS);
			model.put("roleList", roleList);
		} catch (Exception e) {
			e.printStackTrace();
			form.setIsSuccess(FAILURE);
			form.setMsg(e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGrid(HttpServletRequest request, ImportLog log,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			//查询条件： 对象名称，规则名称，对象类型，日志时间
			if(!Strings.isNullOrEmpty(log.getRoleId())){
  				condition.put("roleId", log.getRoleId());
  			}
			if(!Strings.isNullOrEmpty(log.getFileType())){
  				condition.put("fileType", log.getFileType());
  			}
			pageInfo.setCondition(condition);
			importLogService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/showLogInfo", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request,String logId)throws Exception {
		String forward = "import/log/logInfo";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ImportLog log = importLogService.getImportLogById(logId);
			model.put("log", log);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
}