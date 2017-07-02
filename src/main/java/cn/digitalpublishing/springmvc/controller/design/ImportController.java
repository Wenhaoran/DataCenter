package cn.digitalpublishing.springmvc.controller.design;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.digitalpublishing.po.imp.ImportLog;
import cn.digitalpublishing.po.imp.ImportRole;
import cn.digitalpublishing.springmvc.controller.BaseController;

@Controller
@RequestMapping("/pages/import")
public class ImportController extends BaseController {

	@RequestMapping(value = "/index")
	public void index(HttpServletRequest request,HttpServletResponse response, String roleId) {
		ImportLog log = new ImportLog();
		try {
			ImportRole role = importRoleService.getImportRoleById(roleId);
			log.setName(role.getName()); 
			log.setRoleId(roleId);
			log.setFileType(role.getType());
			log.setObjId(role.getObjId());
			log.setCreateDate(new Date());
			
			baseService.checkFileAndSave(role,log);
			
			log.setType("成功");
			log.setFileName("");
			log.setDbId("");
			log.setTable("");
		} catch (Exception e) {
			log.setType("失败");
			log.setErrorInfo(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			log.setErrorInfo("");
			importLogService.insertImportLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}