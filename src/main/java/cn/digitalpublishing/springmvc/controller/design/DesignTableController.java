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

import com.google.common.base.Strings;

import cn.digitalpublishing.po.design.DeDBConnect;
import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.po.design.DesignTableConfig;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.design.DesignTableForm;
import cn.digitalpublishing.util.imp.FtpUtil;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

@Controller
@RequestMapping("/pages/des")
public class DesignTableController extends BaseController {

	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response, DesignTableForm form) throws Exception {

		String forwardString = "design/designTable/showTable";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("status", 1);
			List<DeDBConnect> dbconnList = deDBConnectService.getDBConnectList(condition);
			
			DesignTable table = new DesignTable();
			table.setDbconn(dbconnList.get(0));
			form.setObj(table);

			model.put("form", form);
			model.put("dbconnList", dbconnList);
			form.setIsSuccess(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			form.setIsSuccess(FAILURE);
			form.setMsg("");
			forwardString = "msg";
		}
		return new ModelAndView(forwardString, model);
	}
	
	//查询
	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGrid(HttpServletRequest request, DesignTable table,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			// 查询条件：数据库ID ，
			if (!Strings.isNullOrEmpty(table.getDbId())) {
				condition.put("dbId", table.getDbId());
			}
			pageInfo.setCondition(condition);
			designTableService.findDataGrid(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request, DesignTable table)
			throws Exception {
		String forward = "design/designTable/add";
		Map<String, Object> model = new HashMap<String, Object>();
		String dbId = request.getParameter("dbId");
		try {
			DeDBConnect db = deDBConnectService.getDBConnectById(dbId);
			List<DesignTable> tableList = DBUtil.getTableList(db);
			model.put("tableList", tableList); 
			model.put("dbId", dbId);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, DesignTable table)throws Exception {
		try {
			table.setCreateId((String) request.getSession().getAttribute("accountId"));
			table.setCreateDate(new Date());
			table.setType("1");
			table.setStatus(1);
			designTableService.insert(table);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
	
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String tableId) throws Exception {
		String forward = "design/designTable/edit";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DesignTable table = designTableService.findByTableId(tableId);
			model.put("table", table);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, DesignTable table)throws Exception {
		try {
			table.setUpdateId((String) request.getSession().getAttribute("accountId"));
			table.setUpdateDate(new Date());
			designTableService.update(table);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(DesignTable table) throws Exception {
		try {
			String tableId = table.getId();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("tableId", tableId);
			List<DesignTableColumn> columnList = designTableColumnService.getListByCondition(condition);
			
			for(DesignTableColumn column : columnList){
				designTableColumnService.deleteById(column.getId());
			}
			
			DesignTableConfig config = designTableConfigService.getConfigByTableId(tableId);
			if(config!=null){
				designTableConfigService.deleteById(config.getId());
			}
			
			designTableService.deleteById(tableId);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
	
	public void export(){
		 int port = 21;
        String hostName = "";
        String userName = "";
        String password = "";
        String pathName = "";
        String localpath = "D:/download";
        String filename = ""; 
        FtpUtil.downloadFile(hostName, port, userName, password, pathName, filename, localpath);
	}
}