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

import com.google.common.base.Strings;

import cn.digitalpublishing.po.excel.XlsColumn;
import cn.digitalpublishing.po.excel.XlsObject;
import cn.digitalpublishing.po.imp.ImportRTMapped;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.po.imp.ImportRoleDetail;
import cn.digitalpublishing.po.imp.ImportRoleDetailMatch;
import cn.digitalpublishing.po.txt.TxtColumn;
import cn.digitalpublishing.po.txt.TxtObject;
import cn.digitalpublishing.springmvc.controller.BaseController;
import cn.digitalpublishing.util.mybatis.page.PageInfo;

@Controller
@RequestMapping("/pages/importDetail")
public class ImportRoleDetailController extends BaseController {

	@RequestMapping(value = "/fieldListManagerByRoleId", method = RequestMethod.GET)
    public ModelAndView accountGiveRole(HttpServletRequest request, String roleId) throws Exception {
    	String forward = "import/roleDetail/detailList";
    	Map<String, Object> model = new HashMap<String, Object>();
    	ImportRole role = importRoleService.getImportRoleById(roleId);
    	
    	try{
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("roleId", roleId);
    		List<ImportRoleDetail> detailList = importRoleDetailService.getImportRoleDetailList(condition);
    		
    		model.put("detailList", detailList);
    		model.put("role", role);
    	} catch(Exception e) {
    		logger.error("跳转添加页面出现异常，",e);
    	}
    	return new ModelAndView(forward, model);
    }
	
  	@RequestMapping(value = "/dataGridByRoleId", method = RequestMethod.POST)
  	@ResponseBody
  	public Object dataGridByModuleId(HttpServletRequest request,ImportRoleDetail detail,Integer page, Integer rows, String sort, String order) {
  		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
  		String roleId = request.getParameter("roleId");
  		try {
  			Map<String, Object> condition = new HashMap<String, Object>();
  			//当前 角色ID
  			if(!Strings.isNullOrEmpty(roleId)){
  				condition.put("roleId", roleId);
  			}
  			//查询条件：
  			if(!Strings.isNullOrEmpty(detail.getCode())){
  				condition.put("code", detail.getCode());
  			}
  			pageInfo.setCondition(condition);
  			importRoleDetailService.findDataGrid(pageInfo);
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("查询列表出现异常，", e);
  		}
  		return pageInfo;
  	}
  	
  	
  	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request,ImportRoleDetail detail)throws Exception {
		String forward = "import/roleDetail/add";
		Map<String, Object> model = new HashMap<String, Object>();
		String roleId = request.getParameter("roleId");
		try {
			
			ImportRole role = importRoleService.getImportRoleById(roleId);
			if(role.getType().equals("txt")){
				TxtObject obj = txtObjectService.getTxtObjectById(role.getObjId());
				List<TxtColumn> columnList = txtColumnService.getTxtColumnListByTxtId(obj.getId());
				role.setObjColumnList(columnList);
				role.setObj(obj);
			}else if(role.getType().equals("xls")){
				XlsObject obj = xlsObjectService.getXlsObjectById(role.getObjId());
				List<XlsColumn> columnList = xlsColumnService.getXlsObjectListByXlxId(obj.getId());
				role.setObjColumnList(columnList);
				role.setObj(obj);
			}
			String mappedType = detail.getMappedType();
			
			List<ImportRTMapped> rtList = importRTMappedService.getImportRTMappedByRoleId(role.getId());
			
			model.put("rtList", rtList);
			model.put("mappedType",mappedType);
			model.put("role", role);
		} catch (Exception e) {
			logger.error("跳转添加页面出现异常，", e);
			e.printStackTrace();
		}
		return new ModelAndView(forward, model);
	}
  	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, ImportRoleDetail detail) throws Exception {
		try {
			ImportRole role = importRoleService.getImportRoleById(detail.getRoleId()); 
			
			Map<String, Object> condition = new HashMap<String, Object>();
			/*condition.put("roleId", role.getId());
			condition.put("columnId", detail.getColumnId());*/
			condition.put("rtId", detail.getImportRTId());
			condition.put("tableColumn", detail.getTableColumn());
			List<ImportRoleDetail> detailList = importRoleDetailService.getImportRoleDetailList(condition);
			if(detailList!=null&&detailList.size()>0){
				return renderError("添加异常，请不要重复添加。");
			}
			detail.setType(role.getType());
			detail.setCreateId((String)request.getSession().getAttribute("accountId"));
			detail.setCreateDate(new Date());
			detail.setStatus(0);
			importRoleDetailService.insertImportRoleDetail(detail);
			condition.clear();
			condition.put("rtId", detail.getImportRTId());
			condition.put("tableColumn", detail.getTableColumn());
			List<ImportRoleDetail> detailInsert = importRoleDetailService.getImportRoleDetailList(condition);
			//保存匹配映射的 内容
			if(detail.getMappedType().equals("2")){
				String rtId = detail.getMatchId();
				ImportRTMapped rt = importRTMappedService.getImportRTMappedById(rtId);
				ImportRoleDetailMatch match = new ImportRoleDetailMatch();
				match.setCode(detail.getMatchCode());
				match.setName(detail.getMatchName());
				match.setDbId(rt.getDbId());
				match.setTable(rt.getDbTable());
				match.setDetailId(detailInsert.get(0).getId());
				importRoleDetailMatchService.insert(match);
			}
		} catch (Exception e) {
			logger.error("添加出现异常，", e);
			e.printStackTrace();
			return renderError("添加出现异常,错误信息为" + e.getMessage());
		}
		return renderSuccess("添加成功！");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,String[] ids) throws Exception {
		try {
			importRoleDetailService.deleteByIds(ids);
		} catch (Exception e) {
			logger.error("删除出现异常，", e);
			e.printStackTrace();
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}
}