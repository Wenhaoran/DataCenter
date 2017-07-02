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

import cn.digitalpublishing.po.design.DesignTable;
import cn.digitalpublishing.po.design.DesignTableColumn;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.springmvc.form.design.DesignTableColumnForm;
import cn.digitalpublishing.util.mybatis.page.PageInfo;
import cn.digitalpublishing.util.sql.DBUtil;

@Controller
@RequestMapping("/pages/column")
public class DesignColumnController extends BaseController {
	
	@RequestMapping(value = "/addAttr", method = RequestMethod.GET)
	public ModelAndView addAttr(HttpServletRequest request, String tableId)throws Exception {
		String forward = "design/designTable/addAttr";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DesignTable table = designTableService.findByTableId(tableId);
			
			List<DesignTableColumn> columnList = DBUtil.getColumnList(table);
			model.put("columnList", columnList); 
			model.put("tableId", tableId);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
	
	//查询
	@RequestMapping(value = "/attrDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object attrDataGrid(HttpServletRequest request, String tableId,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			DesignTable table = designTableService.findByTableId(tableId);
			List<DesignTableColumn> columnList = DBUtil.getColumnList(table);
			pageInfo.setRows(columnList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}

	@RequestMapping(value = "/attr", method = RequestMethod.POST)
	@ResponseBody
	public Object attr(HttpServletRequest request, DesignTableColumnForm form)throws Exception {
		try {
			String[] names = form.getNames();
			DesignTable table = designTableService.findByTableId(form.getTableId());
			Map<String,DesignTableColumn> columnMap = DBUtil.getColumnMap(table);
			for(String name : names){
				DesignTableColumn column = columnMap.get(name);
				if(column!=null){
					column.setTableId(table.getId());
					column.setCreateId((String) request.getSession().getAttribute("accountId"));
					column.setCreateDate(new Date());
					column.setStatus(1);
					designTableColumnService.insert(column);
				}
			}
			table.setType("2");
			table.setUpdateDate(new Date());
			table.setUpdateId((String) request.getSession().getAttribute("accountId"));
			designTableService.update(table);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}

	@RequestMapping(value = "/columnListManagerByTableId", method = RequestMethod.GET)
    public ModelAndView columnListManagerByTableId(HttpServletRequest request, String tableId) throws Exception {
        String forward = "design/designColumn/detailList";
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	DesignTable table = designTableService.findByTableId(tableId);
        	/*List<DesignTableColumn> columnList = DBUtil.getColumnList(table);
			model.put("columnList", columnList);*/
			model.put("table", table);
        } catch(Exception e) {
    		logger.error("查询列表出现异常，",e);
    		e.printStackTrace();
    	}
    	return new ModelAndView(forward, model);
    }
	
	//查询
	@RequestMapping(value = "/dataGridByTableId", method = RequestMethod.POST)
	@ResponseBody
	public Object dataGridByTableId(HttpServletRequest request, String tableId,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("tableId", tableId);
			List<DesignTableColumn> columnList = designTableColumnService.getListByCondition(condition);
			
			pageInfo.setRows(columnList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询列表出现异常，", e);
		}
		return pageInfo;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,String[] ids,String tableId) throws Exception {
		try {
			designTableColumnService.deleteListByIdsAndTableId(ids,tableId);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
	
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView editPage(HttpServletRequest request, String id) throws Exception {
		String forward = "design/designColumn/edit";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			DesignTableColumn column = designTableColumnService.findById(id);
			model.put("column", column);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object edit(HttpServletRequest request, DesignTableColumn column)throws Exception {
		try {
			column.setUpdateId((String) request.getSession().getAttribute("accountId"));
			column.setUpdateDate(new Date());
			designTableColumnService.update(column);
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
}