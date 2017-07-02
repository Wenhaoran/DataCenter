package cn.digitalpublishing.service.base;

import cn.digitalpublishing.po.imp.ImportLog;
import cn.digitalpublishing.po.imp.ImportRole;

public interface BaseService {

	void checkFileAndSave(ImportRole role,ImportLog log) throws Exception;
	

}
